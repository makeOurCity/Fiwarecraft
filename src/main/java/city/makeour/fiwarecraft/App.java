package city.makeour.fiwarecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
  private Map<String, Integer> lastGridCounts = new HashMap<>();

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

    // ヒートマップ更新を60秒ごとに実行（CPU削減）
    getServer().getScheduler().scheduleSyncRepeatingTask(this,
      () -> updatePlayerHeatmap(), 0L, 1200L);

    getLogger().info("Send ping");
  }


  private void updatePlayerHeatmap() {
    Map<String, Integer> gridCounts = new HashMap<>();
    int totalPlayers = 0;

    for (Player player : getServer().getOnlinePlayers()) {
      totalPlayers++;
      String gridId = getGridId(player);
      gridCounts.put(gridId, gridCounts.getOrDefault(gridId, 0) + 1);
    }

    if (!Objects.equals(lastGridCounts, gridCounts)) {
      this.mocClient.sendServerData(BASE_ENTITY_ID, gridCounts);
      this.mocClient.sendPlayerCount(BASE_ENTITY_ID, totalPlayers);
      lastGridCounts = new HashMap<>(gridCounts);
      getLogger().info("Updated heatmap and player count: " + totalPlayers + " players");
    }
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