package controllers.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EventInstallOrUninstall {
    public class Data {

        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("userId")
        @Expose
        private String userId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "productId='" + productId + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "EventInstallOrUninstal{" +
                "data=" + data +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", version=" + version +
                ", timestamp=" + timestamp +
                '}';
    }
}
