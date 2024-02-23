package fr.dreamin.dreamintools.api.npc;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.entity.EntityListener;
import net.citizensnpcs.api.npc.NPC;

public class NpcListener {

  private NPC npc;
  private EntityListener entityListener;

  public NpcListener(NPC npc) {
    this.npc = npc;
    this.entityListener = new EntityListener(npc.getEntity());
    McTools.getInstance().getServer().getPluginManager().registerEvents(entityListener, McTools.getInstance());
  }

  public void addCLickEvent(EntityListener.EntityRunnable runnable) {
    entityListener.add(runnable);
  }

  public void removeClickEvent(EntityListener.EntityRunnable runnable) {
    entityListener.remove(runnable);
  }

  public NPC getNpc() {
    return npc;
  }

}
