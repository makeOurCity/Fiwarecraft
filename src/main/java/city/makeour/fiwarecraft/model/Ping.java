package city.makeour.fiwarecraft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDateTime;

public class Ping extends NgsiV2Entity {

    public static final String JSON_PROPERTY_LAST_SUCCEEDED_AT = "lastSucceededAt";
    private LocalDateTime lastSucceededAt;

    public static final String JSON_PROPERTY_STATUS = "status";
    private boolean status;

    @JsonProperty(JSON_PROPERTY_LAST_SUCCEEDED_AT)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setLastSucceededAt(LocalDateTime lastSucceededAt) {
        this.lastSucceededAt = lastSucceededAt;
    }

    @JsonProperty(JSON_PROPERTY_LAST_SUCCEEDED_AT)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getLastSucceededAt() {
        return this.lastSucceededAt;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @JsonGetter(JSON_PROPERTY_STATUS)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getStatus() {
        return this.status ? "OK" : "NG";
    }

    @JsonSetter(JSON_PROPERTY_STATUS)
    public void setStatusFromString(String statusString) {
        this.status = "OK".equals(statusString);
    }

    public boolean isStatus() {
        return this.status;
    }
}
