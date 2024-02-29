package fr.dreamin.mctools.components.players;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.log.Logging;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.manager.*;
import fr.dreamin.mctools.mysql.fetcher.UserFetcher.UserFetcher;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MTPlayer {

  @Getter @Setter
  private final Player player;
  @Getter @Setter
  private PlayerPerm perm;
  @Getter
  private boolean isEditMode;

  @Getter
  private final BuildPlayerManager buildManager = new BuildPlayerManager();
  @Getter
  private final ArmorStandManager armorStandManager;
  @Getter
  private final VoiceConfPlayerManager voiceConfManager = new VoiceConfPlayerManager();
  @Getter
  private final HudPlayerManager hudPlayerManager;
  @Getter
  private final TritonPlayerManager tritonManager;
  @Getter
  private final PlayerTickManager playerTickManager;
  @Getter
  private VoicePlayerManager voiceManager;
  @Getter
  private final ItemStack itemStats;
  @Getter
  private String skinBase64 = null;
  @Getter
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

  public void setEditMode(boolean isEditMode) {
    if (isEditMode) player.sendMessage(LangMsg.CMD_MT_SETEDITMODE.getMsg(getLang(), LangMsg.GENERAL_ENABLED.getMsg(getLang())));
    else player.sendMessage(LangMsg.CMD_MT_SETEDITMODE.getMsg(getLang(), LangMsg.GENERAL_DISABLED.getMsg(getLang())));

    this.isEditMode = isEditMode;
  }

  public void setCanMove(boolean isCanMove) {
    this.isCanMove = isCanMove;
    if (!isCanMove()) player.setGravity(false);
    else player.setGravity(true);
  }
  public String getMsg(LangMsg msg, String... args) {
    return msg.getMsg(getLang(), args);
  }
  public String getMsg(LangMsg msg, LangMsg... args) {
    return msg.getMsg_(getLang(), args);
  }

  public void sendMsg(LangMsg msg, String... args) {
    player.sendMessage(msg.getMsg(getLang(), args));
  }

  public void sendMsg(LangMsg msg, LangMsg... args) {
    player.sendMessage(msg.getMsg_(getLang(), args));
  }

}
