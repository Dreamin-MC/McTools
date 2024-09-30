package fr.dreamin.api.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NBTDownloader {

  // Fonction pour télécharger un fichier à partir d'une URL
  public static File downloadFileFromURL(String urlStr, String outputFileName) throws IOException {
    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    // Vérifier le statut de la connexion
    int status = connection.getResponseCode();
    if (status != HttpURLConnection.HTTP_OK) {
      throw new IOException("Failed to download file. HTTP response code: " + status);
    }

    // Créer un fichier temporaire pour stocker le fichier téléchargé
    File file = new File(outputFileName);
    try (InputStream inputStream = connection.getInputStream();
         FileOutputStream outputStream = new FileOutputStream(file)) {

      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
    } finally {
      connection.disconnect();
    }

    System.out.println("File downloaded: " + file.getAbsolutePath());
    return file;  // Retourne le fichier local téléchargé
  }
}