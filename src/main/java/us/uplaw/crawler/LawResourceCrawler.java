
package us.uplaw.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.uplaw.model.Document;
import us.uplaw.util.db.MySqlUtils;
import us.uplaw.util.string.StringUtils;

public class LawResourceCrawler {

  private static final Logger log = LoggerFactory.getLogger(LawResourceCrawler.class);

  public enum DocumentType {
    article("article"),
    form("form"),
    legislation("legislation"),
    opinion("opinion"),
    regulation("regulation"),
    rule("rule"),
    statute("statute");

    String value;

    DocumentType(final String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public static List<JsonNode> processZipDirectory(String zipDirectoryPath) throws IOException {
    String[] pathParts = zipDirectoryPath.split("/");
    String state = pathParts[6];
    String resourceType = pathParts[7].substring(0, pathParts[7].length() - 4);
    String zipFileName = zipDirectoryPath;
    final ZipFile file = new ZipFile(zipFileName);
    final Enumeration<? extends ZipEntry> entries = file.entries();
    int i = 0;
    ObjectMapper mapper = new ObjectMapper();

    int limit = 0;
    //fixme : delete limit to run full
    while (entries.hasMoreElements() && limit < 3) {
      limit++;
      final ZipEntry entry = entries.nextElement();
      String filePath = entry.getName();
      if ((!filePath.contains("opinion")) && filePath.contains("/documents/") && filePath.endsWith(".xml")) {
        log.info(filePath);
        pathParts = entry.getName().split("/");
        String fileName = pathParts[pathParts.length - 1];
        fileName = fileName.substring(0, fileName.indexOf(".xml"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(entry)));
        String lines = "";
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
          lines += line + "\n";
        }
        String uri = state + "/" + resourceType + "/" + fileName;
        String source = "law.resource.org";
        Document document = parseXmlToJson(lines, uri, source);
        MySqlUtils.insertDocument(document);
      }
    }
    return null;
  }

  private static Document parseXmlToJson(String lines, String uri, String source) {
    String documentType=null;
    if (lines !=null && lines.length()>0){
      documentType = lines.split("\n")[0];
      documentType =documentType.toLowerCase();
      documentType = documentType.replaceAll("[^a-z]","");
      documentType = documentType.trim();
    }
    String singleStringTemp = lines.replace("\n", "@##@");
    Pattern pattern = Pattern.compile("<(.*?)>(.*?)</(\\1)>");

    Pattern patternStatuteHeading = Pattern.compile("<statute_heading>(.*?)</statute_heading>");
    Pattern patternStatuteSource = Pattern.compile("<statute_source>(.*?)</statute_source>");
    Pattern patternStatuteText = Pattern.compile("<statute_text>(.*?)</statute_text>");
    Pattern patternCitation = Pattern.compile("<citation>(.*?)</citation>");

    Matcher matcher;

    String title = "";
    matcher = patternStatuteHeading.matcher(singleStringTemp);
    if (matcher.find()) {
      title = matcher.group(0).replaceAll("@##@", "\n").replaceAll("<.*?>", "");
    }
    String citation = "";
    matcher = patternCitation.matcher(singleStringTemp);
    if (matcher.find()) {
      citation = matcher.group(0).replaceAll("@##@", "\n").replaceAll("<.*?>", "");
    }
    String html = "";
    matcher = patternStatuteText.matcher(singleStringTemp);
    if (matcher.find()) {
      html = matcher.group(0).replaceAll("@##@", "\n").replaceAll("<.*?>", "");
    }
    ObjectNode contentObject = JsonNodeFactory.instance.objectNode();
    contentObject.put("title", title);
    contentObject.put("html", html);
    String content = singleStringTemp.replaceAll("@##@", "\n");
    contentObject.put("original", content);
    String slug = "";
    if (title.length()<1000){
      slug = StringUtils.toSnakeCase(title);
    }
    if (title.length()>1000){
      String[] titleLines = title.split("\n");
      slug=StringUtils.toSnakeCase(titleLines[0]);
    }
    uri = uri + "/" + slug;
    Document document = Document.builder()
            .title(title)
            .uri(uri)
            .documentType(documentType)
            .content(contentObject.toString())
            .timestamp(LocalDate.now())
            .build();
    return document;
  }

  public static void printFnames(String sDir) throws IOException {
    Files.find(Paths.get(sDir), 999, (p, bfa) -> bfa.isRegularFile() && p.getFileName().toString().matches(".*[^law]\\.zip")
    ).forEach(file -> {
      try {
        processZipDirectory(file.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public static void main(String[] args) throws IOException {
    File[] statesDirectories = new File("/data/uplaw/law.resource.org/states").listFiles();
    for (File f : statesDirectories) {
      printFnames(f.getAbsolutePath());
    }
    // printFnames("/home/casey/data/dump/law.resource/states");
    //processZipDirectory("/data/uplaw/law.resource.org/states");
    //processZipDirectory("/home/casey/data/dump/law.resource/federal/federal_rules_of_appellate_procedure.zip");
    //processZipDirectory("/home/casey/data/dump/law.resource/federal/united_states_code.zip");
    //processZipDirectory("/home/casey/data/dump/law.resource/federal/supreme_court_of_the_united_states.zip");
  }

}
