package fr.dreamin.api.advancements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.dreamin.mctools.McTools;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AdvancementMessage extends DisplayMessage	{

  private NamespacedKey id;
  private int modelDataId = -1;
  private final String title, icon, description, background, frame = "task";
  private boolean announce = false, toast = true;

  /**
   * Create a Toast/Advancement display (Top right corner)
   * @param id A unique id for this Advancement, will be the name if advancement file
   * @param title Message to show/send
   * @param icon minecraft id of display item (minecraft:...)
   */
  public AdvancementMessage(String id, String title, String icon, int modelDataId)	{
    this(new NamespacedKey(McTools.getInstance(), id), title, icon, modelDataId);
  }

  /**
   * Create a Toast/Advancement display (Top right corner)
   * @param id A unique id for this Advancement, will be the name if advancement file
   * @param title Message to show/send
   * @param icon minecraft id of display item (minecraft:...)
   */
  public AdvancementMessage(NamespacedKey id, String title, String icon, int modelDataId) {
    this.id = id;
    this.title = title;
    this.description = "§7This Toast was created with DreamAPI";
    this.icon = icon;
    this.background = "minecraft:textures/gui/advancements/backgrounds/dirt.png";
    this.modelDataId = modelDataId;
  }

  @Override
  public void showTo(Player player)	{
    showTo(List.of(player));
  }

  @Override
  public void showTo(List<Player> players)	{

    if (McTools.getInstance() == null) return;

    add();
    grant(players);
    new BukkitRunnable() {

      @Override
      public void run() {
        revoke(players);
        if (!remove()) McTools.getInstance().getServer().getLogger().info("Advancement " + id + " failed to remove");
      }
    }.runTaskLater(McTools.getInstance(), 5);
  }

  @SuppressWarnings("deprecation")
  private Advancement add()	{
    try {
      McTools.getInstance().getServer().getLogger().info("Advancement " + id + " saved");
      return McTools.getInstance().getServer().getUnsafe().loadAdvancement(id, getJson());
    } catch (IllegalArgumentException e){
      if (remove()) add();

      McTools.getInstance().getServer().getLogger().info("Advancement " + id + " already saved, remove and add for corrections");
    }
    return null;
  }

  private void grant(Collection<? extends Player> players) {
    Advancement advancement = McTools.getInstance().getServer().getAdvancement(id);
    AdvancementProgress progress;
    for (Player player : players)	{

      assert advancement != null;
      progress = player.getAdvancementProgress(advancement);
      if (!progress.isDone())	{
        for (String criteria : progress.getRemainingCriteria())	{
          progress.awardCriteria(criteria);
        }
      }
    }
  }

  @SuppressWarnings("deprecation")
  private boolean remove() {
    return McTools.getInstance().getServer().getUnsafe().removeAdvancement(id);
  }

  private void revoke(List<Player> players)	{
    Advancement advancement = McTools.getInstance().getServer().getAdvancement(id);
    AdvancementProgress progress;
    for (Player player : players)	{

      assert advancement != null;
      progress = player.getAdvancementProgress(advancement);
      if (progress.isDone())	{
        for (String criteria : progress.getAwardedCriteria())	{
          progress.revokeCriteria(criteria);
        }
      }
    }
  }

  public String getJson()	{

    JsonObject json = new JsonObject();

    JsonObject icon = new JsonObject();
    icon.addProperty("id", this.icon);



    if (this.modelDataId >= 0) {
      JsonObject modelDataId = new JsonObject();
      modelDataId.addProperty("minecraft:custom_model_data", this.modelDataId);
      icon.add("components", modelDataId);
    }

    JsonObject display = new JsonObject();
    display.add("icon", icon);
    display.addProperty("title", this.title);
    display.addProperty("description", this.description);
    display.addProperty("background", this.background);
    display.addProperty("frame", this.frame);
    display.addProperty("announce_to_chat", announce);
    display.addProperty("show_toast", toast);
    display.addProperty("hidden", true);

    JsonObject criteria = new JsonObject();
    JsonObject trigger = new JsonObject();

    trigger.addProperty("trigger", "minecraft:impossible");
    criteria.add("impossible", trigger);

    json.add("criteria", criteria);
    json.add("display", display);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    return gson.toJson(json);

  }
}