package fr.dreamin.dreamintools.api.player;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions;
import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.dependency.PaperDependencyService;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OpenAudioManager {

    public static String playSound(Player player, String link, boolean loop, String id, SoundType soundType) {

        DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

        if (!McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) return "";

        MediaOptions options = new MediaOptions();
        options.setLoop(loop);
        options.setVolume(dtPlayer.getVoiceManager().getVolume(soundType));
        options.setId(id);

        if (dtPlayer.getVoiceManager().getClientConnection() != null) {
            AudioApi.getInstance().getMediaApi().playMedia(dtPlayer.getVoiceManager().getClientConnection(), link, options);
            return options.getId();
        }
        else return "";

    }

    public static String playSound(Player player, Location location, String link, int radius, boolean useSurroundSound, int obstructions) {

        DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

        if (!McTools.getService(PaperDependencyService.class).isPluginEnabled("OpenAudioMc")) return "";

        if (dtPlayer.getVoiceManager().getClientConnection() != null)  return AudioApi.getInstance().getMediaApi().playSpatialSound(dtPlayer.getVoiceManager().getClientConnection(), link, location.blockX(), location.blockY(), location.blockZ(), radius, useSurroundSound, obstructions);
        else return "";

    }
}
