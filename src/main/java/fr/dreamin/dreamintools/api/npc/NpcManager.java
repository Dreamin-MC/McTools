package fr.dreamin.dreamintools.api.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.SkinTrait;

import java.util.ArrayList;
import java.util.List;

public class NpcManager {

  private static final NpcManager npcManager = new NpcManager();

  private final NPCRegistry registry = CitizensAPI.getNPCRegistry();

  public static String getSkinName(NPC npc) {
    SkinTrait skinTrait = npc.getTrait(SkinTrait.class);
    return skinTrait.getSkinName();
  }

  public static void removeAllCitizensNPCs() {
    NPCRegistry registry = CitizensAPI.getNPCRegistry();
    List<NPC> npcsToDelete = new ArrayList<>();

    // Collecter les NPCs dans une liste
    for (NPC npc : registry) {
      npcsToDelete.add(npc);
    }
    // Supprimer les NPCs après avoir terminé l'itération
    for (NPC npc : npcsToDelete) {
      npc.destroy();
    }
  }

}
