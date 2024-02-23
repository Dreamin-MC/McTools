package fr.dreamin.mctools.api.voice;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.api.impl.event.events.ClientRequestVoiceEvent;
import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.spigot.modules.voicechat.filters.FilterService;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.paper.services.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class VoiceWallManager {

  private BukkitTask bukkitTask;

  public VoiceWallManager() {

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
      if (McTools.getInstance().getGuiManager().getGuiConfig().getMainGui() != null) McTools.getInstance().getGuiManager().getGuiConfig().openGuiForAll(McTools.getInstance().getGuiManager().getGuiConfig().getMainGui().getClass());
    });
  }

  private void startRunTask() {
    this.bukkitTask = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {

      PlayersService playersService = McTools.getService(PlayersService.class);

      for (DTPlayer dtPlayer : playersService.getDTPlayers()) {
        for (DTPlayer otherDTPlayer : playersService.getDTPlayers()) {
          if (otherDTPlayer.equals(dtPlayer)) continue;

          //get if player is dead and the otherPlayer is not dead
          if (playersService.getSpectators().contains(dtPlayer) && !playersService.getSpectators().contains(otherDTPlayer)) {
            dtPlayer.getVoiceManager().getClientConnection().setModerating(true);

          }
          //get if player is not dead and the otherPlayer is dead
          else if (!playersService.getSpectators().contains(dtPlayer) && playersService.getSpectators().contains(otherDTPlayer)) {
            otherDTPlayer.getVoiceManager().getClientConnection().setModerating(true);
          }
          else {
            double distance = dtPlayer.getPlayer().getLocation().distance(otherDTPlayer.getPlayer().getLocation());
            if (distance <= McTools.getCodex().getVoiceDistanceMax() && (RayCast. hasLineOfSight(dtPlayer.getPlayer(), otherDTPlayer.getPlayer()))) {

              if (dtPlayer.getVoiceManager().getDTPlayersSpeaker().contains(otherDTPlayer)) continue;
              else if (otherDTPlayer.getVoiceManager().getDTPlayersSpeaker().contains(dtPlayer)) continue;

              dtPlayer.getVoiceManager().getDTPlayersSpeaker().add(otherDTPlayer);
              otherDTPlayer.getVoiceManager().getDTPlayersSpeaker().add(dtPlayer);
            }
            else {
              dtPlayer.getVoiceManager().getDTPlayersSpeaker().remove(otherDTPlayer);
              otherDTPlayer.getVoiceManager().getDTPlayersSpeaker().remove(dtPlayer);
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
      DTPlayer dtPlayer = playersService.getPlayer(listener);
      DTPlayer otherDTPlayer = playersService.getPlayer(possibleSpeaker);

      if (!playersService.getSpectators().contains(dtPlayer) && !playersService.getSpectators().contains(otherDTPlayer)) {
        if (dtPlayer.getVoiceManager().getDTPlayersSpeaker().contains(otherDTPlayer)) {
          Bukkit.broadcastMessage(listener.getName() + " " + "can hear" + " " + possibleSpeaker.getName());
          return true;
        }
        else {
          return false;
        }
      }
      else {
        return true;
      }
    });
  }

  public void stopRunTask() {
    if (bukkitTask != null) {
      bukkitTask.cancel();
    }
  }



}
