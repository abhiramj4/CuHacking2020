package com.example.barcodescanner;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("webLink")
    @Expose
    private String webLink;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("composition")
    @Expose
    private String composition;
    @SerializedName("madeIn")
    @Expose
    private String madeIn;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}