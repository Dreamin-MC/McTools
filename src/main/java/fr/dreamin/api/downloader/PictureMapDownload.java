package fr.dreamin.api.downloader;

import com.bergerkiller.bukkit.common.map.MapTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class PictureMapDownload {

  // Méthode pour télécharger une image depuis une URL et la convertir en MapTexture
  public static MapTexture loadImageFromUrl(String imageUrl, int width, int height) throws Exception {
    // Télécharger l'image
    URL url = new URL(imageUrl);
    InputStream inputStream = url.openStream();
    BufferedImage image = ImageIO.read(inputStream);

    // Redimensionner l'image (par exemple à 16x16 pixels)
    BufferedImage resizedImage = resizeImage(image, width, height); // Redimensionner l'image

    // Convertir BufferedImage redimensionné en MapTexture
    return MapTexture.fromImage(resizedImage);
  }

  public static MapTexture loadImageFromUrl(String imageUrl) throws Exception {
    // Télécharger l'image
    URL url = new URL(imageUrl);
    InputStream inputStream = url.openStream();
    BufferedImage image = ImageIO.read(inputStream);

    // Convertir BufferedImage redimensionné en MapTexture
    return MapTexture.fromImage(image);
  }

  public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    graphics2D.dispose();
    return resizedImage;
  }

  // Méthode pour faire tourner l'image de fond
  public static BufferedImage rotateImage(BufferedImage image, double angle) {
    int width = image.getWidth();
    int height = image.getHeight();

    // Créer une nouvelle image vide pour contenir l'image pivotée
    BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = rotatedImage.createGraphics();

    // Appliquer la transformation de rotation
    AffineTransform transform = new AffineTransform();
    transform.rotate(angle, width / 2, height / 2);  // Rotation autour du centre de l'image
    g2d.setTransform(transform);

    // Dessiner l'image pivotée
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();

    return rotatedImage;
  }

}
