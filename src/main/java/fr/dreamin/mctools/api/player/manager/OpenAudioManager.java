package fr.dreamin.mctools.api.player.manager;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.player.SoundType;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.dependency.PaperDependencyService;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OpenAudioManager {

  public static String playSound(Player player, String link, boolean loop, String id, SoundType soundType) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (!McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) return "";

    MediaOptions options = new MediaOptions();
    options.setLoop(loop);
    options.setVolume(MTPlayer.getVoiceManager().getVolume(soundType));
    options.setId(id);

    if (MTPlayer.getVoiceManager().getClientConnection() != null) {
      AudioApi.getInstance().getMediaApi().playMedia(MTPlayer.getVoiceManager().getClientConnection(), link, options);
      return options.getId();
    }
    else return "";
  }

  public static String playSound(Player player, Location location, String link, int radius, boolean useSurroundSound, int obstructions) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (!McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) return "";

    if (MTPlayer.getVoiceManager().getClientConnection() != null)  return AudioApi.getInstance().getMediaApi().playSpatialSound(MTPlayer.getVoiceManager().getClientConnection(), link, location.blockX(), location.blockY(), location.blockZ(), radius, useSurroundSound, obstructions);
    else return "";
  }
}
