package fr.dreamin.mctools.components.listeners.interact;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.api.player.manager.SoundManager;
import fr.dreamin.mctools.api.armorPose.ArmorManager;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.EnumSet;
import java.util.Set;

public class InteractListener implements Listener {

  private Set<Material> stopTypeInteract = EnumSet.noneOf(Material.class);

  @EventHandler
  public void onInteractAtBlock(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

    //Empecher la double interaction lors d'un clic droit.
    if (event.getHand() != EquipmentSlot.HAND) {
      return;
    }

    // Vérifie si l'action est un clic droit sur un bloc
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block clickedBlock = event.getClickedBlock();

      // Vérifie si le bloc cliqué est un bloc de note
      if (clickedBlock != null && clickedBlock.getType() == Material.NOTE_BLOCK) {
        // Annule l'événement pour empêcher de changer la note
        event.setCancelled(true);
      }
    }

    if (Minecraft.compareItem(event.getItem(), new ItemBuilder(ItemsPreset.itemDash.getItem()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.dash[/lang]")).toItemStack())) {
      player.setVelocity(player.getEyeLocation().getDirection().multiply(3));
      player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1, 1);
    }
    else if (Minecraft.compareItem(event.getItem(), new ItemBuilder(ItemsPreset.itemConfig.getItem()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.config[/lang]")).toItemStack())) {
      McTools.getService(GuiManager.class).open(player, McTools.getService(GuiManager.class).getGuiConfig().getMainGui().getClass());
      SoundManager.playSound(event.getPlayer(), null, "danganronpa:open", SoundCategory.MASTER, 1.0F, 1.0F);
    }

  }

  @EventHandler
  public void titleAtInteractPlayer(PlayerInteractAtEntityEvent event) {

    //Empecher la double interaction lors d'un clic droit.
    if (event.getHand() != EquipmentSlot.HAND) {
      return;
    }

    if (event.getRightClicked() instanceof ArmorStand) {
      ArmorStand armorStand = (ArmorStand) event.getRightClicked();
      MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

      if (MTPlayer != null && ArmorManager.getArmor(armorStand) == null && !event.getPlayer().isSneaking()) {

        if (ArmorManager.haveTagSeat(armorStand)) ArmorManager.addArmor(armorStand, event.getPlayer(), Pose.SITTING);
        else if (ArmorManager.haveTagSleep(armorStand)) ArmorManager.addArmor(armorStand, event.getPlayer(), Pose.SLEEPING);
        return;
      }
    }

    if (McTools.getCodex().isBuildMode()) {

      if (event.getRightClicked() instanceof ArmorStand && event.getPlayer().isSneaking()) {

        MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

        if (MTPlayer != null) {

          if (MTPlayer.getArmorStandManager().getIfArmorStandSelected((ArmorStand) event.getRightClicked()) == null)

            if (MTPlayer.getArmorStandManager().getIfArmorStandLocked((ArmorStand) event.getRightClicked()) == null) MTPlayer.getArmorStandManager().addArmorStandSelected((ArmorStand) event.getRightClicked());
            else MTPlayer.getPlayer().sendMessage("§cErreur: §fVous ne pouvez pas sélectionner un armor stand verrouillé.");
          else MTPlayer.getArmorStandManager().removeArmorStandSelected((ArmorStand) event.getRightClicked(), true);
        }

      }
      event.setCancelled(true);
    }

  }

  @EventHandler
  public void onNotePlay(NotePlayEvent event) {
    // Vérifie si le bloc est un bloc de note
    if (event.getBlock().getType() == Material.NOTE_BLOCK) {
      // Annule l'événement pour empêcher de changer la note
      event.setCancelled(true);
    }
  }

  public Set<Material> getStopTypeInteract() {
    return stopTypeInteract;
  }

  public void setStopTypeInteract(Set<Material> stopTypeInteract) {
    this.stopTypeInteract = stopTypeInteract;
  }

  public void addStopTypeInteract(Material material) {
    this.stopTypeInteract.add(material);
  }

}
