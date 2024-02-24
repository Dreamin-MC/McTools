package fr.dreamin.mctools.api.triton;

import com.rexcantor64.triton.api.Triton;
import com.rexcantor64.triton.api.TritonAPI;
import com.rexcantor64.triton.api.language.LanguageManager;
import com.rexcantor64.triton.api.players.PlayerManager;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.service.Service;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TritonManager extends Service {
  private Triton triton;
  private PlayerManager playerManager;
  private LanguageManager languageManager;
  private List<ItemStack> itemsLanguage = new ArrayList<>();

  @Override
  public void onEnable() {
    super.onEnable();
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
