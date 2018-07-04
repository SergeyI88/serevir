package controllers.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventSubscription {

    @SerializedName("subscriptionId")
    @Expose
    private String subscriptionId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("sequenceNumber")
    @Expose
    private Integer sequenceNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("trialPeriodDuration")
    @Expose
    private String trialPeriodDuration;
    @SerializedName("deviceNumber")
    @Expose
    private Integer deviceNumber;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getTrialPeriodDuration() {
        return trialPeriodDuration;
    }

    public void setTrialPeriodDuration(String trialPeriodDuration) {
        this.trialPeriodDuration = trialPeriodDuration;
    }

    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

}


