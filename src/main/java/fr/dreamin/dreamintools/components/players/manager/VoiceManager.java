package fr.dreamin.dreamintools.components.players.manager;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.api.interfaces.Client;
import com.craftmend.openaudiomc.generic.client.objects.ClientConnection;
import com.craftmend.openaudiomc.generic.networking.interfaces.NetworkingService;
import fr.dreamin.dreamintools.api.player.SoundType;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.mysql.fetcher.UserFetcher.UserFetcher;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoiceManager {

  private Player player;
  private boolean isSelected = false;
  private boolean isForcedMute = false;
  private Client client;
  private final ClientConnection clientConnection;
  private int volumeAction;
  private int volumeAmbien;
  private int volumeEvent;
  private List<DTPlayer> vPlayersSpeaker = new ArrayList<>();

  public VoiceManager(DTPlayer dtPlayer) {
    this.player = dtPlayer.getPlayer();
    this.client = AudioApi.getInstance().getClient(dtPlayer.getPlayer().getUniqueId());
    this.clientConnection = OpenAudioMc.getService(NetworkingService.class).getClient(dtPlayer.getPlayer().getUniqueId());
  }

  public Client getClient() {
    return client;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public boolean isForcedMute() {
    return isForcedMute;
  }

  public void setForcedMutet(boolean forcedMute) {
    isForcedMute = forcedMute;
    getClient().forcefullyDisableMicrophone(forcedMute);
  }

  public int getVolumeAction() {
    return volumeAction;
  }

  public void setVolumeAction(int volumeAction) {
    this.volumeAction = volumeAction;
  }

  public int getVolumeAmbien() {
    return volumeAmbien;
  }

  public void setVolumeAmbien(int volumeAmbien) {
    this.volumeAmbien = volumeAmbien;
  }

  public int getVolumeEvent() {
    return volumeEvent;
  }

  public void setVolumeEvent(int volumeEvent) {
    this.volumeEvent = volumeEvent;
  }

  public ClientConnection getClientConnection() {
    return clientConnection;
  }

  public List<DTPlayer> getDTPlayersSpeaker() {
    return vPlayersSpeaker;
  }

  public int getVolume(SoundType soundType) {
    switch (soundType) {
      case ACTION:
        return getVolumeAction();
      case AMBIENT:
        return getVolumeAmbien();
      case EVENT:
        return getVolumeEvent();
      default:
        return 80;
    }
  }

}