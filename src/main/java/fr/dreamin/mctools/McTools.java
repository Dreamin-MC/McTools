package fr.dreamin.mctools;

import com.google.gson.Gson;
import fr.dreamin.mctools.api.glowing.GlowingBlocks;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.LanguageConfifGui;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.api.gui.defaultGui.TestGui;
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
import fr.dreamin.mctools.components.commands.build.models.CommandGetCoal;
import fr.dreamin.mctools.components.commands.build.models.CommandGetId;
import fr.dreamin.mctools.components.commands.build.voice.CommandShowVoice;
import fr.dreamin.mctools.components.commands.dev.CommandDT;
import fr.dreamin.mctools.api.commands.SimpleCommand;
import fr.dreamin.mctools.components.commands.dev.CommandDVD;
import fr.dreamin.mctools.components.commands.hote.CommandDVH;
import fr.dreamin.mctools.components.commands.player.CommandDV;
import fr.dreamin.mctools.components.game.DTGame;
import fr.dreamin.mctools.api.armorPose.ArmorManager;
import fr.dreamin.mctools.components.game.manager.DoorManager;
import fr.dreamin.mctools.components.game.manager.InteractManager;
import fr.dreamin.mctools.components.gui.armorStand.*;
import fr.dreamin.mctools.components.gui.tag.TagCategoryList;
import fr.dreamin.mctools.components.gui.tag.TagList;
import fr.dreamin.mctools.config.Codex;
import fr.dreamin.mctools.generic.modules.listeners.interact.FilterInteractService;
import fr.dreamin.mctools.generic.service.Service;
import fr.dreamin.mctools.generic.service.ServiceManager;
import fr.dreamin.mctools.mysql.DatabaseCodex;
import fr.dreamin.mctools.mysql.DatabaseManager;
import fr.dreamin.mctools.paper.services.dependency.PaperDependencyService;
import fr.dreamin.mctools.paper.services.players.PlayersService;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class McTools extends JavaPlugin{
  private static McTools instance;
  private static Codex codex;
  private DatabaseManager databaseDreaminVoice;
  private static DTGame dtGame;
  private TritonManager tritonManager;
  private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
  private GuiManager guiManager;
  private GlowingEntities glowingEntities;
  private GlowingBlocks glowingBlocks;
  private final TimerManager timerManager = new TimerManager();
  private final CooldownManager cooldownManager = new CooldownManager();
  private final ServiceManager serviceManager = new ServiceManager();
  private VoiceWallManager voiceWallManager = null;
  private static Logging logging;
  private static Gson gson;

  @Getter
  private boolean isDisabled = false;


  @Override
  public void onEnable() {
    instance = this;
    this.glowingEntities = new GlowingEntities(instance);
    this.glowingBlocks = new GlowingBlocks(instance);

    logging = new Logging(this);

    loadDefaultGui();
    saveDefaultConfig();
    codex = new Codex(getConfig());

    setDatabase(new DatabaseManager(codex.getHost(), codex.getPort(), codex.getDbName(), codex.getUsername(), codex.getPassword()));
    getDatabase().connection();
    DatabaseCodex.checkIfExist();

    dtGame = new DTGame(this);

    getServiceManager().loadServices(FilterInteractService.class, PaperDependencyService.class, PlayersService.class);

    getService(PaperDependencyService.class)
      .ifPluginEnabled("FastAsyncWorldEdit", (pluginName, plugin) -> {
        McTools.getLog().info("FastAsyncWorldEdit is detected !");})
      .ifPluginEnabled("Citizens", (pluginName, plugin) -> {
        McTools.getLog().info("Citizens is detected !");})
      .ifPluginEnabled("LuckPerms", (pluginName, plugin) -> {
        McTools.getLog().info("LuckPerms is detected !");})
      .ifPluginEnabled("PlaceholderAPI", (pluginName, plugin) -> {
        McTools.getLog().info("PlaceholderAPI is detected !");})
      .ifPluginEnabled("ProtocolLib", (pluginName, plugin) -> {
        McTools.getLog().info("ProtocolLib is detected !");})
      .ifPluginEnabled("ModelEngine", (pluginName, plugin) -> {
        McTools.getLog().info("ModelEngine is detected !");})
      .ifPluginEnabled("Multiverse-Core", (pluginName, plugin) -> {
        McTools.getLog().info("Multiverse-Core is detected !");})
      .ifPluginEnabled("OpenAudioMc", (pluginName, plugin) -> {
        McTools.getLog().info("OpenAudioMc is detected !");
        voiceWallManager = new VoiceWallManager();
      })
      .ifPluginEnabled("Triton", (pluginName, plugin) -> {
        McTools.getLog().info("Triton is detected !");
        if (getService(PaperDependencyService.class).isPluginEnabled("ProtocolLib") && getService(PaperDependencyService.class).isPluginEnabled("PlaceholderAPI")) {
          tritonManager = new TritonManager();
          guiManager.addMenu(new LanguageConfifGui());
        }
      }
    );

    loadCommands();

    gson = new Gson();


    
  }

  @Override
  public void onDisable() {
    this.glowingEntities.disable();
    this.glowingBlocks.disable();
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
  public static DTGame getGame() {
    return dtGame;
  }

  public DatabaseManager getDatabase() {
    return databaseDreaminVoice;
  }
  public void setDatabase(DatabaseManager databaseDreaminVoice) {
    this.databaseDreaminVoice = databaseDreaminVoice;
  }

  public GuiManager getGuiManager() {
        return guiManager;
    }
  public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
  }
  public void addMenu(GuiBuilder guiBuilder){
    guiManager.addMenu(guiBuilder);
  }
  private void loadDefaultGui() {
    getServer().getPluginManager().registerEvents(new GuiManager(),instance);
    guiManager=new GuiManager();
    registeredMenus=new HashMap<>();

    guiManager.addMenu(new WeatherSettingGui());
    guiManager.addMenu(new ListMapGui());
    guiManager.addMenu(new TestGui());

  }

  public GlowingEntities getGlowingEntities() {
    return glowingEntities;
  }
  public GlowingBlocks getGlowingBlocks() {
    return glowingBlocks;
  }

  public TimerManager getTimerManager() {
    return timerManager;
  }
  public CooldownManager getCooldownManager() {
    return cooldownManager;
  }
  public ServiceManager getServiceManager() {
    return serviceManager;
  }

  public TritonManager getTritonManager() {

    if (getService(PaperDependencyService.class).isPluginEnabled("Triton")) {
      return tritonManager;
    }
    else {
      McTools.getLog().error("Triton is not enabled !");
      return null;
    }
  }

  public VoiceWallManager getVoiceWallManager() {

    if (getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) {
      return voiceWallManager;
    }
    else {
      McTools.getLog().error("OpenAudioMc is not enabled !");
      return null;
    }
  }

  public static Logging getLog() {
    return logging;
  }

  public static Gson getGson() {
    return gson;
  }

  public static <T extends Service> T getService(Class<T> service) {
    return service.cast(McTools.getInstance().getServiceManager().loadService(service));
  }

  public static <T> T resolveDependency(Class<T> d) {
    return d.cast(McTools.getInstance().getServiceManager().resolve(d));
  }

  private void loadCommands() {

    SimpleCommand.createCommand("dt", new CommandDT(), "dreamintools");
    SimpleCommand.createCommand("dvh", new CommandDVH());
    SimpleCommand.createCommand("dv", new CommandDV());
    SimpleCommand.createCommand("dvd", new CommandDVD());

    if (getCodex().isBuildMode()) {
      SimpleCommand.createCommand("as", new CommandArmorStand(), "as");
      SimpleCommand.createCommand("coal", new CommandGetCoal(), "getcoal");
      SimpleCommand.createCommand("getid", new CommandGetId(), "getid");
      SimpleCommand.createCommand("showvoice", new CommandShowVoice(), "showvoice");
      SimpleCommand.createCommand("tag", new CommandTag(), "tag");
      SimpleCommand.createCommand("door", new CommandDoor(), "door");
      SimpleCommand.createCommand("interact", new CommandInteract(), "interact");

      //armor stand
      addMenu(new ArmorStandMenuGui());
      addMenu(new ArmorStandMoveRotate());
      addMenu(new ArmorStandBasicSettings());
      addMenu(new ArmorStandListSelected());
      addMenu(new ArmorStandPresetPoses());
      addMenu(new ArmorStandListRadius());
      addMenu(new ArmorStandListLocked());
      addMenu(new ArmorStandArmsSettings());

      //tag
      addMenu(new TagCategoryList());
      addMenu(new TagList());
    }

  }

}
