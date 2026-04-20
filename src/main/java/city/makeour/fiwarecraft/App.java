package city.makeour.fiwarecraft;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import city.makeour.fiwarecraft.client.FcMocClient;
import city.makeour.moc.MocClient;

/**
 * Fiwarecraft plugin main class
 */
public class App extends JavaPlugin implements Listener {

  protected FcMocClient mocClient;
  private static final String PLAYER_COUNT_ENTITY_ID = "urn:ngsi-ld:PlayerCount:server-001";

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
    this.mocClient.sendPing("urn:ngsi-ld:ping:test-serer-001", true);
    getServer().getPluginManager().registerEvents(this, this);

    getLogger().info("Send ping");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    int count = getServer().getOnlinePlayers().size();
    getLogger().info("Player joined: " + event.getPlayer().getName() + " (online: " + count + ")");
    this.mocClient.sendPlayerCount(PLAYER_COUNT_ENTITY_ID, count);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    // Quit時点ではまだプレイヤーが含まれているので -1
    int count = getServer().getOnlinePlayers().size() - 1;
    getLogger().info("Player quit: " + event.getPlayer().getName() + " (online: " + count + ")");
    this.mocClient.sendPlayerCount(PLAYER_COUNT_ENTITY_ID, count);
  }

  @Override
  public void onDisable() {
    getLogger().info("Fiwarecraft plugin has been disabled!");
    if (this.mocClient != null) {
      // onEnableと同じ Entity ID を指定して、ステータスを false にして送信
      this.mocClient.sendPing("urn:ngsi-ld:ping:test-serer-001", false);
      getLogger().info("Send ping (offline)");
    }
  }
}