package fr.dreamin.mctools.api.voice;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.api.impl.event.events.ClientRequestVoiceEvent;
import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.spigot.modules.voicechat.filters.FilterService;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class VoiceWallManager extends Service {

  private BukkitTask bukkitTask;

  @Override
  public void onEnable() {
    super.onEnable();
    new BukkitRunnable() {
      @Override
      public void run() {
        if (McTools.getCodex().isVoiceWallMode()) {
          startRunTask();
          addFilter();
        }
        startEvents();
      }
    }.runTaskLater(McTools.getInstance(), 40);
  }

  private void startEvents() {
    AudioApi.getInstance().getEventDriver().on(ClientRequestVoiceEvent.class).setHandler(event -> {
      if (McTools.getService(GuiManager.class).getGuiConfig().getMainGui() != null) McTools.getService(GuiManager.class).getGuiConfig().openGuiForAll(McTools.getService(GuiManager.class).getGuiConfig().getMainGui().getClass());
    });
  }

  private void startRunTask() {
    this.bukkitTask = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {

      PlayersService playersService = McTools.getService(PlayersService.class);

      for (MTPlayer MTPlayer : playersService.getDTPlayers()) {
        for (MTPlayer otherMTPlayer : playersService.getDTPlayers()) {
          if (otherMTPlayer.equals(MTPlayer)) continue;

          //get if player is dead and the otherPlayer is not dead
          if (playersService.getSpectators().contains(MTPlayer) && !playersService.getSpectators().contains(otherMTPlayer)) MTPlayer.getVoiceManager().getClientConnection().setModerating(true);

          //get if player is not dead and the otherPlayer is dead
          else if (!playersService.getSpectators().contains(MTPlayer) && playersService.getSpectators().contains(otherMTPlayer)) otherMTPlayer.getVoiceManager().getClientConnection().setModerating(true);
          else {
            double distance = MTPlayer.getPlayer().getLocation().distance(otherMTPlayer.getPlayer().getLocation());
            if (distance <= McTools.getCodex().getVoiceDistanceMax() && (RayCast. hasLineOfSight(MTPlayer.getPlayer(), otherMTPlayer.getPlayer()))) {
              if (MTPlayer.getVoiceManager().getDTPlayersSpeaker().contains(otherMTPlayer)) continue;
              else if (otherMTPlayer.getVoiceManager().getDTPlayersSpeaker().contains(MTPlayer)) continue;
              MTPlayer.getVoiceManager().getDTPlayersSpeaker().add(otherMTPlayer);
              otherMTPlayer.getVoiceManager().getDTPlayersSpeaker().add(MTPlayer);
            }
            else {
              MTPlayer.getVoiceManager().getDTPlayersSpeaker().remove(otherMTPlayer);
              otherMTPlayer.getVoiceManager().getDTPlayersSpeaker().remove(MTPlayer);
            }
          }

        }

      }

    }, 0, 1);

  }

  private void addFilter() {

//    OpenAudioMc.getService(FilterService.class).addFilterFunction((listener, possibleSpeaker) -> {
//      DTPlayer vPlayer = Main.getVoice().getPlayerManager().getPlayer(listener);
//      DTPlayer otherDTPlayer = Main.getVoice().getPlayerManager().getPlayer(possibleSpeaker);
//
//      if (vPlayer.getPlayer().getName().equals("ScravenPro")) return false;
//      else return true;
//    });

    PlayersService playersService = McTools.getService(PlayersService.class);

    OpenAudioMc.getService(FilterService.class).addFilterFunction((listener, possibleSpeaker) -> {
      MTPlayer MTPlayer = playersService.getPlayer(listener);
      MTPlayer otherMTPlayer = playersService.getPlayer(possibleSpeaker);

      if (!playersService.getSpectators().contains(MTPlayer) && !playersService.getSpectators().contains(otherMTPlayer)) {
        if (MTPlayer.getVoiceManager().getDTPlayersSpeaker().contains(otherMTPlayer)) return true;
        else return false;
      }
      else return true;
    });
  }

  public void stopRunTask() {
    if (bukkitTask != null) bukkitTask.cancel();
  }

}
