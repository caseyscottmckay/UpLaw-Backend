/*
package us.uplaw.crawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import us.uplaw.model.Court;
import us.uplaw.repository.CourtRepository;
import us.uplaw.util.AppConstants;
import us.uplaw.util.db.MySqlUtils;
import us.uplaw.util.string.StringUtils;

public class CourtListenerCrawler extends DocumentCrawler {

  private static final Logger log = LoggerFactory.getLogger(CourtListenerCrawler.class);

  private static final String DOMAIN_NAME = "courtlistener";

  private static final String COURTLISTENER_URL = "https://www.courtlistener.com/api/bulk-data/";

  private static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

  private static final String[] FILENAMES = AppConstants.COURT_LISTENER_JURISDICTIONS_ABBREVIATED().split(",");

  @Autowired
  static CourtRepository courtRepository;

  public enum DocumentType {
    clusters("clusters"),
    dockets("dockets"),
    opinions("opinions");

    String value;

    DocumentType(final String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public static void downloadFiles() throws IOException {
    for (DocumentType documentType : DocumentType.values()) {
      for (String filename : FILENAMES) {
        filename = filename + ".tar.gz";
        File dumpDir = new File(AppConstants.DUMP_DIRECTORY + "/" + AppConstants.COURTLISTENER_DOMAIN_NAME + "/" + documentType.getValue() + "/");
        File cacheDir = new File(AppConstants.CACHE_DIRECTORY + "/" + AppConstants.COURTLISTENER_DOMAIN_NAME + "/" + documentType.getValue() + "/");
        File dataDir = new File(AppConstants.DATA_DIRECTORY + "/" + AppConstants.COURTLISTENER_DOMAIN_NAME + "/" + documentType.getValue() + "/");
        if (!dumpDir.exists()) {
          dumpDir.mkdirs();
        }
        if (!dataDir.exists()) {
          dataDir.mkdirs();
        }
        if (!cacheDir.exists()) {
          cacheDir.mkdirs();
        }
        File file = new File(dumpDir + "/" + filename);
        if (!file.exists()) {
          downloadFile(COURTLISTENER_URL + documentType.getValue() + "/" + filename, file.getAbsolutePath());
          log.info("Download complete: " + file.getAbsolutePath());
        } else {
          log.info("File exists: " + file.getAbsolutePath());
        }
      }
    }
  }

  public static void gzipToZip() {
    for (DocumentType documentType : DocumentType.values()) {
      for (String filename : FILENAMES) {
        filename = filename + ".tar.gz";

        File dumpDir = new File(AppConstants.DUMP_DIRECTORY + "/" + DOMAIN_NAME + "/" + documentType.getValue() + "/");
        File dataDir = new File(AppConstants.DATA_DIRECTORY + "/" + DOMAIN_NAME + "/" + documentType.getValue() + "/");

        String outputZipPath = dataDir.getAbsolutePath() + "/" + filename.substring(0, filename.indexOf(".tar.gz"));
        File outputZipFile = new File(outputZipPath);
        if (outputZipFile.exists()) {
          log.info("File does exists: " + outputZipFile);
        }
        if (!outputZipFile.exists()) {
          log.info("File does not exists: " + outputZipFile);
          IOUtils.extractTarGzDirectory(dumpDir.getAbsolutePath() + "/" + filename, dataDir.getAbsolutePath() + "/" + filename.substring(0, filename.indexOf(".tar.gz")));
          //IOUtils.zipDirectory(cacheDir.getAbsolutePath() + "/" + filename);
        }
      }
    }
  }

  public static void generateBinders() throws IOException {
    downloadFiles();
    gzipToZip();
    //IOUtils.deleteDirectory(new File("/home/casey/up/uplaw-backend/dump/cache"));
    log.info("CourtListener zip binders generated at " + "dump/data");
  }

  public static void indexCourts() throws IOException {
    for (String fileName : FILENAMES) {

      String resourceTypeAbbreviated = fileName;
      HashMap<String, String>      jurisdictionsMeta = AppConstants.getCourtListenerJurisdicitonMeta();
      if (jurisdictionsMeta.containsKey(resourceTypeAbbreviated)) {
        String[] parts = jurisdictionsMeta.get(resourceTypeAbbreviated).split(",");
        LocalDate startDate = LocalDate.now();
        if (!parts[6].contains("Unknown")){
          startDate = LocalDate.parse(parts[6]);
        }
        LocalDate endDate = LocalDate.now();
        if (!parts[7].contains("Unknown")){
          endDate = LocalDate.parse(parts[7]);
        }
        Court court = Court.builder()
                .courtAbbreviation(resourceTypeAbbreviated)
                .courtName(parts[0])
                .courtJurisdiction(parts[2])
                .courtHomepage(parts[3])
                .courtAbbreviation(parts[4])
                .courtCitationAbbreviation(parts[5])
                .courtStartDate(startDate)
                .courtEndDate(endDate)
                .build();
        MySqlUtils.insertCourt(court);
      }
    }
  }

  public static void indexDocuments() throws IOException {
    for (String fileName : FILENAMES) {
      String resourceTypeAbbreviated = fileName;
      String opinionDirectoryPath = "/data/uplaw/courtlistener/opinions/" + fileName;
      String docketDirectoryPath = "/data/uplaw/courtlistener/dockets/" + fileName;
      String clusterDirectoryPath = "/data/uplaw/courtlistener/clusters/" + fileName;
      File opinionDir = new File(opinionDirectoryPath);
      File docketDir = new File(docketDirectoryPath);
      File clusterDir = new File(clusterDirectoryPath);
      List<String> opinions = Arrays.asList(opinionDir.list());
      List<String> dockets = Arrays.asList(docketDir.list());
      List<String> clusters = Arrays.asList(clusterDir.list());
      for (String f : opinions) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        if (dockets.contains(f)) {
          objectNode.put("docket", new String(Files.readAllBytes(Paths.get(docketDirectoryPath + "/" + f))));
        }
        if (clusters.contains(f)) {
          objectNode.put("cluster", new String(Files.readAllBytes(Paths.get(clusterDirectoryPath + "/" + f))));
        }
        if (opinions.contains(f)) {
          objectNode.put("document", new String(Files.readAllBytes(Paths.get(opinionDirectoryPath + "/" + f))));
        }
        Document document = parseDocument(objectNode);

        MySqlUtils.insertDocument(document);

      }

    }
  }

  public static Document parseDocument(ObjectNode documentObject) throws IOException {
    String document = documentObject.get("document").asText();
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory factory = mapper.getFactory();
    JsonParser parser = factory.createParser(document);
    JsonNode json = mapper.readTree(parser);

    String date_created = Optional.ofNullable(json.get("date_created")).map(s -> s.asText()).orElse("");
    String date_modified = Optional.ofNullable(json.get("date_modified")).map(s -> s.asText()).orElse("");
    String opinions_cited = Optional.ofNullable(json.get("opinions_cited")).map(s -> s.toString()).orElse("");
    String html = Optional.ofNullable(json.get("html")).map(s -> s.asText()).orElse("");
    String html_with_citations = Optional.ofNullable(json.get("html_with_citations")).map(s -> s.asText()).orElse("");
    String html_lawbox = Optional.ofNullable(json.get("html_lawbox")).map(s -> s.asText()).orElse("");
    String html_columbia = Optional.ofNullable(json.get("html_columbia")).map(s -> s.asText()).orElse("");
    String htmlTemp = html_columbia.length() > 10 ? html_columbia : html_lawbox;
    html = html_with_citations.length() > 10 ? html_with_citations : htmlTemp;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_24H_FULL_FORMAT);
    LocalDate dateCreated = LocalDate.now();
    LocalDate dateModified = LocalDate.now();
    try {
      LocalDateTime localDate = Optional.ofNullable(date_created).map(d -> LocalDateTime.parse(d, formatter)).orElse(LocalDateTime.now());
      dateCreated = localDate.atZone(ZoneId.systemDefault()).toLocalDate();
      localDate = Optional.ofNullable(date_modified).map(d -> LocalDateTime.parse(d, formatter)).orElse(LocalDateTime.now());
      dateModified = localDate.atZone(ZoneId.systemDefault()).toLocalDate();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    String cluster = Optional.ofNullable(documentObject.get("cluster")).map(d -> d.asText()).orElse("");
    parser = factory.createParser(cluster);
    JsonNode clusterJson = mapper.readTree(parser);
    String docket = Optional.ofNullable(documentObject.get("docket")).map(d -> d.asText()).orElse("");
    parser = factory.createParser(docket);
    JsonNode docketJson = mapper.readTree(parser);
    String resource_uri = Optional.ofNullable(json.get("resource_uri")).map(s -> s.asText()).orElse("");
    String absolute_url = Optional.ofNullable(json.get("absolute_url")).map(s -> s.asText()).orElse("");
    String slug = Optional.ofNullable(absolute_url).map(s -> s.split("/")[absolute_url.split("/").length - 1]).orElse("");
    ObjectNode contentObject = JsonNodeFactory.instance.objectNode();
    contentObject.put("original",json.toString());
    Document newDocument = new Document();
    newDocument.setTitle(StringUtils.toTitleCase(slug.replace("-", " ")));
    newDocument.setUri(resource_uri.substring(resource_uri.indexOf("/v3/")));
    newDocument.setContent(contentObject.toString());
    return newDocument;
  }

  public static void main(String[] args) throws IOException {
    indexCourts();
    //generateBinders();
    //indexDocuments();
  }

}
*/
