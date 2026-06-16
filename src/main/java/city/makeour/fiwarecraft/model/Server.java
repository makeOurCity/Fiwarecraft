package city.makeour.fiwarecraft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Server extends NgsiV2Entity {

    public static final String JSON_PROPERTY_PLAYER_COUNT = "playerCount";
    private int playerCount;

    public static final String JSON_PROPERTY_HEATMAP = "heatmap";
    private Map<String, Integer> heatmap;

    public static final String JSON_PROPERTY_STATUS = "status";
    private String status;

    public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
    private LocalDateTime updatedAt;

    public Server() {
        this.playerCount = 0;
        this.heatmap = new HashMap<>();
        this.status = "OFFLINE";
    }

    public Server playerCount(int playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public Server heatmap(Map<String, Integer> heatmap) {
        this.heatmap = heatmap;
        return this;
    }

    public Server status(String status) {
        this.status = status;
        return this;
    }

    public Server updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Nonnull
    @JsonProperty(JSON_PROPERTY_PLAYER_COUNT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public int getPlayerCount() {
        return this.playerCount;
    }

    @JsonProperty(JSON_PROPERTY_PLAYER_COUNT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    @Nonnull
    @JsonProperty(JSON_PROPERTY_HEATMAP)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public Map<String, Integer> getHeatmap() {
        return this.heatmap;
    }

    @JsonProperty(JSON_PROPERTY_HEATMAP)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setHeatmap(Map<String, Integer> heatmap) {
        this.heatmap = heatmap;
    }

    @Nonnull
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public String getStatus() {
        return this.status;
    }

    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
