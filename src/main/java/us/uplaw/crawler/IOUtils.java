package us.uplaw.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {

  private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

  public static void deleteDirectory(File directory) {
    File[] contents = directory.listFiles();
    if (contents != null) {
      for (File f : contents) {
        if (!Files.isSymbolicLink(f.toPath())) {
          deleteDirectory(f);
        }
      }
    }
    directory.delete();
  }

  public static void downloadFile(String url, String filePath) throws IOException {
    File dir = new File(filePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    InputStream in = new URL(url).openStream();
    Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
  }

  public static InputStream getInputStream(String tarFileName) throws Exception {
    if (tarFileName.substring(tarFileName.lastIndexOf(".") + 1, tarFileName.lastIndexOf(".") + 3).equalsIgnoreCase("gz")) {
      log.info("Creating an GZIPInputStream for the file");
      return new GZIPInputStream(new FileInputStream(new File(tarFileName)));
    } else {
      log.info("Creating an InputStream for the file");
      return new FileInputStream(new File(tarFileName));
    }
  }

  public static void extractTarGzDirectory(String sourceTarGzDirectoryAbsolutePath, String destinationDirectoryAbsolutePath) {
    File destinationDir = new File(destinationDirectoryAbsolutePath);
    if (!destinationDir.exists()) {
      destinationDir.mkdirs();
    }
    try {
      String strSourceFile = sourceTarGzDirectoryAbsolutePath;
      String strDest = destinationDirectoryAbsolutePath;
      InputStream in = getInputStream(strSourceFile);
      untar(in, strDest);
    } catch (Exception e) {
      e.printStackTrace();
      log.info(e.getMessage());
    }
  }

  private static void untar(InputStream in, String untarDir) throws IOException {
    log.info("Reading TarInputStream... ");
    TarInputStream tin = new TarInputStream(in);
    TarEntry tarEntry = tin.getNextEntry();
    log.info("UNTARDIR " + untarDir);
    if (new File(untarDir).exists()) {
      while (tarEntry != null) {
        File destPath = new File(untarDir + File.separatorChar + tarEntry.getName());
        if (!tarEntry.isDirectory()) {
          FileOutputStream fout = new FileOutputStream(destPath);
          tin.copyEntryContents(fout);
          fout.close();
        } else {
          destPath.mkdir();
        }
        tarEntry = tin.getNextEntry();
      }
      tin.close();
    } else {
      log.info("That destination directory doesn't exist! " + untarDir);
    }
  }

  public static void zipFile(String inputFilePath, String outPutFilePath) throws IOException {
    String sourceFile = inputFilePath;
    FileOutputStream fos = new FileOutputStream(outPutFilePath);
    ZipOutputStream zipOut = new ZipOutputStream(fos);
    File fileToZip = new File(sourceFile);
    FileInputStream fis = new FileInputStream(fileToZip);
    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
    zipOut.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zipOut.write(bytes, 0, length);
    }
    zipOut.close();
    fis.close();
    fos.close();
  }

  public static void zipDirectory(String absoluteDirectoryPath) throws IOException {
    File directoryToZip = new File(absoluteDirectoryPath);

    List<File> fileList = new ArrayList<File>();
    log.info("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
    getAllFiles(directoryToZip, fileList);
    log.info("---Creating zip file");
    writeZipFile(directoryToZip, fileList);
    log.info("---Done");
  }

  public static void getAllFiles(File dir, List<File> fileList) {
    try {
      File[] files = dir.listFiles();
      for (File file : files) {
        fileList.add(file);
        if (file.isDirectory()) {
          log.info("directory:" + file.getCanonicalPath());
          getAllFiles(file, fileList);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeZipFile(File directoryToZip, List<File> fileList) {
    try {
      FileOutputStream fos = new FileOutputStream(directoryToZip.getAbsolutePath() + ".zip");
      ZipOutputStream zos = new ZipOutputStream(fos);

      for (File file : fileList) {
        if (!file.isDirectory()) { // we only zip files, not directories
          addToZip(directoryToZip, file, zos);
        }
      }
      zos.close();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
          IOException {
    FileInputStream fis = new FileInputStream(file);
    String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
    ZipEntry zipEntry = new ZipEntry(zipFilePath);
    zos.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zos.write(bytes, 0, length);
    }
    zos.closeEntry();
    fis.close();
  }

  public static String fileToString(String absoluteFilePath) {
    File file = new File(absoluteFilePath);
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }

}
