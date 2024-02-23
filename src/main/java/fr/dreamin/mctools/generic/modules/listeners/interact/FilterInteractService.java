package fr.dreamin.mctools.generic.modules.listeners.interact;

import fr.dreamin.mctools.generic.service.Service;

import java.util.ArrayList;
import java.util.List;

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