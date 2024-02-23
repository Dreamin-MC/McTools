package fr.dreamin.dreamintools.generic.modules.listeners.interact;

import fr.dreamin.dreamintools.generic.service.Service;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FilterInteractService extends Service {

  private List<CustomFilterInteractFunction> customFilterFunctions;

  @Override
  public void onEnable() {
    customFilterFunctions = new ArrayList<>();
  }

  public void addFilterFunction(CustomFilterInteractFunction customFilterFunction){
    customFilterFunctions.add(customFilterFunction);
  }
  public List<CustomFilterInteractFunction> getCustomFilterFunctions(){
    return new ArrayList<>(customFilterFunctions);
  }
}