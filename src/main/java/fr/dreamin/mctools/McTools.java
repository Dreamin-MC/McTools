package fr.dreamin.mctools;

import fr.dreamin.mctools.api.glowing.GlowingBlocks;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.LanguageConfifGui;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.api.gui.defaultGui.WeatherSettingGui;
import fr.dreamin.mctools.api.interfaces.InterfaceManager;
import fr.dreamin.mctools.api.modelEngine.Interact;
import fr.dreamin.mctools.api.log.Logging;
import fr.dreamin.mctools.api.time.CooldownManager;
import fr.dreamin.mctools.api.time.TimerManager;
import fr.dreamin.mctools.api.triton.TritonManager;
import fr.dreamin.mctools.api.voice.VoiceWallManager;
import fr.dreamin.mctools.components.commands.build.CommandBuild;
import fr.dreamin.mctools.components.commands.build.CommandDisplay;
import fr.dreamin.mctools.components.commands.modelEngine.CommandInteract;
import fr.dreamin.mctools.components.commands.modelEngine.CommandDoor;
import fr.dreamin.mctools.components.commands.build.CommandTag;
import fr.dreamin.mctools.components.commands.build.CommandArmorStand;
import fr.dreamin.mctools.components.commands.build.CommandModel;
import fr.dreamin.mctools.components.commands.build.CommandGetId;
import fr.dreamin.mctools.components.commands.voice.CommandShowVoice;
import fr.dreamin.mctools.components.commands.CommandMT;
import fr.dreamin.mctools.api.commands.SimpleCommand;
import fr.dreamin.mctools.components.commands.staff.CommandStaff;
import fr.dreamin.mctools.components.commands.voice.CommandDV;
import fr.dreamin.mctools.components.game.MTGame;
import fr.dreamin.mctools.api.armorPose.ArmorManager;
import fr.dreamin.mctools.components.game.manager.DoorManager;
import fr.dreamin.mctools.components.game.manager.InteractManager;
import fr.dreamin.mctools.components.gui.build.armorStand.*;
import fr.dreamin.mctools.components.gui.build.display.DisplayListRadiusGui;
import fr.dreamin.mctools.components.gui.build.display.DisplayListSelectedGui;
import fr.dreamin.mctools.components.gui.build.display.DisplayMenuGui;
import fr.dreamin.mctools.components.gui.build.display.DisplayMoveRotateGui;
import fr.dreamin.mctools.components.gui.staff.FreezeGui;
import fr.dreamin.mctools.components.gui.tag.TagCategoryListGui;
import fr.dreamin.mctools.components.gui.tag.TagListGui;
import fr.dreamin.mctools.config.Codex;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.api.service.ServiceManager;
import fr.dreamin.mctools.config.LangManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class McTools extends JavaPlugin{
  @Getter private static McTools instance;
  private static Codex codex;
  private static LangManager langManager;

  @Getter
  private static LangMsg langMsg;
  private static MTGame dtGame;
  private final ServiceManager serviceManager = new ServiceManager();

  private boolean isDisabled = false;


  @Override
  public void onEnable() {
    instance = this;

    loadGui();
    saveDefaultConfig();

    codex = new Codex();

    langManager = new LangManager(this);

    dtGame = new MTGame(this);

    getServiceManager().loadServices(Logging.class, GuiManager.class, InterfaceManager.class, PaperDependencyService.class, PlayersService.class, TimerManager.class, CooldownManager.class, GlowingEntities.class, GlowingBlocks.class);

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

    DatabaseManager.closeAllConnection();

    ArmorManager.getArmors().forEach(armorPose -> armorPose.remove());

    DoorManager.getDoors().forEach(door -> {
      if (door.getCuboide() != null) door.getCuboide().replaceType(Material.BARRIER, Material.AIR);
      door.remove();
    });

    InteractManager.getInteracts().forEach(Interact::remove);

  }
  public static Codex getCodex() {
    return codex;
  }
  public static MTGame getGame() {
    return dtGame;
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
      new ArmorStandListIdsGui(),
      new ArmorStandArmsSettingsGui(),
      new ArmorStandListSelectedGui(),

      //display
      new DisplayMenuGui(),
      new DisplayListRadiusGui(),
      new DisplayMoveRotateGui(),
      new DisplayListRadiusGui(),
      new DisplayListSelectedGui(),

      //tag
      new TagCategoryListGui(),
      new TagListGui(),

      //staff
      new FreezeGui()
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
    SimpleCommand.createCommand("mt", new CommandMT(), "mctools", "mt");
    SimpleCommand.createCommand("dv", new CommandDV());

    if (getCodex().isBuildMode()) {
      SimpleCommand.createCommand("as", new CommandArmorStand(), "armorstand");
      SimpleCommand.createCommand("model", new CommandModel(), "m");
      SimpleCommand.createCommand("getid", new CommandGetId(), "getid");
      SimpleCommand.createCommand("showvoice", new CommandShowVoice(), "showvoice");
      SimpleCommand.createCommand("tag", new CommandTag(), "tag");
      SimpleCommand.createCommand("door", new CommandDoor(), "door");
      SimpleCommand.createCommand("interact", new CommandInteract(), "interact");
      SimpleCommand.createCommand("build", new CommandBuild(), "b");
      SimpleCommand.createCommand("display", new CommandDisplay(), "d");
    }

    SimpleCommand.createCommand("staff", new CommandStaff());


  }

}
