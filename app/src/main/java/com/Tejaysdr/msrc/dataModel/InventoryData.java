package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/9/2017.
 */

public class InventoryData  implements Serializable {
    public String getRequiredqty() {
        return requiredqty;
    }

    public void setRequiredqty(String requiredqty) {
        this.requiredqty = requiredqty;
    }

    public String getIndentno() {
        return indentno;
    }

    public void setIndentno(String indentno) {
        this.indentno = indentno;
    }

    public String getIndentreason() {
        return indentreason;
    }

    public void setIndentreason(String indentreason) {
        this.indentreason = indentreason;
    }

    public String getRequiredon() {
        return requiredon;
    }

    public void setRequiredon(String requiredon) {
        this.requiredon = requiredon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInwardremarks() {
        return inwardremarks;
    }

    public void setInwardremarks(String inwardremarks) {
        this.inwardremarks = inwardremarks;
    }

    public String getApprovedqty() {
        return Approvedqty;
    }

    public void setApprovedqty(String approvedqty) {
        Approvedqty = approvedqty;
    }

    public String getInvqty() {
        return invqty;
    }

    public void setInvqty(String invqty) {
        this.invqty = invqty;
    }

    public String getStatustxt() {
        return statustxt;
    }

    public void setStatustxt(String statustxt) {
        this.statustxt = statustxt;
    }

    @SerializedName("statusTxt")
    @Expose
    public String statustxt;

    public String getAvlqty() {
        return avlqty;
    }

    public void setAvlqty(String avlqty) {
        this.avlqty = avlqty;
    }

    @SerializedName("avlqty")
    @Expose
    public String avlqty;

    public String getInvgroupname() {
        return invgroupname;
    }

    public void setInvgroupname(String invgroupname) {
        this.invgroupname = invgroupname;
    }

    @SerializedName("inv_group_name")
    @Expose
    public String invgroupname;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @SerializedName("category")
    @Expose
    public String category;

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    @SerializedName("bodyMsg")
    @Expose
    public String message1;
    @SerializedName("approved_qty")
    @Expose
    public String Approvedqty;
    @SerializedName("inv_qty")
    @Expose
    public String invqty;

    @SerializedName("required_qty")
    @Expose
    public String requiredqty;
    @SerializedName("indentNo")
    @Expose
    public String indentno;

    public String getReturnqty() {
        return returnqty;
    }

    public void setReturnqty(String returnqty) {
        this.returnqty = returnqty;
    }

    @SerializedName("Returnqty")
    @Expose
    public String returnqty;
//    @SerializedName("avlqty")
//    @Expose
//    public String avlqty;
    @SerializedName("indent_reason")
    @Expose
    public String indentreason;
    @SerializedName("requiredOn")
    @Expose
    public String requiredon;
    @SerializedName("status")
    @Expose
    public String status;

    public String getDateCretaed() {
        return dateCretaed;
    }

    public void setDateCretaed(String dateCretaed) {
        this.dateCretaed = dateCretaed;
    }

    public String getEmpinwardid() {
        return empinwardid;
    }

    public void setEmpinwardid(String empinwardid) {
        this.empinwardid = empinwardid;
    }

    @SerializedName("emp_inward_id")
    @Expose
    public String empinwardid;
    @SerializedName("dateCreated")
    @Expose
    public String dateCretaed;
    @SerializedName("assign_inv_sl_no")
    @Expose
    public String assign_inv_sl_no;

    public String getAssign_inv_sl_no() {
        return assign_inv_sl_no;
    }

    public void setAssign_inv_sl_no(String assign_inv_sl_no) {
        this.assign_inv_sl_no = assign_inv_sl_no;
    }
//    public String getDateCretaed1() {
//        return dateCretaed1;
//    }
//
//    public void setDateCretaed1(String dateCretaed1) {
//        this.dateCretaed1 = dateCretaed1;
//    }

//    @SerializedName("dateCreated")
//    @Expose
//    public String dateCretaed1;
    @SerializedName("inward_remarks")
    @Expose
    public String inwardremarks;
    public String getAssignedqty() {
        return assignedqty;
    }

    public void setAssignedqty(String assignedqty) {
        this.assignedqty = assignedqty;
    }

    @SerializedName("assignedQty")
    @Expose
    public String assignedqty;

    public String getAssignedon() {
        return assignedon;
    }

    public void setAssignedon(String assignedon) {
        this.assignedon = assignedon;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @SerializedName("start_point")
    @Expose
    public String startpoint;


    @SerializedName("end_point")
    @Expose
    public String endpoint;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("assignedOn")
    @Expose
    public String assignedon;
    @SerializedName("inv_id")
    @Expose
    public String invId;
    @SerializedName("inv_name")
    @Expose
    public String invName;

//    public String getInwardid() {
//        return inwardid;
//    }
//
//    public void setInwardid(String inwardid) {
//        this.inwardid = inwardid;
//    }

//    @SerializedName("emp_inward_id")
//    @Expose
//    public String inwardid;
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

    @SerializedName("id")
    String id;
    @SerializedName("assign_inv_sl_no_id")
    @Expose
    public String assign_inv_sl_no_id;

    public String getAssign_inv_sl_no_id() {
        return assign_inv_sl_no_id;
    }

    public void setAssign_inv_sl_no_id(String assign_inv_sl_no_id) {
        this.assign_inv_sl_no_id = assign_inv_sl_no_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
