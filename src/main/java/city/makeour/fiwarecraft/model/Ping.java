package city.makeour.fiwarecraft.model;

import javax.json.bind.annotation.JsonbProperty;

public class Ping extends NgsiV2Entity {

    @JsonbProperty("timestamp")
    public String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    @JsonbProperty("status")
    public String getStatus() {
        return "ok";
    }
}
