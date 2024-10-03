package fr.dreamin.api.msg;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class generates a gradient effect across a given text using a list of hexadecimal color codes.
 */
@Getter @Setter
public class GradientText {

  private final String BASE_TEXT;  // The text to apply the gradient to
  private final List<ChatColor> gradient;  // List of ChatColors forming the gradient
  private int shiftIndex = 0;  // Index used to shift the gradient effect

  /**
   * Constructs a GradientText object that generates a gradient across the specified text using the given list of colors.
   *
   * @param text The text on which the gradient will be applied.
   * @param colorHexList The list of hexadecimal color codes representing the gradient.
   * @throws IllegalArgumentException If less than two colors are provided in the colorHexList.
   */
  public GradientText(final String text, final List<String> colorHexList) {
    if (colorHexList.size() < 2) {
      throw new IllegalArgumentException("GradientText requires at least two colors.");
    }

    this.BASE_TEXT = text;
    this.gradient = new LinkedList<>();

    // Generate the gradient from the provided color list
    generateGradient(colorHexList);
  }

  /**
   * Generates a gradient between the provided color hex codes and stores the result in the gradient list.
   *
   * @param colorHexList The list of hexadecimal color codes representing the gradient.
   */
  private void generateGradient(final List<String> colorHexList) {
    int halfLength = BASE_TEXT.length() / 2;  // Length for the first half of the gradient

    for (int i = 0; i < colorHexList.size() - 1; i++) {
      ChatColor color1 = ChatColor.of(colorHexList.get(i));
      ChatColor color2 = ChatColor.of(colorHexList.get(i + 1));

      for (int j = 0; j < halfLength; j++) {
        // Interpolate between color1 and color2 for each component (RGB)
        double ratio = (double) j / halfLength;
        int red = interpolate(color1.getColor().getRed(), color2.getColor().getRed(), ratio);
        int green = interpolate(color1.getColor().getGreen(), color2.getColor().getGreen(), ratio);
        int blue = interpolate(color1.getColor().getBlue(), color2.getColor().getBlue(), ratio);

        gradient.add(ChatColor.of(new Color(red, green, blue)));
      }
    }
  }

  /**
   * Interpolates between two color component values (e.g., red, green, blue) based on a given ratio.
   *
   * @param start The starting component value (e.g., red, green, or blue).
   * @param end The ending component value.
   * @param ratio The interpolation ratio, between 0 and 1.
   * @return The interpolated component value.
   */
  private int interpolate(int start, int end, double ratio) {
    return (int) (start * (1 - ratio) + end * ratio);
  }

  /**
   * Returns the text with the gradient effect applied, shifting the gradient with each call.
   *
   * @return The gradient text as a string with color codes.
   */
  public String getGradientText() {
    StringBuilder sb = new StringBuilder();

    // Apply the gradient to each character in BASE_TEXT
    for (int i = 0; i < BASE_TEXT.length(); i++) {
      ChatColor color = gradient.get((shiftIndex + i) % gradient.size());
      sb.append(color).append(BASE_TEXT.charAt(i));
    }

    // Shift the gradient for the next call
    shiftIndex = (shiftIndex - 1 + gradient.size()) % gradient.size();

    return sb.toString();
  }
}