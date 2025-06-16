package city.makeour.fiwarecraft;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.bukkit.plugin.java.JavaPlugin;

import city.makeour.fiwarecraft.client.FcMocClient;
import city.makeour.moc.MocClient;

/**
 * Fiwarecraft plugin main class
 */
public class App extends JavaPlugin {

  protected FcMocClient mocClient;

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

    this.mocClient = new FcMocClient(new MocClient());
    boolean authenticated = this.mocClient.auth();
    if (!authenticated) {
      getLogger().severe("Authentication failed. Please check your environment variables for Cognito credentials.");
      return;
    }
    this.mocClient.sendPing(" urn:ngsi-ld:ping:test-serer-001", true);

    getLogger().info("Send ping");
  }

  @Override
  public void onDisable() {
    getLogger().info("Fiwarecraft plugin has been disabled!");
  }
}