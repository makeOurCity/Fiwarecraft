package city.makeour.fiwarecraft.client;

import city.makeour.moc.MocClient;
import city.makeour.fiwarecraft.model.Ping;
import city.makeour.fiwarecraft.model.PlayerCount;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClient.ResponseSpec;

public class FcMocClient {

    protected MocClient mocClient;
    private String fiwareService;

    public FcMocClient(MocClient mocClient) {
        this.mocClient = mocClient;
        this.fiwareService = "fiwarecraft";
    }

    public FcMocClient(MocClient mocClient, String fiwareService) {
        this.mocClient = mocClient;
        this.fiwareService = fiwareService;
    }

    public boolean auth() {
        String cognitoUserPoolId = System.getenv("TEST_COGNITO_USER_POOL_ID");
        String cognitoClientId = System.getenv("TEST_COGNITO_CLIENT_ID");
        String username = System.getenv("TEST_COGNITO_USERNAME");
        String password = System.getenv("TEST_COGNITO_PASSWORD");

        System.out.println("Cognito User Pool ID: " + cognitoUserPoolId);

        if (cognitoUserPoolId == null || cognitoClientId == null || username == null || password == null) {
            return false;
        }

        this.mocClient.setMocAuthInfo(cognitoUserPoolId, cognitoClientId);
        try {
            this.mocClient.auth(username, password);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Authentication successful for user: " + username);

        return true;
    }

    /*
    public void sendPing(String entityId, boolean status) {
        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId(entityId);
        pingEntity.setLastSucceededAt(LocalDateTime.now());
        pingEntity.setStatus(status);

        sendPing(pingEntity);
    }

    public void sendPing(Ping pingEntity) {
        if (pingEntity.getType() == null) {
            pingEntity.setType("Ping");
        }

        if (pingEntity.getLastSucceededAt() == null) {
            pingEntity.setLastSucceededAt(LocalDateTime.now());
        }

        if (this.fiwareService != null && !this.fiwareService.isEmpty()) {
            mocClient.setFiwareService(this.fiwareService);
        }

        var resp = mocClient.createEntity("application/json", pingEntity);
        if (resp != null) {
            System.out.println("Response: " + resp.toString());
            System.out.println("Ping sent: " + resp.body(String.class));
        }
    }
    */

    public void sendPing(String entityId, boolean status) {
        // 1. Pingオブジェクトの生成（Mapではなくこちらを使う）
        Ping pingEntity = new Ping();
        pingEntity.setId(entityId);
        pingEntity.setType("Ping");
        pingEntity.setLastSucceededAt(LocalDateTime.now());
        pingEntity.setStatus(status);

        // 2. Fiware-Service ヘッダーの設定
        if (this.fiwareService != null && !this.fiwareService.isEmpty()) {
            mocClient.setFiwareService(this.fiwareService);
        }

        // 3. MocClientのupdateEntityを呼び出し（作成 or 更新を自動判別）
        var resp = mocClient.updateEntity(entityId, "Ping", pingEntity);

        // 4. レスポンスの確認
        if (resp != null) {
            System.out.println("Ping Upserted for ID: " + entityId);
        }
    }


    public void sendPlayerCount(String entityId, int count) {
        PlayerCount entity = new PlayerCount();
        entity.setId(entityId);
        entity.setType("PlayerCount");
        entity.setCount(count);
        entity.setUpdatedAt(LocalDateTime.now());

        if (this.fiwareService != null && !this.fiwareService.isEmpty()) {
            mocClient.setFiwareService(this.fiwareService);
        }

        var resp = mocClient.updateEntity(entityId, "PlayerCount", entity);
        if (resp != null) {
            System.out.println("PlayerCount Upserted: " + count);
        }
    }

    public void sendServerData(String entityId, Map<String, Integer> gridCounts) {
        Map<String, Object> heatmapGrids = new HashMap<>();
        for (String gridId : gridCounts.keySet()) {
            Map<String, Object> gridData = new HashMap<>();
            gridData.put("gridId", gridId);
            gridData.put("count", gridCounts.get(gridId));
            heatmapGrids.put(gridId, gridData);
        }

        Map<String, Object> serverData = new HashMap<>();
        serverData.put("id", entityId);
        serverData.put("type", "Server");
        serverData.put("heatmapGrids", heatmapGrids);
        serverData.put("recordedAt", LocalDateTime.now());

        if (this.fiwareService != null && !this.fiwareService.isEmpty()) {
            mocClient.setFiwareService(this.fiwareService);
        }

        var resp = mocClient.updateEntity(entityId, "Server", serverData);
        if (resp != null) {
            System.out.println("Server Data Sent: " + gridCounts.size() + " grids updated");
        }
    }

    public void setFiwareService(String fiwareService) {
        this.fiwareService = fiwareService;
    }

    public String getFiwareService() {
        return this.fiwareService;
    }
}
