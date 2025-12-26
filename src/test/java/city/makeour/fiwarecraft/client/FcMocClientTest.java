package city.makeour.fiwarecraft.client;

import city.makeour.moc.MocClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FcMocClientTest {

    @Mock
    private MocClient mockMocClient;

    private FcMocClient fcMocClient;

    @Before
    public void setUp() {
        // テストごとに FcMocClient を初期化
        fcMocClient = new FcMocClient(mockMocClient);
    }

    @Test
    public void testConstructorWithDefaultService() {
        // デフォルトの Fiware-Service 名が正しくセットされているか
        assertEquals("fiwarecraft", fcMocClient.getFiwareService());
    }

    @Test
    public void testConstructorWithCustomService() {
        // カスタムの Fiware-Service 名が正しくセットされているか
        FcMocClient client = new FcMocClient(mockMocClient, "custom-service");
        assertEquals("custom-service", client.getFiwareService());
    }

    @Test
    public void testSetAndGetFiwareService() {
        // 途中でサービス名を変更できるか
        fcMocClient.setFiwareService("test-service");
        assertEquals("test-service", fcMocClient.getFiwareService());
    }

    @Test
    public void testSendPingWithIdAndStatus() throws Exception {
        String entityId = "test-ping-001";
        boolean status = true;

        fcMocClient.sendPing(entityId, status);

        // 1. Fiware-Service ヘッダーがセットされたか検証
        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        
        // 2. updateEntity が正しい引数 (ID, Type, Map) で呼ばれたか検証
        // 第3引数の Map には時刻とステータスが入っているため any(Map.class) で検証
        verify(mockMocClient, times(1)).updateEntity(eq(entityId), eq("Ping"), any(Map.class));
    }

    @Test
    public void testSendPingWithCustomService() throws Exception {
        // サービス名を変更した状態で送信した場合
        fcMocClient.setFiwareService("custom-service");
        fcMocClient.sendPing("test-id", true);

        verify(mockMocClient, times(1)).setFiwareService("custom-service");
        verify(mockMocClient, times(1)).updateEntity(anyString(), eq("Ping"), any(Map.class));
    }

    @Test
    public void testSendPingWithNullService() throws Exception {
        // サービス名が null の場合、ヘッダー設定がスキップされるか
        fcMocClient.setFiwareService(null);
        fcMocClient.sendPing("test-id", true);

        verify(mockMocClient, never()).setFiwareService(anyString());
        verify(mockMocClient, times(1)).updateEntity(anyString(), eq("Ping"), any(Map.class));
    }

    @Test
    public void testSendPingWithEmptyService() throws Exception {
        // サービス名が空文字の場合、ヘッダー設定がスキップされるか
        fcMocClient.setFiwareService("");
        fcMocClient.sendPing("test-id", true);

        verify(mockMocClient, never()).setFiwareService(anyString());
        verify(mockMocClient, times(1)).updateEntity(anyString(), eq("Ping"), any(Map.class));
    }
}