package fr.dreamin.dreamintools.components.players;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import fr.dreamin.dreamintools.components.players.manager.*;
import fr.dreamin.dreamintools.mysql.fetcher.UserFetcher.UserFetcher;
import fr.dreamin.dreamintools.paper.services.dependency.PaperDependencyService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DTPlayer {

  private final Player player;
  private boolean hasPermAdmin = false;
  private boolean hasPermDev = false;
  private boolean isEditMode;
  private final BuildManager buildManager = new BuildManager();
  private final ArmorStandManager armorStandManager;
  private final VoiceConfManager voiceConfManager = new VoiceConfManager();
  private final HudPlayerManager hudPlayerManager;
  private final TritonManager tritonManager;
  private VoiceManager voiceManager;
  private final ItemStack itemStats;
  private final String skinBase64;
  private boolean isCanMove = true;

  public DTPlayer(Player player) {
    this.player = player;
    this.isEditMode = false;
    this.armorStandManager = new ArmorStandManager(this);
    this.hudPlayerManager = new HudPlayerManager(this);
    this.itemStats = new ItemBuilder(Material.PLAYER_HEAD).setPlayerHFromName(player.getName()).setName("§eStatistique de " + player.getName()).toItemStack();

    if (player.hasPermission("dreamintools.admin")) this.hasPermAdmin = true;
    if (player.hasPermission("dreamintools.dev")) this.hasPermDev = true;

    if (McTools.getService(PaperDependencyService.class).isPluginEnabled("Triton")) this.tritonManager = new TritonManager(player);
    else {
      McTools.getLog().warn("§cTriton is not enabled, some features will not be available.");
      this.tritonManager = null;
    }

    if (McTools.getCodex().isVoiceMode()) {
      if (McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) {
        this.voiceManager = new VoiceManager(this);
        UserFetcher.getIfInsert(this);
      }
      else {
        McTools.getLog().warn("§cOpenAudio is not enabled, some features will note be available.");
        this.voiceManager = null;
      }
    }
    else {
      this.voiceManager = null;
    }

    this.skinBase64 = null;
//    try {
//      this.skinBase64 = Minecraft.getSkinBase64(player.getUniqueId().toString());
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }

  }

  public Player getPlayer() {
    return player;
  }

  public boolean isEditMode() {
    return isEditMode;
  }

  public void setEditMode(boolean isEditMode) {
    this.isEditMode = isEditMode;
  }

  public boolean isCanMove() {
    return isCanMove;
  }

  public void setCanMove(boolean isCanMove) {
    this.isCanMove = isCanMove;
    if (!isCanMove()) player.setGravity(false);
    else player.setGravity(true);
  }

  public boolean hasPermAdmin() {
    return hasPermAdmin;
  }

  public boolean hasPermDev() {
    return hasPermDev;
  }

  public BuildManager getBuildManager() {
    return buildManager;
  }

  public ArmorStandManager getArmorStandManager() {
    return armorStandManager;
  }

  public HudPlayerManager getHudPlayerManager() {
    return hudPlayerManager;
  }

  public VoiceConfManager getVoiceConfManager() {
    return voiceConfManager;
  }

  public TritonManager getTritonManager() {
    return tritonManager;
  }

  public VoiceManager getVoiceManager() {
    return voiceManager;
  }

  public ItemStack getItemStats() {
    return itemStats;
  }

  public String getSkinBase64() {
    return skinBase64;
  }


}
