package city.makeour.fiwarecraft.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NgsiV2EntityTest {

    private NgsiV2Entity entity;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        entity = new NgsiV2Entity();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAndSetType() {
        String testType = "TestEntity";
        entity.setType(testType);
        assertEquals(testType, entity.getType());
    }

    @Test
    public void testGetAndSetId() {
        String testId = "test-id-123";
        entity.setId(testId);
        assertEquals(testId, entity.getId());
    }

    @Test
    public void testFluentIdMethod() {
        String testId = "fluent-id-456";
        NgsiV2Entity result = entity.id(testId);
        
        assertSame(entity, result);
        assertEquals(testId, entity.getId());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        entity.setType("TestType");
        entity.setId("test-123");

        String json = objectMapper.writeValueAsString(entity);
        
        assertTrue(json.contains("\"type\":\"TestType\""));
        assertTrue(json.contains("\"id\":\"test-123\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"type\":\"DeserializedType\",\"id\":\"deserialized-123\"}";
        
        NgsiV2Entity deserializedEntity = objectMapper.readValue(json, NgsiV2Entity.class);
        
        assertEquals("DeserializedType", deserializedEntity.getType());
        assertEquals("deserialized-123", deserializedEntity.getId());
    }

    @Test
    public void testJsonSerializationWithNullValues() throws Exception {
        String json = objectMapper.writeValueAsString(entity);
        
        assertTrue(json.contains("\"type\":null"));
        assertTrue(json.contains("\"id\":null"));
    }

    @Test
    public void testBuilderPattern() {
        NgsiV2Entity result = entity.id("builder-test");
        
        assertSame(entity, result);
        assertEquals("builder-test", entity.getId());
    }

    @Test
    public void testTypeAndIdIndependence() {
        entity.setType("Type1");
        entity.setId("Id1");
        
        assertEquals("Type1", entity.getType());
        assertEquals("Id1", entity.getId());
        
        entity.setType("Type2");
        assertEquals("Type2", entity.getType());
        assertEquals("Id1", entity.getId());
        
        entity.setId("Id2");
        assertEquals("Type2", entity.getType());
        assertEquals("Id2", entity.getId());
    }

    @Test
    public void testChainedFluentMethodCalls() {
        NgsiV2Entity result = entity.id("chained-id");
        
        assertSame(entity, result);
        assertEquals("chained-id", entity.getId());
    }
}