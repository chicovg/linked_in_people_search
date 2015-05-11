package com.captech.appdevsearch.model;

/**
 * Created by victorguthrie on 3/6/15.
 */
public class AndroidAppDetail {

    private String id;
    private String title;
    private String appType;
    private String creator;
    private String version;
    private String price;
    private String rating;
    private int ratingsCount;
    private String creatorId;
    private String packageName;
    private int versionCode;
    private String priceCurrency;
    private int priceMicros;

    public AndroidAppDetail() {

    }

    public AndroidAppDetail(String id, String title, String appType, String creator, String version, String price, String rating, int ratingsCount, String creatorId, String packageName, int versionCode, String priceCurrency, int priceMicros) {
        this.id = id;
        this.title = title;
        this.appType = appType;
        this.creator = creator;
        this.version = version;
        this.price = price;
        this.rating = rating;
        this.ratingsCount = ratingsCount;
        this.creatorId = creatorId;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.priceCurrency = priceCurrency;
        this.priceMicros = priceMicros;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public int getPriceMicros() {
        return priceMicros;
    }

    public void setPriceMicros(int priceMicros) {
        this.priceMicros = priceMicros;
    }
}
