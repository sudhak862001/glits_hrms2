package com.Tejaysdr.msrc.activitys.emp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerModel  implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("bannerimage")
    int bannerimage;

    public BannerModel(int id, int bannerimage) {
        this.id = id;
        this.bannerimage = bannerimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBannerimage() {
        return bannerimage;
    }

    public void setBannerimage(int bannerimage) {
        this.bannerimage = bannerimage;
    }
}
