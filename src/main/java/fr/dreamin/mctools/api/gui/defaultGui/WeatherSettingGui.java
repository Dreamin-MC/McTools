package fr.dreamin.mctools.api.gui.defaultGui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.GuiPaginationManager;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeatherSettingGui implements GuiBuilder {

  private static WeatherType weatherType = WeatherType.CLEAR;
  private static DayType dayType = DayType.SUN;

  @Override
  public String name(MTPlayer mtPlayer) {
    return mtPlayer.getMsg(LangMsg.GUI_WEATHERSETTINGS_TITLE, "");
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {
    items.createList("", Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44});
    items.create(mtPlayer.getMsg(LangMsg.GUI_WEATHERSETTINGS_WEATHER, weatherType.getName(mtPlayer)), weatherType.getMaterial(), 21);
    items.create(mtPlayer.getMsg(LangMsg.GUI_WEATHERSETTINGS_DAY, dayType.getName(mtPlayer)), dayType.getMaterial(), 23);
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
          mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.ERROR_MAINGUI_NOTSET, ""));
          mtPlayer.getPlayer().closeInventory();
        }
        break;
    }
  }

  private static enum WeatherType {

    THUNDER(LangMsg.GUI_WEATHERSETTINGS_WEATHER_THUNDER, null, Material.BLAZE_ROD) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(true);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },
    RAIN(LangMsg.GUI_WEATHERSETTINGS_WEATHER_RAIN, THUNDER, Material.WATER_BUCKET) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },


    CLEAR(LangMsg.GUI_WEATHERSETTINGS_WEATHER_CLEAR, RAIN, Material.BUCKET) {
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
      return mtPlayer.getMsg(msg, "");
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

    MIDNIGHT(LangMsg.GUI_WEATHERSETTINGS_DAY_MIDNIGHT, null, Material.ORANGE_TULIP) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(18000);
        });
      }
    },
    MOON(LangMsg.GUI_WEATHERSETTINGS_DAY_MOON, MIDNIGHT, Material.WITHER_ROSE) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(13000);
        });
      }
    },
    SUN(LangMsg.GUI_WEATHERSETTINGS_DAY_SUN, MOON, Material.SUNFLOWER) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(6000);
        });
      }
    },
    MORNING(LangMsg.GUI_WEATHERSETTINGS_DAY_MORNING, SUN, Material.CORNFLOWER) {
      @Override
      public void setTime() {
        Bukkit.getWorlds().forEach(world -> {
          world.setTime(1000);
        });
      }
    };

    private final LangMsg msg;
    private DayType nextDayType;
    private final Material material;

    DayType(LangMsg msg, DayType nextDayType, Material material) {
      this.msg = msg;
      this.nextDayType = nextDayType;
      this.material = material;
    }

    static {
      MIDNIGHT.setNextDayType(MORNING);
    }

    public String getName(MTPlayer mtPlayer) {
      return mtPlayer.getMsg(this.msg, "");
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
