package city.makeour.fiwarecraft;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

/**
 * Unit test for App plugin.
 */
public class AppTest {

  @SuppressWarnings("unused")
  private ServerMock server;
  private App plugin;

  @Before
  public void setUp() {
    // MockBukkitサーバーを起動
    server = MockBukkit.mock();
    // プラグインをロード
    plugin = MockBukkit.load(App.class);
  }

  @After
  public void tearDown() {
    // MockBukkitサーバーをシャットダウン
    MockBukkit.unmock();
  }

  @Test
  public void testPluginEnabled() {
    // プラグインが正しくロードされていることを確認
    assertTrue(plugin.isEnabled());
  }

  @Test
  public void testMocClientInitialized() {
    // MocClientが正しく初期化されていることを確認
    assertNotNull(plugin.mocClient);
  }
}