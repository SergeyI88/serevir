package controllers;

import java.util.List;

public class Good {

    private String uuid;

    private String code;

    private List<Object> barCodes = null;

    private List<Object> alcoCodes = null;

    private String name;

    private Double price;

    public Good() {
    }

    private Integer quantity;

    private Double costPrice;

    private String measureName;

    private String tax;

    private Boolean allowToSell;

    private String description;

    private String articleNumber;

    private String parentUuid;

    private Boolean group;

    private String type;

    private Double alcoholByVolume;

    private Integer alcoholProductKindCode;

    private Double tareVolume;

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

    public List<Object> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(List<Object> barCodes) {
        this.barCodes = barCodes;
    }

    public List<Object> getAlcoCodes() {
        return alcoCodes;
    }

    public void setAlcoCodes(List<Object> alcoCodes) {
        this.alcoCodes = alcoCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public Boolean getAllowToSell() {
        return allowToSell;
    }

    public void setAllowToSell(Boolean allowToSell) {
        this.allowToSell = allowToSell;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAlcoholByVolume() {
        return alcoholByVolume;
    }

    public void setAlcoholByVolume(Double alcoholByVolume) {
        this.alcoholByVolume = alcoholByVolume;
    }

    public Integer getAlcoholProductKindCode() {
        return alcoholProductKindCode;
    }

    public void setAlcoholProductKindCode(Integer alcoholProductKindCode) {
        this.alcoholProductKindCode = alcoholProductKindCode;
    }

    public Double getTareVolume() {
        return tareVolume;
    }

    public void setTareVolume(Double tareVolume) {
        this.tareVolume = tareVolume;
    }


    @Override
    public String toString() {
        return "Good{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", barCodes=" + barCodes +
                ", alcoCodes=" + alcoCodes +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", costPrice=" + costPrice +
                ", measureName='" + measureName + '\'' +
                ", tax='" + tax + '\'' +
                ", allowToSell=" + allowToSell +
                ", description='" + description + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", group=" + group +
                ", type='" + type + '\'' +
                ", alcoholByVolume=" + alcoholByVolume +
                ", alcoholProductKindCode=" + alcoholProductKindCode +
                ", tareVolume=" + tareVolume +
                '}';
    }
}
