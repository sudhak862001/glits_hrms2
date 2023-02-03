package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/20/2017.
 */

public class ClosedcompData implements Serializable {
    @SerializedName("sub_cat_id")
    @Expose
    public String subCatId;
    @SerializedName("cat_id")
    @Expose
    public String catId;
    @SerializedName("sub_cat_name")
    @Expose
    public String subCatName;
    @SerializedName("sub_cat_status")
    @Expose
    public String subCatStatus;
    @SerializedName("sub_cat_show")
    @Expose
    public String subCatShow;
    @SerializedName("sub_cat_date_created")
    @Expose
    public String subCatDateCreated;
    @SerializedName("sub_cat_description")
    @Expose
    public Object subCatDescription;
    @SerializedName("cat_comp")
    @Expose
    public String catComp;
    @SerializedName("noc_comp_cat")
    @Expose
    public Object nocCompCat;
    @SerializedName("org_id")
    @Expose
    public Object orgId;

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getSubCatStatus() {
        return subCatStatus;
    }

    public void setSubCatStatus(String subCatStatus) {
        this.subCatStatus = subCatStatus;
    }

    public String getSubCatShow() {
        return subCatShow;
    }

    public void setSubCatShow(String subCatShow) {
        this.subCatShow = subCatShow;
    }

    public String getSubCatDateCreated() {
        return subCatDateCreated;
    }

    public void setSubCatDateCreated(String subCatDateCreated) {
        this.subCatDateCreated = subCatDateCreated;
    }

    public Object getSubCatDescription() {
        return subCatDescription;
    }

    public void setSubCatDescription(Object subCatDescription) {
        this.subCatDescription = subCatDescription;
    }

    public String getCatComp() {
        return catComp;
    }

    public void setCatComp(String catComp) {
        this.catComp = catComp;
    }

    public Object getNocCompCat() {
        return nocCompCat;
    }

    public void setNocCompCat(Object nocCompCat) {
        this.nocCompCat = nocCompCat;
    }

    public Object getOrgId() {
        return orgId;
    }

    public void setOrgId(Object orgId) {
        this.orgId = orgId;
    }
}
