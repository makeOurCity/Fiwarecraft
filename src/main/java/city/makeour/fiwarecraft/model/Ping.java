package city.makeour.fiwarecraft.model;

import javax.json.bind.annotation.JsonbProperty;

public class Ping extends NgsiV2Entity {

    public static final String JSON_PROPERTY_LAST_SUCCEEDED_AT = "lastSucceededAt";
    private String lastSucceededAt;

    public static final String JSON_PROPERTY_STATUS = "status";
    private boolean status;

    @JsonbProperty(JSON_PROPERTY_LAST_SUCCEEDED_AT)
    public void setLastSucceededAt(String lastSucceededAt) {
        this.lastSucceededAt = lastSucceededAt;
    }

    @JsonbProperty(JSON_PROPERTY_LAST_SUCCEEDED_AT)
    public String getLastSucceededAt() {
        return this.lastSucceededAt;
    }

    @JsonbProperty(JSON_PROPERTY_STATUS)
    public void setStatus(boolean status) {
        this.status = status;
    }

    @JsonbProperty(JSON_PROPERTY_STATUS)
    public String getStatus() {
        return this.status ? "OK" : "NG";
    }
}
