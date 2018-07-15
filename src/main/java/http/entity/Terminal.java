
package http.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terminal {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("storeUuid")
    @Expose
    private String storeUuid;
    @SerializedName("timezoneOffset")
    @Expose
    private Integer timezoneOffset;
    @SerializedName("imei")
    @Expose
    private String imei;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}


