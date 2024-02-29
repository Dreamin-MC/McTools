package fr.dreamin.mctools;

import fr.dreamin.mctools.api.glowing.GlowingBlocks;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.LanguageConfifGui;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.api.gui.defaultGui.WeatherSettingGui;
import fr.dreamin.mctools.api.interact.Interact;
import fr.dreamin.mctools.api.log.Logging;
import fr.dreamin.mctools.api.time.CooldownManager;
import fr.dreamin.mctools.api.time.TimerManager;
import fr.dreamin.mctools.api.triton.TritonManager;
import fr.dreamin.mctools.api.voice.VoiceWallManager;
import fr.dreamin.mctools.components.commands.build.CommandInteract;
import fr.dreamin.mctools.components.commands.build.CommandDoor;
import fr.dreamin.mctools.components.commands.build.CommandTag;
import fr.dreamin.mctools.components.commands.build.armorStand.CommandArmorStand;
import fr.dreamin.mctools.components.commands.build.models.CommandCoal;
import fr.dreamin.mctools.components.commands.build.models.CommandGetId;
import fr.dreamin.mctools.components.commands.build.voice.CommandShowVoice;
import fr.dreamin.mctools.components.commands.CommandMT;
import fr.dreamin.mctools.api.commands.SimpleCommand;
import fr.dreamin.mctools.components.commands.voice.CommandDV;
import fr.dreamin.mctools.components.game.MTGame;
import fr.dreamin.mctools.api.armorPose.ArmorManager;
import fr.dreamin.mctools.components.game.manager.DoorManager;
import fr.dreamin.mctools.components.game.manager.InteractManager;
import fr.dreamin.mctools.components.gui.armorStand.*;
import fr.dreamin.mctools.components.gui.tag.TagCategoryListGui;
import fr.dreamin.mctools.components.gui.tag.TagListGui;
import fr.dreamin.mctools.config.Codex;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.api.service.ServiceManager;
import fr.dreamin.mctools.config.LangManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.mysql.DatabaseManager;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class McTools extends JavaPlugin{
  private static McTools instance;
  private static Codex codex;
  private static LangManager langManager;

  @Getter
  private static LangMsg langMsg;
  private DatabaseManager databaseDreaminVoice;
  private static MTGame dtGame;
  private final ServiceManager serviceManager = new ServiceManager();

  private boolean isDisabled = false;


  @Override
  public void onEnable() {
    instance = this;

    loadGui();
    saveDefaultConfig();
    codex = new Codex(getConfig());

    langManager = new LangManager(this);

    setDatabase(new DatabaseManager(codex.getHost(), codex.getPort(), codex.getDbName(), codex.getUsername(), codex.getPassword()));
    getDatabase().connection();
//    DatabaseCodex.checkIfExist();

    dtGame = new MTGame(this);

    getServiceManager().loadServices(Logging.class, GuiManager.class, PaperDependencyService.class, PlayersService.class, TimerManager.class, CooldownManager.class, GlowingEntities.class, GlowingBlocks.class);

    getService(PaperDependencyService.class)
      .ifPluginEnabled("FastAsyncWorldEdit", (pluginName, plugin) -> {
        getService(Logging.class).info("FastAsyncWorldEdit is detected !");})
      .ifPluginEnabled("Citizens", (pluginName, plugin) -> {
        getService(Logging.class).info("Citizens is detected !");})
      .ifPluginEnabled("LuckPerms", (pluginName, plugin) -> {
        getService(Logging.class).info("LuckPerms is detected !");})
      .ifPluginEnabled("PlaceholderAPI", (pluginName, plugin) -> {
        getService(Logging.class).info("PlaceholderAPI is detected !");})
      .ifPluginEnabled("ProtocolLib", (pluginName, plugin) -> {
        getService(Logging.class).info("ProtocolLib is detected !");})
      .ifPluginEnabled("ModelEngine", (pluginName, plugin) -> {
        getService(Logging.class).info("ModelEngine is detected !");})
      .ifPluginEnabled("Multiverse-Core", (pluginName, plugin) -> {
        getService(Logging.class).info("Multiverse-Core is detected !");})
      .ifPluginEnabled("OpenAudioMc", (pluginName, plugin) -> {
        getService(Logging.class).info("OpenAudioMc is detected !");
        getService(VoiceWallManager.class);
      })
      .ifPluginEnabled("Triton", (pluginName, plugin) -> {
        getService(Logging.class).info("Triton is detected !");
        if (getService(PaperDependencyService.class).isPluginEnabled("ProtocolLib") && getService(PaperDependencyService.class).isPluginEnabled("PlaceholderAPI")) {
          getServiceManager().loadService(TritonManager.class);
          getService(GuiManager.class).addMenu(new LanguageConfifGui());
        }
      }
    );

    loadCommands();
  }

  @Override
  public void onDisable() {
    isDisabled = true;

    ArmorManager.getArmors().forEach(armorPose -> armorPose.remove());

    DoorManager.getDoors().forEach(door -> {
      if (door.getCuboide() != null) door.getCuboide().replaceType(Material.BARRIER, Material.AIR);
      door.remove();
    });

    InteractManager.getInteracts().forEach(Interact::remove);

  }

  public static McTools getInstance() {
    return instance;
  }
  public static Codex getCodex() {
    return codex;
  }
  public static MTGame getGame() {
    return dtGame;
  }

  public DatabaseManager getDatabase() {
    return databaseDreaminVoice;
  }
  public void setDatabase(DatabaseManager databaseDreaminVoice) {
    this.databaseDreaminVoice = databaseDreaminVoice;
  }


  private void loadGui() {
    GuiManager guiManager = getService(GuiManager.class);

    guiManager.addMenu(
      //default gui
      new WeatherSettingGui(),
      new ListMapGui(),

      //armorStand
      new ArmorStandMenuGui(),
      new ArmorStandMoveRotateGui(),
      new ArmorStandBasicSettingsGui(),
      new ArmorStandListLockedGui(),
      new ArmorStandPresetPosesGui(),
      new ArmorStandListRadiusGui(),
      new ArmorStandArmsSettingsGui(),
      new ArmorStandListSelectedGui(),

      //tag
      new TagCategoryListGui(),
      new TagListGui()
    );
  }
  public boolean isDisabled() {
    return isDisabled;
  }

  public ServiceManager getServiceManager() {
    return serviceManager;
  }

  public TritonManager getTritonManager() {

    if (getService(PaperDependencyService.class).isPluginEnabled("Triton")) {
      return getService(TritonManager.class);
    }
    else {
      getService(Logging.class).error("Triton is not enabled !");
      return null;
    }
  }

  public VoiceWallManager getVoiceWallManager() {

    if (getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) {
      return getService(VoiceWallManager.class);
    }
    else {
      getService(Logging.class).error("OpenAudioMc is not enabled !");
      return null;
    }
  }

  public static <T extends Service> T getService(Class<T> service) {
    return service.cast(McTools.getInstance().getServiceManager().loadService(service));
  }

  public static <T> T resolveDependency(Class<T> d) {
    return d.cast(McTools.getInstance().getServiceManager().resolve(d));
  }

  private void loadCommands() {
    SimpleCommand.createCommand("mt", new CommandMT(), "mctools");
    SimpleCommand.createCommand("dv", new CommandDV());

    if (getCodex().isBuildMode()) {
      SimpleCommand.createCommand("as", new CommandArmorStand(), "as");
      SimpleCommand.createCommand("coal", new CommandCoal(), "getcoal");
      SimpleCommand.createCommand("getid", new CommandGetId(), "getid");
      SimpleCommand.createCommand("showvoice", new CommandShowVoice(), "showvoice");
      SimpleCommand.createCommand("tag", new CommandTag(), "tag");
      SimpleCommand.createCommand("door", new CommandDoor(), "door");
      SimpleCommand.createCommand("interact", new CommandInteract(), "interact");
    }

  }

}
