package us.uplaw.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {

  private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

  public static String readZipFileToString(String zipFilePath) throws IOException {

    ZipFile file = new ZipFile(zipFilePath);
    String line = "";
    String lines = "";
    try (FileInputStream fis = new FileInputStream(zipFilePath);
         BufferedInputStream bis = new BufferedInputStream(fis);
         ZipInputStream zis = new ZipInputStream(bis)) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        log.info("File: %s Size: %d",
                entry.getName(), entry.getSize());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(entry)));
        line = "";
        lines = "";
        while ((line = bufferedReader.readLine()) != null) {
          lines += line + "\n";
        }
      }
    }
    return lines;
  }

}
