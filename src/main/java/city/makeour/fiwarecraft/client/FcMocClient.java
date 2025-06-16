package city.makeour.fiwarecraft.client;

import city.makeour.moc.MocClient;
import city.makeour.fiwarecraft.model.Ping;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

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

    public void auth() {
        String cognitoUserPoolId = System.getenv("TEST_COGNITO_USER_POOL_ID");
        String cognitoClientId = System.getenv("TEST_COGNITO_CLIENT_ID");
        String username = System.getenv("TEST_COGNITO_USERNAME");
        String password = System.getenv("TEST_COGNITO_PASSWORD");

        this.mocClient.setMocAuthInfo(cognitoUserPoolId, cognitoClientId);
        try {
            this.mocClient.auth(username, password);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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

        mocClient.createEntity("application/json", pingEntity);
    }

    public void setFiwareService(String fiwareService) {
        this.fiwareService = fiwareService;
    }

    public String getFiwareService() {
        return this.fiwareService;
    }
}
