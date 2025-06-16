package city.makeour.fiwarecraft.client;

import city.makeour.moc.MocClient;
import city.makeour.fiwarecraft.model.Ping;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FcMocClientTest {

    @Mock
    private MocClient mockMocClient;

    private FcMocClient fcMocClient;

    @Before
    public void setUp() {
        fcMocClient = new FcMocClient(mockMocClient);
        fcMocClient.auth();
    }

    @Test
    public void testConstructorWithDefaultService() {
        FcMocClient client = new FcMocClient(mockMocClient);
        assertEquals("fiwarecraft", client.getFiwareService());
    }

    @Test
    public void testConstructorWithCustomService() {
        FcMocClient client = new FcMocClient(mockMocClient, "custom-service");
        assertEquals("custom-service", client.getFiwareService());
    }

    @Test
    public void testSetAndGetFiwareService() {
        fcMocClient.setFiwareService("test-service");
        assertEquals("test-service", fcMocClient.getFiwareService());
    }

    @Test
    public void testSendPingWithIdAndStatus() throws Exception {
        String entityId = "test-ping-001";
        boolean status = true;

        fcMocClient.sendPing(entityId, status);

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity(eq("application/json"), any(Ping.class));
    }

    @Test
    public void testSendPingWithPingEntity() throws Exception {
        LocalDateTime testTime = LocalDateTime.of(2023, 12, 25, 14, 30, 45);
        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-002");
        pingEntity.setLastSucceededAt(testTime);
        pingEntity.setStatus(true);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
        assertEquals("Ping", pingEntity.getType());
        assertEquals("test-ping-002", pingEntity.getId());
        assertEquals(testTime, pingEntity.getLastSucceededAt());
        assertTrue(pingEntity.isStatus());
    }

    @Test
    public void testSendPingSetsDefaultType() throws Exception {
        Ping pingEntity = new Ping();
        pingEntity.setId("test-ping-003");
        pingEntity.setStatus(false);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
        assertEquals("Ping", pingEntity.getType());
    }

    @Test
    public void testSendPingSetsDefaultTimestamp() throws Exception {
        LocalDateTime beforeCall = LocalDateTime.now();

        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-004");
        pingEntity.setStatus(true);

        fcMocClient.sendPing(pingEntity);

        LocalDateTime afterCall = LocalDateTime.now();

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
        assertNotNull(pingEntity.getLastSucceededAt());
        assertTrue(pingEntity.getLastSucceededAt().isAfter(beforeCall.minusSeconds(1)));
        assertTrue(pingEntity.getLastSucceededAt().isBefore(afterCall.plusSeconds(1)));
    }

    @Test
    public void testSendPingWithCustomService() throws Exception {
        fcMocClient.setFiwareService("custom-service");

        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-005");
        pingEntity.setStatus(true);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, times(1)).setFiwareService("custom-service");
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
    }

    @Test
    public void testSendPingHandlesException() throws Exception {
        Exception testException = new RuntimeException("Network error");
        doThrow(testException).when(mockMocClient).createEntity(anyString(), any(Ping.class));

        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-error");
        pingEntity.setStatus(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            fcMocClient.sendPing(pingEntity);
        });

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        assertEquals("Network error", thrown.getMessage());
    }

    @Test
    public void testSendPingPreservesExistingValues() throws Exception {
        LocalDateTime existingTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);

        Ping pingEntity = new Ping();
        pingEntity.setType("CustomPing");
        pingEntity.setId("test-ping-preserve");
        pingEntity.setLastSucceededAt(existingTime);
        pingEntity.setStatus(false);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
        assertEquals("CustomPing", pingEntity.getType());
        assertEquals(existingTime, pingEntity.getLastSucceededAt());
        assertFalse(pingEntity.isStatus());
    }

    @Test
    public void testSendPingWithIdAndStatusCreatesCorrectEntity() throws Exception {
        LocalDateTime beforeCall = LocalDateTime.now();

        fcMocClient.sendPing("simple-ping", false);

        LocalDateTime afterCall = LocalDateTime.now();

        verify(mockMocClient, times(1)).setFiwareService("fiwarecraft");
        verify(mockMocClient, times(1)).createEntity(eq("application/json"), argThat(ping -> {
            Ping p = (Ping) ping;
            return "Ping".equals(p.getType()) &&
                    "simple-ping".equals(p.getId()) &&
                    !p.isStatus() &&
                    p.getLastSucceededAt() != null &&
                    p.getLastSucceededAt().isAfter(beforeCall.minusSeconds(1)) &&
                    p.getLastSucceededAt().isBefore(afterCall.plusSeconds(1));
        }));
    }

    @Test
    public void testSendPingWithNullService() throws Exception {
        fcMocClient.setFiwareService(null);

        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-null-service");
        pingEntity.setStatus(true);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, never()).setFiwareService(anyString());
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
    }

    @Test
    public void testSendPingWithEmptyService() throws Exception {
        fcMocClient.setFiwareService("");

        Ping pingEntity = new Ping();
        pingEntity.setType("Ping");
        pingEntity.setId("test-ping-empty-service");
        pingEntity.setStatus(true);

        fcMocClient.sendPing(pingEntity);

        verify(mockMocClient, never()).setFiwareService(anyString());
        verify(mockMocClient, times(1)).createEntity("application/json", pingEntity);
    }

    @Test
    @EnabledIfEnvironmentVariables({
            @EnabledIfEnvironmentVariable(named = "TEST_COGNITO_USER_POOL_ID", matches = ".*"),
            @EnabledIfEnvironmentVariable(named = "TEST_COGNITO_CLIENT_ID", matches = ".*"),
            @EnabledIfEnvironmentVariable(named = "TEST_COGNITO_USERNAME", matches = ".*"),
            @EnabledIfEnvironmentVariable(named = "TEST_COGNITO_PASSWORD", matches = ".*")
    })
    public void testSendPingWithRealClient() {
        FcMocClient realClient = new FcMocClient(new MocClient());
        boolean authenticated = realClient.auth();
        assertTrue("Authentication should succeed with valid credentials", authenticated);

        String entityId = "real-ping-001";
        boolean status = true;

        realClient.sendPing(entityId, status);
        // Here you would typically verify the entity was created in the actual MOC
        // service
    }
}