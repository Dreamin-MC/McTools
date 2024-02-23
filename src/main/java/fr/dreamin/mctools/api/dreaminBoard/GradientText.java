package fr.dreamin.mctools.api.dreaminBoard;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GradientText {

  private static String BASE_TEXT = "====================";

  private List<ChatColor> gradient;
  private int shiftIndex = 0;

  public GradientText(String text, List<String> colorHexList) {
    BASE_TEXT = text;
    if (colorHexList.size() < 2) {
      throw new IllegalArgumentException("GradientText requires at least two colors");
    }

    gradient = new LinkedList<>();
    for (int i = 0; i < colorHexList.size() - 1; i++) {
      ChatColor color1 = ChatColor.of(colorHexList.get(i));
      ChatColor color2 = ChatColor.of(colorHexList.get(i + 1));
      for (int j = 0; j < BASE_TEXT.length() / 2; j++) { // première moitié du texte
        int red = (int) (color1.getColor().getRed() * (1 - (double) j / (BASE_TEXT.length() / 2)) + color2.getColor().getRed() * (double) j / (BASE_TEXT.length() / 2));
        int green = (int) (color1.getColor().getGreen() * (1 - (double) j / (BASE_TEXT.length() / 2)) + color2.getColor().getGreen() * (double) j / (BASE_TEXT.length() / 2));
        int blue = (int) (color1.getColor().getBlue() * (1 - (double) j / (BASE_TEXT.length() / 2)) + color2.getColor().getBlue() * (double) j / (BASE_TEXT.length() / 2));
        gradient.add(ChatColor.of(new Color(red, green, blue)));
      }
    }
  }

  public String getGradientText() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < BASE_TEXT.length(); i++) {
      ChatColor color = gradient.get((shiftIndex + i) % gradient.size());
      sb.append(color);
      sb.append(BASE_TEXT.charAt(i));
    }

    shiftIndex = (shiftIndex - 1 + gradient.size()) % gradient.size();

    return sb.toString();
  }
}