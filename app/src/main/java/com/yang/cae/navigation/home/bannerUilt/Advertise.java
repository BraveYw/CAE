package com.yang.cae.navigation.home.bannerUilt;


public class Advertise {

    private String id;
    private String imageUrl;
    private String advertiseUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAdvertiseUrl() {
        return advertiseUrl;
    }

    public void setAdvertiseUrl(String advertiseUrl) {
        this.advertiseUrl = advertiseUrl;
    }

    @Override
    public String toString() {
        return "Advertise{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", advertiseUrl='" + advertiseUrl + '\'' +
                '}';
    }
}
