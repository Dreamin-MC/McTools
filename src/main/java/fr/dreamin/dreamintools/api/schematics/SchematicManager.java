package fr.dreamin.dreamintools.api.schematics;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import java.io.*;
import java.nio.file.Files;
import java.sql.Blob;

public class SchematicManager {

  private File schematicFile;
  private Clipboard clipboard;
  private AffineTransform affineTransform;


  public SchematicManager(String resourcePath) {
    this.affineTransform = new AffineTransform();

    try (InputStream schematicStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
      if (schematicStream == null) {
        throw new FileNotFoundException("Le fichier schématique à l'emplacement " + resourcePath + " n'a pas été trouvé dans les ressources.");
      }

      String fileExtension = resourcePath.substring(resourcePath.lastIndexOf(".") + 1);
      ClipboardFormat format = ClipboardFormats.findByExtension(fileExtension);
      if (format == null) {
        throw new IOException("Format non reconnu pour le fichier à l'emplacement " + resourcePath);
      }

      try (ClipboardReader reader = format.getReader(schematicStream)) {
        this.clipboard = reader.read();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public SchematicManager(Blob blob) {
    try {
      this.schematicFile = File.createTempFile("schematic", ".schem");
      this.affineTransform = new AffineTransform();
      try (FileOutputStream fos = new FileOutputStream(this.schematicFile)) {
        fos.write(blob.getBytes(1, (int) blob.length()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadSchematic() {
    if (clipboard != null)
      return;

    try {
      try (ClipboardReader reader = ClipboardFormats.findByFile(schematicFile).getReader(Files.newInputStream(schematicFile.toPath()))) {
        clipboard = reader.read();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void rotateSchematic(double angle) {
    if (clipboard == null)
      loadSchematic();

    // Calculer le centre du schématique
    BlockVector3 center = clipboard.getRegion().getCenter().toBlockPoint();

    // Créer une nouvelle transformation qui effectue la rotation autour du centre
    AffineTransform transform = new AffineTransform();
    transform = transform.translate(center.getX(), center.getY(), center.getZ());
    transform = transform.rotateY(angle);
    transform = transform.translate(-center.getX(), -center.getY(), -center.getZ());

    // Mettre à jour l'attribut affineTransform de la classe
    this.affineTransform = transform;
  }



  public void pasteSchematic(Location location, boolean ignoreAirBlock) {
    if (clipboard == null)
      loadSchematic();

    EditSession editSession = null;
    try {
      editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()));
      BlockVector3 vectorLocation = BlockVector3.at(location.getX(), location.getY(), location.getZ());

      // Préparer et exécuter l'opération de collage avec rotation
      ClipboardHolder holder = new ClipboardHolder(clipboard);
      holder.setTransform(affineTransform);
      this.clipboard = holder.getClipboard();

      Operation operation = holder
        .createPaste(editSession)
        .to(vectorLocation)
        .ignoreAirBlocks(ignoreAirBlock)
        .build();

      Operations.complete(operation);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (editSession != null) {
        editSession.close();
      }
    }
  }

  //--------------------
  //Getter & Setter
  //--------------------

  public File getSchematicFile() {
    return schematicFile;
  }

  public Clipboard getClipboard() {
    return clipboard;
  }

  public AffineTransform getAffineTransform() {
    return affineTransform;
  }
  public void setAffineTransform(AffineTransform affineTransform) {
    this.affineTransform = affineTransform;
  }
}

