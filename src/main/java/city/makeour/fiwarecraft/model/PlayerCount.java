package city.makeour.fiwarecraft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;

public class PlayerCount extends NgsiV2Entity {

    public static final String JSON_PROPERTY_COUNT = "count";
    private int count;

    public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
    private LocalDateTime updatedAt;

    public PlayerCount() {
        this.count = 0;
    }

    public PlayerCount count(int count) {
        this.count = count;
        return this;
    }

    public PlayerCount updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Nonnull
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public int getCount() {
        return this.count;
    }

    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setCount(int count) {
        this.count = count;
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
