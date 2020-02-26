package us.uplaw.crawler;

import java.io.File;
import java.io.IOException;

public abstract class DocumentCrawler {

  public static void downloadFile(String url, String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    IOUtils.downloadFile(url, filePath);
  }

}
