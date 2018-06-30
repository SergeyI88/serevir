

package controllers.json;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("deviceUuid")
    @Expose
    private String deviceUuid;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;
    @SerializedName("closeDate")
    @Expose
    private String closeDate;
    @SerializedName("openDate")
    @Expose
    private String openDate;
    @SerializedName("openUserCode")
    @Expose
    private Object openUserCode;
    @SerializedName("openUserUuid")
    @Expose
    private String openUserUuid;
    @SerializedName("closeUserCode")
    @Expose
    private Object closeUserCode;
    @SerializedName("closeUserUuid")
    @Expose
    private String closeUserUuid;
    @SerializedName("sessionUUID")
    @Expose
    private String sessionUUID;
    @SerializedName("sessionNumber")
    @Expose
    private String sessionNumber;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("closeResultSum")
    @Expose
    private String closeResultSum;
    @SerializedName("closeSum")
    @Expose
    private String closeSum;
    @SerializedName("storeUuid")
    @Expose
    private String storeUuid;
    @SerializedName("completeInventory")
    @Expose
    private Boolean completeInventory;
    @SerializedName("extras")
    @Expose
    private Extras extras;
    @SerializedName("version")
    @Expose
    private String version;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public Object getOpenUserCode() {
        return openUserCode;
    }

    public void setOpenUserCode(Object openUserCode) {
        this.openUserCode = openUserCode;
    }

    public String getOpenUserUuid() {
        return openUserUuid;
    }

    public void setOpenUserUuid(String openUserUuid) {
        this.openUserUuid = openUserUuid;
    }

    public Object getCloseUserCode() {
        return closeUserCode;
    }

    public void setCloseUserCode(Object closeUserCode) {
        this.closeUserCode = closeUserCode;
    }

    public String getCloseUserUuid() {
        return closeUserUuid;
    }

    public void setCloseUserUuid(String closeUserUuid) {
        this.closeUserUuid = closeUserUuid;
    }

    public String getSessionUUID() {
        return sessionUUID;
    }

    public void setSessionUUID(String sessionUUID) {
        this.sessionUUID = sessionUUID;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCloseResultSum() {
        return closeResultSum;
    }

    public void setCloseResultSum(String closeResultSum) {
        this.closeResultSum = closeResultSum;
    }

    public String getCloseSum() {
        return closeSum;
    }

    public void setCloseSum(String closeSum) {
        this.closeSum = closeSum;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public Boolean getCompleteInventory() {
        return completeInventory;
    }

    public void setCompleteInventory(Boolean completeInventory) {
        this.completeInventory = completeInventory;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public class Transaction {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("uuid")
        @Expose
        private Object uuid;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userCode")
        @Expose
        private Object userCode;
        @SerializedName("userUuid")
        @Expose
        private String userUuid;
        @SerializedName("creationDate")
        @Expose
        private String creationDate;
        @SerializedName("timezone")
        @Expose
        private Integer timezone;
        @SerializedName("baseDocumentNumber")
        @Expose
        private Object baseDocumentNumber;
        @SerializedName("baseDocumentUUID")
        @Expose
        private Object baseDocumentUUID;
        @SerializedName("clientName")
        @Expose
        private Object clientName;
        @SerializedName("clientPhone")
        @Expose
        private Object clientPhone;
        @SerializedName("couponNumber")
        @Expose
        private Object couponNumber;
        @SerializedName("alcoholByVolume")
        @Expose
        private Integer alcoholByVolume;
        @SerializedName("alcoholProductKindCode")
        @Expose
        private Integer alcoholProductKindCode;
        @SerializedName("balanceQuantity")
        @Expose
        private Integer balanceQuantity;
        @SerializedName("barcode")
        @Expose
        private String barcode;
        @SerializedName("commodityCode")
        @Expose
        private String commodityCode;
        @SerializedName("commodityUuid")
        @Expose
        private String commodityUuid;
        @SerializedName("commodityName")
        @Expose
        private String commodityName;
        @SerializedName("commodityType")
        @Expose
        private String commodityType;
        @SerializedName("costPrice")
        @Expose
        private Integer costPrice;
        @SerializedName("fprintSection")
        @Expose
        private String fprintSection;
        @SerializedName("mark")
        @Expose
        private Object mark;
        @SerializedName("measureName")
        @Expose
        private String measureName;
        @SerializedName("tareVolume")
        @Expose
        private Integer tareVolume;
        @SerializedName("price")
        @Expose
        private BigDecimal price;
        @SerializedName("quantity")
        @Expose
        private Double quantity;
        @SerializedName("resultPrice")
        @Expose
        private BigDecimal resultPrice;
        @SerializedName("resultSum")
        @Expose
        private BigDecimal resultSum;
        @SerializedName("sum")
        @Expose
        private BigDecimal sum;
        @SerializedName("positionId")
        @Expose
        private Object positionId;
        @SerializedName("extraKeys")
        @Expose
        private List<Object> extraKeys = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUuid() {
            return uuid;
        }

        public void setUuid(Object uuid) {
            this.uuid = uuid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getUserCode() {
            return userCode;
        }

        public void setUserCode(Object userCode) {
            this.userCode = userCode;
        }

        public String getUserUuid() {
            return userUuid;
        }

        public void setUserUuid(String userUuid) {
            this.userUuid = userUuid;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Integer getTimezone() {
            return timezone;
        }

        public void setTimezone(Integer timezone) {
            this.timezone = timezone;
        }

        public Object getBaseDocumentNumber() {
            return baseDocumentNumber;
        }

        public void setBaseDocumentNumber(Object baseDocumentNumber) {
            this.baseDocumentNumber = baseDocumentNumber;
        }

        public Object getBaseDocumentUUID() {
            return baseDocumentUUID;
        }

        public void setBaseDocumentUUID(Object baseDocumentUUID) {
            this.baseDocumentUUID = baseDocumentUUID;
        }

        public Object getClientName() {
            return clientName;
        }

        public void setClientName(Object clientName) {
            this.clientName = clientName;
        }

        public Object getClientPhone() {
            return clientPhone;
        }

        public void setClientPhone(Object clientPhone) {
            this.clientPhone = clientPhone;
        }

        public Object getCouponNumber() {
            return couponNumber;
        }

        public void setCouponNumber(Object couponNumber) {
            this.couponNumber = couponNumber;
        }

        public Integer getAlcoholByVolume() {
            return alcoholByVolume;
        }

        public void setAlcoholByVolume(Integer alcoholByVolume) {
            this.alcoholByVolume = alcoholByVolume;
        }

        public Integer getAlcoholProductKindCode() {
            return alcoholProductKindCode;
        }

        public void setAlcoholProductKindCode(Integer alcoholProductKindCode) {
            this.alcoholProductKindCode = alcoholProductKindCode;
        }

        public Integer getBalanceQuantity() {
            return balanceQuantity;
        }

        public void setBalanceQuantity(Integer balanceQuantity) {
            this.balanceQuantity = balanceQuantity;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getCommodityCode() {
            return commodityCode;
        }

        public void setCommodityCode(String commodityCode) {
            this.commodityCode = commodityCode;
        }

        public String getCommodityUuid() {
            return commodityUuid;
        }

        public void setCommodityUuid(String commodityUuid) {
            this.commodityUuid = commodityUuid;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getCommodityType() {
            return commodityType;
        }

        public void setCommodityType(String commodityType) {
            this.commodityType = commodityType;
        }

        public Integer getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Integer costPrice) {
            this.costPrice = costPrice;
        }

        public String getFprintSection() {
            return fprintSection;
        }

        public void setFprintSection(String fprintSection) {
            this.fprintSection = fprintSection;
        }

        public Object getMark() {
            return mark;
        }

        public void setMark(Object mark) {
            this.mark = mark;
        }

        public String getMeasureName() {
            return measureName;
        }

        public void setMeasureName(String measureName) {
            this.measureName = measureName;
        }

        public Integer getTareVolume() {
            return tareVolume;
        }

        public void setTareVolume(Integer tareVolume) {
            this.tareVolume = tareVolume;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getResultPrice() {
            return resultPrice;
        }

        public void setResultPrice(BigDecimal resultPrice) {
            this.resultPrice = resultPrice;
        }

        public BigDecimal getResultSum() {
            return resultSum;
        }

        public void setResultSum(BigDecimal resultSum) {
            this.resultSum = resultSum;
        }

        public BigDecimal getSum() {
            return sum;
        }

        public void setSum(BigDecimal sum) {
            this.sum = sum;
        }

        public Object getPositionId() {
            return positionId;
        }

        public void setPositionId(Object positionId) {
            this.positionId = positionId;
        }

        public List<Object> getExtraKeys() {
            return extraKeys;
        }

        public void setExtraKeys(List<Object> extraKeys) {
            this.extraKeys = extraKeys;
        }

    }

    public class Extras {


    }

}





