package city.makeour.fiwarecraft;

import org.bukkit.plugin.java.JavaPlugin;

import city.makeour.moc.MocClient;


/**
 * Fiwarecraft plugin main class
 */
public class App extends JavaPlugin {

  protected MocClient mocClient;

  /**
   * デフォルトのコンストラクタ
   */
  public App() {
    super();
  }

  /**
   * MockBukkitのテスト用コンストラクタ
   */
  protected App(org.bukkit.plugin.java.JavaPluginLoader loader, org.bukkit.plugin.PluginDescriptionFile description,
          java.io.File dataFolder, java.io.File file) {
    super(loader, description, dataFolder, file);
  }

  @Override
  public void onEnable() {
    getLogger().info("Fiwarecraft plugin has been enabled!");
    this.mocClient = new MocClient();
  }

  @Override
  public void onDisable() {
    getLogger().info("Fiwarecraft plugin has been disabled!");
  }
}