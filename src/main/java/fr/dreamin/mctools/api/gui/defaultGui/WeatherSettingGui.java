package fr.dreamin.mctools.api.gui.defaultGui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.PaginationManager;
import fr.dreamin.mctools.api.gui.GuiItems;
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
    return McTools.getCodex().getPrefixGUIName() + CustomChatColor.WHITE.getColorWithText("Weather Settings");
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
  public void contents(Player player, Inventory inv, GuiItems items) {
    items.createList("", Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44});
    items.create("§7Weather: " + weatherType.getName(), weatherType.getMaterial(), 21);
    items.create("§7Day: " + dayType.getName(), dayType.getMaterial(), 23);
    items.create(CustomChatColor.RED.getColorWithText("Back"), Material.ARROW, 31);
  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    switch (slot) {
      case 21:
        weatherType = weatherType.getNextWeatherType();
        weatherType.setWeather();
        McTools.getService(GuiManager.class).open(player, WeatherSettingGui.class);
        break;
      case 23:
        dayType = dayType.getNextDayType();
        dayType.setTime();
        McTools.getService(GuiManager.class).open(player, WeatherSettingGui.class);
        break;
      case 31:
        if (McTools.getService(GuiManager.class).getGuiConfig().getMainGui() != null)
          McTools.getService(GuiManager.class).open(player, McTools.getService(GuiManager.class).getGuiConfig().getMainGui().getClass());
        else {
          player.sendMessage(CustomChatColor.RED.getColorWithText("The main gui is not set"));
          player.closeInventory();
        }
        break;
    }
  }

  private static enum WeatherType {

    THUNDER(CustomChatColor.YELLOW.getColorWithText("Thunder"), null, Material.BLAZE_ROD) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(true);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },
    RAIN(CustomChatColor.BLUE.getColorWithText("Rain"), THUNDER, Material.WATER_BUCKET) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(true);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    },


    CLEAR(CustomChatColor.GREEN.getColorWithText("Clear"), RAIN, Material.BUCKET) {
      @Override
      public void setWeather() {
        Bukkit.getWorlds().forEach(world -> {
          world.setStorm(false);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        });
      }
    };

    private final String name;
    private WeatherType nextWeatherType;
    private final Material material;

    WeatherType(String name, WeatherType nextWeatherType, Material material) {
      this.name = name;
      this.nextWeatherType = nextWeatherType;
      this.material = material;
    }

    static {
      THUNDER.setNextWeatherType(CLEAR);
    }

    public String getName() {
      return name;
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
