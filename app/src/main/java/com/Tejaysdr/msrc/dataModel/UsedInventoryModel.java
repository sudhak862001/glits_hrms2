package com.Tejaysdr.msrc.dataModel;



public class UsedInventoryModel {
    public String getInvid() {
        return invid;
    }

    public void setInvid(String invid) {
        this.invid = invid;
    }

    String invid ,invName, usdQty, srtrdng, endrdng,srlnum;

    public UsedInventoryModel( String invid,String invName, String usdQty, String srtrdng, String endrdng, String srlnum) {
        this.invid=invid;
        this.invName = invName;
        this.usdQty = usdQty;
        this.srtrdng = srtrdng;
        this.endrdng = endrdng;
        this.srlnum = srlnum;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getUsdQty() {
        return usdQty;
    }

    public void setUsdQty(String usdQty) {
        this.usdQty = usdQty;
    }

    public String getSrtrdng() {
        return srtrdng;
    }

    public void setSrtrdng(String srtrdng) {
        this.srtrdng = srtrdng;
    }

    public String getEndrdng() {
        return endrdng;
    }

    public void setEndrdng(String endrdng) {
        this.endrdng = endrdng;
    }

    public String getSrlnum() {
        return srlnum;
    }

    public void setSrlnum(String srlnum) {
        this.srlnum = srlnum;
    }


}
