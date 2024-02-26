package fr.dreamin.mctools.components.players;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.log.Logging;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.players.manager.*;
import fr.dreamin.mctools.mysql.fetcher.UserFetcher.UserFetcher;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MTPlayer {

  private final Player player;
  private PlayerPerm perm;
  private boolean isEditMode;
  private final BuildPlayerManager buildManager = new BuildPlayerManager();
  private final ArmorStandManager armorStandManager;
  private final VoiceConfPlayerManager voiceConfManager = new VoiceConfPlayerManager();
  private final HudPlayerManager hudPlayerManager;
  private final TritonPlayerManager tritonManager;
  private final PlayerTickManager playerTickManager;
  private VoicePlayerManager voiceManager;
  private final ItemStack itemStats;
  private String skinBase64 = null;
  private boolean isCanMove = true;

  @Getter @Setter
  private Lang lang = McTools.getCodex().getDefaultLang();

  public MTPlayer(Player player) {
    this.player = player;
    this.isEditMode = false;
    this.armorStandManager = new ArmorStandManager(this);
    this.hudPlayerManager = new HudPlayerManager(this);
    this.playerTickManager = new PlayerTickManager(this);
    this.itemStats = new ItemBuilder(Material.PLAYER_HEAD).setPlayerHFromName(player.getName()).setName("§eStatistique de " + player.getName()).toItemStack();

    this.perm = PlayerPerm.getTopPerm(player);

    if (McTools.getService(PaperDependencyService.class).isPluginEnabled("Triton")) this.tritonManager = new TritonPlayerManager(player);
    else {
      McTools.getService(Logging.class).warn("§cTriton is not enabled, some features will not be available.");
      this.tritonManager = null;
    }

    if (McTools.getCodex().isVoiceMode()) {
      if (McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) {
        this.voiceManager = new VoicePlayerManager(this);
        UserFetcher.getIfInsert(this);
      }
      else {
        McTools.getService(Logging.class).warn("§cOpenAudio is not enabled, some features will note be available.");
        this.voiceManager = null;
      }
    }
    else this.voiceManager = null;

    try {
      this.skinBase64 = Minecraft.getSkinBase64(player.getUniqueId().toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

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

  public PlayerPerm getPerm() {
    return perm;
  }

  public PlayerTickManager getPlayerTickManager() {
    return playerTickManager;
  }

  public BuildPlayerManager getBuildManager() {
    return buildManager;
  }

  public ArmorStandManager getArmorStandManager() {
    return armorStandManager;
  }

  public HudPlayerManager getHudPlayerManager() {
    return hudPlayerManager;
  }

  public VoiceConfPlayerManager getVoiceConfManager() {
    return voiceConfManager;
  }

  public TritonPlayerManager getTritonManager() {
    return tritonManager;
  }

  public VoicePlayerManager getVoiceManager() {
    return voiceManager;
  }

  public ItemStack getItemStats() {
    return itemStats;
  }

  public String getSkinBase64() {
    return skinBase64;
  }
}
