package fr.dreamin.mctools.api.gui.defaultGui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.PaginationManager;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.api.player.manager.MessageManager;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeatherSettingGui implements GuiBuilder {

  private static WeatherType weatherType = WeatherType.CLEAR;
  private static DayType dayType = DayType.SUN;

  @Override
  public String name(Player player) {
    return LangMsg.WEATHERSETTINGS_TITLE.getMsg(McTools.getService(PlayersService.class).getPlayer(player).getLang());
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {
    items.createList("", Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44});
    items.create(LangMsg.WEATHERSETTINGS_WEATHER.getMsg(mtPlayer.getLang(), weatherType.getName(mtPlayer)), weatherType.getMaterial(), 21);
    items.create(LangMsg.WEATHERSETTINGS_DAY.getMsg(mtPlayer.getLang(), dayType.getName()), dayType.getMaterial(), 23);
    items.create(ItemsPreset.arrowBackWard.getItem(), 31);
  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 21:
        weatherType = weatherType.getNextWeatherType();
        weatherType.setWeather();
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), WeatherSettingGui.class);
        break;
      case 23:
        dayType = dayType.getNextDayType();
        dayType.setTime();
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), WeatherSettingGui.class);
        break;
      case 31:
        if (McTools.getService(GuiManager.class).getGuiConfig().getMainGui() != null)
          McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), McTools.getService(GuiManager.class).getGuiConfig().getMainGui().getClass());
        else {
          mtPlayer.getPlayer().sendMessage(LangMsg.MAINGUI_NOTSET.getMsg(mtPlayer.getLang()));
          mtPlayer.getPlayer().closeInventory();
        }
        break;
    }
  }

  private static enum WeatherType {

    THUNDER(LangMsg.WEATHERSETTINGS_THUNDER, null, Material.BLAZE_ROD) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(true);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },
    RAIN(LangMsg.WEATHERSETTINGS_THUNDER, THUNDER, Material.WATER_BUCKET) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },


    CLEAR(LangMsg.WEATHERSETTINGS_THUNDER, RAIN, Material.BUCKET) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(false);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    };

    private final LangMsg msg;
    private WeatherType nextWeatherType;
    private final Material material;

    WeatherType(LangMsg msg, WeatherType nextWeatherType, Material material) {
      this.msg = msg;
      this.nextWeatherType = nextWeatherType;
      this.material = material;
    }

    static {
      THUNDER.setNextWeatherType(CLEAR);
    }

    public String getName(MTPlayer mtPlayer) {
      return this.msg.getMsg(mtPlayer.getLang());
    }

    public Material getMaterial() {
      return material;
    }

    public WeatherType getNextWeatherType() {
      return nextWeatherType;
    }

    public void setNextWeatherType(WeatherType nextWeatherType) {
      this.nextWeatherType = nextWeatherType;
    }
    public abstract void setWeather();
  }
  private static enum DayType {

    MIDNIGHT(CustomChatColor.DARKBLUE.getColorWithText("Midnight"), null, Material.ORANGE_TULIP) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(18000);
        });
      }
    },
    MOON(CustomChatColor.DARKBLUE.getColorWithText("Night"), MIDNIGHT, Material.WITHER_ROSE) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(13000);
        });
      }
    },
    SUN(CustomChatColor.YELLOW.getColorWithText("Day"), MOON, Material.SUNFLOWER) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(6000);
        });
      }
    },
    MORNING(CustomChatColor.YELLOW.getColorWithText("Morning"), SUN, Material.CORNFLOWER) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(1000);
        });
      }
    };

    private final String name;
    private DayType nextDayType;
    private final Material material;

    DayType(String name, DayType nextDayType, Material material) {
      this.name = name;
      this.nextDayType = nextDayType;
      this.material = material;
    }

    static {
      MIDNIGHT.setNextDayType(MORNING);
    }

    public String getName() {
      return name;
    }

    public Material getMaterial() {
      return material;
    }

    public DayType getNextDayType() {
      return nextDayType;
    }

    public void setNextDayType(DayType nextDayType) {
      this.nextDayType = nextDayType;
    }

    public abstract void setTime();
  }

}
