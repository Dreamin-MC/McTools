package fr.dreamin.dreamintools.api.triton;

import com.rexcantor64.triton.api.Triton;
import com.rexcantor64.triton.api.TritonAPI;
import com.rexcantor64.triton.api.language.LanguageManager;
import com.rexcantor64.triton.api.players.PlayerManager;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TritonManager {
  private Triton triton;
  private PlayerManager playerManager;
  private LanguageManager languageManager;
  private List<ItemStack> itemsLanguage = new ArrayList<>();

  public TritonManager() {
    triton = TritonAPI.getInstance();
    playerManager = triton.getPlayerManager();
    languageManager = triton.getLanguageManager();

    triton.getLanguageManager().getAllLanguages().forEach(language -> {
      this.itemsLanguage.add(new ItemBuilder(Material.STONE).setName("§6" + language.getName()).toItemStack());
    });

  }

  public Triton getTriton() {
    return triton;
  }
  public PlayerManager getPlayerManager() {
    return playerManager;
  }
  public LanguageManager getLanguageManager() {
    return languageManager;
  }
  public List<ItemStack> getAllItemsLanguage() {
    return itemsLanguage;
  }
}
