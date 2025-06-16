package city.makeour.fiwarecraft.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class PingTest {

    private Ping ping;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        ping = new Ping();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testSetAndGetLastSucceededAt() {
        LocalDateTime testDateTime = LocalDateTime.of(2023, 12, 25, 14, 30, 45);
        ping.setLastSucceededAt(testDateTime);
        assertEquals(testDateTime, ping.getLastSucceededAt());
    }

    @Test
    public void testSetAndGetStatus() {
        ping.setStatus(true);
        assertEquals("OK", ping.getStatus());
        
        ping.setStatus(false);
        assertEquals("NG", ping.getStatus());
    }

    @Test
    public void testJsonSerializationWithDateTime() throws Exception {
        LocalDateTime testDateTime = LocalDateTime.of(2023, 12, 25, 14, 30, 45);
        ping.setType("Ping");
        ping.setId("ping-001");
        ping.setLastSucceededAt(testDateTime);
        ping.setStatus(true);

        String json = objectMapper.writeValueAsString(ping);
        
        assertTrue(json.contains("\"type\":\"Ping\""));
        assertTrue(json.contains("\"id\":\"ping-001\""));
        assertTrue(json.contains("\"lastSucceededAt\":\"2023-12-25T14:30:45\""));
        assertTrue(json.contains("\"status\":\"OK\""));
    }

    @Test
    public void testJsonDeserializationWithDateTime() throws Exception {
        String json = "{\"type\":\"Ping\",\"id\":\"ping-002\",\"lastSucceededAt\":\"2023-12-25T14:30:45\",\"status\":\"OK\"}";
        
        Ping deserializedPing = objectMapper.readValue(json, Ping.class);
        
        assertEquals("Ping", deserializedPing.getType());
        assertEquals("ping-002", deserializedPing.getId());
        assertEquals(LocalDateTime.of(2023, 12, 25, 14, 30, 45), deserializedPing.getLastSucceededAt());
        assertEquals("OK", deserializedPing.getStatus());
        assertTrue(deserializedPing.isStatus());
    }

    @Test
    public void testJsonSerializationWithNullDateTime() throws Exception {
        ping.setType("Ping");
        ping.setId("ping-003");
        ping.setStatus(false);

        String json = objectMapper.writeValueAsString(ping);
        
        assertTrue(json.contains("\"type\":\"Ping\""));
        assertTrue(json.contains("\"id\":\"ping-003\""));
        assertTrue(json.contains("\"lastSucceededAt\":null"));
        assertTrue(json.contains("\"status\":\"NG\""));
    }

    @Test
    public void testInheritanceFromNgsiV2Entity() {
        assertTrue(ping instanceof NgsiV2Entity);
        
        ping.setType("PingEntity");
        ping.setId("test-ping");
        
        assertEquals("PingEntity", ping.getType());
        assertEquals("test-ping", ping.getId());
    }

    @Test
    public void testFluentIdMethodFromParent() {
        Ping result = (Ping) ping.id("fluent-ping-id");
        
        assertSame(ping, result);
        assertEquals("fluent-ping-id", ping.getId());
    }

    @Test
    public void testDateTimeHandlingWithCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        ping.setLastSucceededAt(now);
        
        LocalDateTime retrieved = ping.getLastSucceededAt();
        assertEquals(now, retrieved);
    }

    @Test
    public void testStatusBooleanToStringConversion() {
        ping.setStatus(true);
        assertEquals("OK", ping.getStatus());
        assertTrue(ping.isStatus());
        
        ping.setStatus(false);
        assertEquals("NG", ping.getStatus());
        assertFalse(ping.isStatus());
    }

    @Test
    public void testStatusStringToBoolean() {
        ping.setStatusFromString("OK");
        assertTrue(ping.isStatus());
        assertEquals("OK", ping.getStatus());
        
        ping.setStatusFromString("NG");
        assertFalse(ping.isStatus());
        assertEquals("NG", ping.getStatus());
        
        ping.setStatusFromString("INVALID");
        assertFalse(ping.isStatus());
        assertEquals("NG", ping.getStatus());
    }
}