package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/11/2017.
 */

public class InventoryItemsData implements Serializable {

    @SerializedName("inv_id")
    @Expose
    public String invId;
    @SerializedName("inv_name")
    @Expose
    public String invName;
    @SerializedName("inv_code")
    @Expose
    public String invCode;
    @SerializedName("inv_price")
    @Expose
    public String invPrice;
    @SerializedName("inv_tax")
    @Expose
    public String invTax;
    @SerializedName("inv_unit")
    @Expose
    public String invUnit;
    @SerializedName("inv_group")
    @Expose
    public String invGroup;
    @SerializedName("inv_description")
    @Expose
    public String invDescription;
    @SerializedName("inv_item_type")
    @Expose
    public String invItemType;
    @SerializedName("inv_date_created")
    @Expose
    public String invDateCreated;
    @SerializedName("inv_updated_on")
    @Expose
    public String invUpdatedOn;
    @SerializedName("inv_show")
    @Expose
    public String invShow;

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvPrice() {
        return invPrice;
    }

    public void setInvPrice(String invPrice) {
        this.invPrice = invPrice;
    }

    public String getInvTax() {
        return invTax;
    }

    public void setInvTax(String invTax) {
        this.invTax = invTax;
    }

    public String getInvUnit() {
        return invUnit;
    }

    public void setInvUnit(String invUnit) {
        this.invUnit = invUnit;
    }

    public String getInvGroup() {
        return invGroup;
    }

    public void setInvGroup(String invGroup) {
        this.invGroup = invGroup;
    }

    public String getInvDescription() {
        return invDescription;
    }

    public void setInvDescription(String invDescription) {
        this.invDescription = invDescription;
    }

    public String getInvItemType() {
        return invItemType;
    }

    public void setInvItemType(String invItemType) {
        this.invItemType = invItemType;
    }

    public String getInvDateCreated() {
        return invDateCreated;
    }

    public void setInvDateCreated(String invDateCreated) {
        this.invDateCreated = invDateCreated;
    }

    public String getInvUpdatedOn() {
        return invUpdatedOn;
    }

    public void setInvUpdatedOn(String invUpdatedOn) {
        this.invUpdatedOn = invUpdatedOn;
    }

    public String getInvShow() {
        return invShow;
    }

    public void setInvShow(String invShow) {
        this.invShow = invShow;
    }
}