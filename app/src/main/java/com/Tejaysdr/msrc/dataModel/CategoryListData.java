package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/10/2017.
 */

public class CategoryListData  implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("category")
    private String category;

    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }

    public String getcategory() {
        return category;
    }
    public void setcategory(String category) {
        this.category = category;
    }
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
    public String subCatDescription;
    @SerializedName("cat_comp")
    @Expose
    public String catComp;
    @SerializedName("noc_comp_cat")
    @Expose
    public String nocCompCat;
    @SerializedName("org_id")
    @Expose
    public String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public String getSubCatDescription() {
        return subCatDescription;
    }

    public void setSubCatDescription(String subCatDescription) {
        this.subCatDescription = subCatDescription;
    }

    public String getCatComp() {
        return catComp;
    }

    public void setCatComp(String catComp) {
        this.catComp = catComp;
    }

    public String getNocCompCat() {
        return nocCompCat;
    }

    public void setNocCompCat(String nocCompCat) {
        this.nocCompCat = nocCompCat;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
