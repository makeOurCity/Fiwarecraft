package city.makeour.fiwarecraft;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
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
  private static final String BASE_ENTITY_ID = "test-server-001";
  private static final int GRID_SIZE = 64; // グリッドサイズ（ブロック単位）

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
    this.mocClient.sendPing(BASE_ENTITY_ID, true);
    getServer().getPluginManager().registerEvents(this, this);

    // ヒートマップ更新を5秒ごとに実行
    getServer().getScheduler().scheduleSyncRepeatingTask(this,
      () -> updatePlayerHeatmap(), 0L, 100L);

    getLogger().info("Send ping");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    int count = getServer().getOnlinePlayers().size();
    getLogger().info("Player joined: " + event.getPlayer().getName() + " (online: " + count + ")");
    this.mocClient.sendPlayerCount(BASE_ENTITY_ID, count);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    // Quit時点ではまだプレイヤーが含まれているので -1
    int count = getServer().getOnlinePlayers().size() - 1;
    getLogger().info("Player quit: " + event.getPlayer().getName() + " (online: " + count + ")");
    this.mocClient.sendPlayerCount(BASE_ENTITY_ID, count);
  }

  private void updatePlayerHeatmap() {
    Map<String, Integer> gridCounts = new HashMap<>();

    for (Player player : getServer().getOnlinePlayers()) {
      String gridId = getGridId(player);
      gridCounts.put(gridId, gridCounts.getOrDefault(gridId, 0) + 1);
    }

    this.mocClient.sendServerData(BASE_ENTITY_ID, gridCounts);
  }

  private String getGridId(Player player) {
    int gridX = (int) player.getLocation().getX() / GRID_SIZE;
    int gridZ = (int) player.getLocation().getZ() / GRID_SIZE;
    int gridY = (int) player.getLocation().getY() / GRID_SIZE;
    return String.format("urn:ngsi-ld:Heatmap:grid-%d-%d-%d", gridX, gridY, gridZ);
  }

  @Override
  public void onDisable() {
    getLogger().info("Fiwarecraft plugin has been disabled!");
    if (this.mocClient != null) {
      this.mocClient.sendPing(BASE_ENTITY_ID, false);
      getLogger().info("Send ping (offline)");
    }
  }
}