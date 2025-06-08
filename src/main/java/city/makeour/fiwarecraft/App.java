package city.makeour.fiwarecraft;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Fiwarecraft plugin main class
 */
public class App extends JavaPlugin {

  App() {
    super();
  }

  @Override
  public void onEnable() {
    getLogger().info("Fiwarecraft plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("Fiwarecraft plugin has been disabled!");
  }
}