package fr.dreamin.mctools.components.players;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.log.Logging;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.manager.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MTPlayer {

  @Getter @Setter private Player player;
  @Getter private PlayerTickManager playerTickManager = new PlayerTickManager(this);

  @Getter @Setter private PlayerPerm perm;
  @Getter private boolean isEditMode = false;
  @Getter private ItemStack itemStats;
  @Getter private String skinBase64 = null;
  @Getter private boolean isCanMove = true;
  @Getter @Setter private Lang lang;
  @Getter @Setter private String ip = "";

  @Getter private final BuildPlayerManager buildManager = new BuildPlayerManager();
  @Getter private ArmorStandManager armorStandManager = null;
  @Getter private VoiceConfPlayerManager voiceConfManager = new VoiceConfPlayerManager(this);
  @Getter private final HudPlayerManager hudPlayerManager = null;
  @Getter private TritonPlayerManager tritonManager;
  @Getter private VoicePlayerManager voiceManager = null;
  @Getter private StaffPlayerManager staffPlayerManager = new StaffPlayerManager(this);
  @Getter private SelectObjectManager selectObjectManager = new SelectObjectManager(this);

  public MTPlayer(Player player) {
    this.player = player;

    if (McTools.getCodex().isBuildMode()) this.armorStandManager = new ArmorStandManager(this);

//    this.hudPlayerManager = new HudPlayerManager(this);

    this.itemStats = new ItemBuilder(Material.PLAYER_HEAD).setPlayerHFromName(player.getName()).setName("§eStatistique de " + player.getName()).toItemStack();

    this.perm = PlayerPerm.getTopPerm(player);

    if (McTools.getCodex().isVoiceMode()) {
      if (McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) this.voiceManager = new VoicePlayerManager(this);
      else {
        McTools.getService(Logging.class).warn("§cOpenAudio is not enabled, some features will note be available.");
        this.voiceManager = null;
      }

      if (McTools.getService(PaperDependencyService.class).isPluginEnabled("Triton")) this.tritonManager = new TritonPlayerManager(player);
      else {
        McTools.getService(Logging.class).warn("§cTriton is not enabled, some features will not be available.");
        this.tritonManager = null;
      }

    }

//    try {
//      this.skinBase64 = Minecraft.getSkinBase64(player.getUniqueId().toString());
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }

  }

  public MTPlayer(TritonPlayerManager tritonManager) {
    this.tritonManager = tritonManager;
  }

  public void setEditMode(boolean isEditMode) {
    if (isEditMode) getMsg(LangMsg.CMD_MT_SETEDITMODE, LangMsg.GENERAL_ENABLED);
    else getMsg(LangMsg.CMD_MT_SETEDITMODE, LangMsg.GENERAL_DISABLED);

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
    return msg.getMsg(getLang(), args);
  }

  public void sendMsg(LangMsg msg, String... args) {
    player.sendMessage(msg.getMsg(getLang(), args));
  }

  public void sendMsg(LangMsg msg, LangMsg... args) {
    player.sendMessage(msg.getMsg(getLang(), args));
  }

}
