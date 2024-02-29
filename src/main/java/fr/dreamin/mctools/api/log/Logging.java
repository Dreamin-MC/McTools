package fr.dreamin.mctools.api.log;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.Version;
import fr.dreamin.mctools.api.service.Service;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.logging.Logger;

public class Logging extends Service {
  private Logger logger;
  private Plugin plugin;

  @Override
  public void onEnable() {
    super.onEnable();
    this.plugin = McTools.getInstance();
    this.logger = plugin.getLogger();
  }

  public void info(String info) {
    this.logger.info(info);
  }

  public void warn(String warn) {
    this.logger.warning(warn);
  }

  public void error(String error) {
    this.logger.severe(error);
  }

  public void stack(String error, String fix) {
    this.stack(error, fix, (Throwable)null);
  }

  public void stack(String error, Throwable throwable) {
    this.stack(error, (String)null, throwable);
  }

  public void stack(String error, @Nullable String fix, @Nullable Throwable throwable) {
    this.error("##");
    this.error("## %s has encountered a critical error!".formatted(this.plugin.getName()));
    this.error("## %s".formatted(error));
    this.error("##");
    if (throwable == null) this.error("## No stack trace provided");
    else {
      this.error("## Stack trace:");
      this.error("## %s".formatted(throwable));
      StackTraceElement[] stack = throwable.getStackTrace();
      StackTraceElement[] var5 = stack;
      int var6 = stack.length;

      for(int var7 = 0; var7 < var6; ++var7) {
        StackTraceElement stackTraceElement = var5[var7];
        this.error("##\t" + stackTraceElement.toString());
      }
    }

    this.error("##");
    if (fix == null) {
      this.error("## This is probably not your fault.");
      this.error("## Contact the developer to fix it.");
      this.error("## Be sure to send the entire error while reporting.");
    }
    else {
      this.error("## This is probably not your fault, but you may be able to fix it.");
      this.error("## You should try: %s".formatted(fix));
      this.error("## Contact the developer if this doesn't work.");
      this.error("## Be sure to send the entire error while reporting.");
    }

    this.error("##");
    this.error("## Version information:");
    this.error("##\tPlugin Version: %s".formatted(this.plugin.getDescription().getVersion()));
    this.error("##\tMcTools Version: %s".formatted(McTools.getInstance().getDescription().getVersion()));
    this.error("##\tMinecraft: %s".formatted(Version.getVersion().name().replaceAll("_", ".")));
    this.error("##");
  }
}

