package fr.dreamin.mctools.components.players.filter.tick;

import fr.dreamin.mctools.components.players.DTPlayer;

public interface CustomFilterTick {

  void actualTick(DTPlayer dtPlayer, Integer tick);
}
