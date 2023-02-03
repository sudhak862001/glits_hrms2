package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Expensesbdemodel implements Serializable {

    public String getExpItemId() {
        return expItemId;
    }

    public void setExpItemId(String expItemId) {
        this.expItemId = expItemId;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getExpItemDate() {
        return expItemDate;
    }

    public void setExpItemDate(String expItemDate) {
        this.expItemDate = expItemDate;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpMode() {
        return expMode;
    }

    public void setExpMode(String expMode) {
        this.expMode = expMode;
    }

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getApproxKm() {
        return approxKm;
    }

    public void setApproxKm(String approxKm) {
        this.approxKm = approxKm;
    }

    public String getExpPrice() {
        return expPrice;
    }

    public void setExpPrice(String expPrice) {
        this.expPrice = expPrice;
    }

    public String getExpMApprovedAmt() {
        return expMApprovedAmt;
    }

    public void setExpMApprovedAmt(String expMApprovedAmt) {
        this.expMApprovedAmt = expMApprovedAmt;
    }

    public String getExpApprovedAmt() {
        return expApprovedAmt;
    }

    public void setExpApprovedAmt(String expApprovedAmt) {
        this.expApprovedAmt = expApprovedAmt;
    }

    public String getExpRemarks() {
        return expRemarks;
    }

    public void setExpRemarks(String expRemarks) {
        this.expRemarks = expRemarks;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getExpItemDateCreated() {
        return expItemDateCreated;
    }

    public void setExpItemDateCreated(String expItemDateCreated) {
        this.expItemDateCreated = expItemDateCreated;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public String getApprovedByM() {
        return approvedByM;
    }

    public void setApprovedByM(String approvedByM) {
        this.approvedByM = approvedByM;
    }

    public String getApprovedByFin() {
        return approvedByFin;
    }

    public void setApprovedByFin(String approvedByFin) {
        this.approvedByFin = approvedByFin;
    }

    public String getExpItemUpdatedOn() {
        return expItemUpdatedOn;
    }

    public void setExpItemUpdatedOn(String expItemUpdatedOn) {
        this.expItemUpdatedOn = expItemUpdatedOn;
    }

    public String getExpStatus() {
        return expStatus;
    }

    public void setExpStatus(String expStatus) {
        this.expStatus = expStatus;
    }

    public String getExpImg1() {
        return expImg1;
    }

    public void setExpImg1(String expImg1) {
        this.expImg1 = expImg1;
    }

    public String getManagerRemarks() {
        return managerRemarks;
    }

    public void setManagerRemarks(String managerRemarks) {
        this.managerRemarks = managerRemarks;
    }

    public String getFinRemarks() {
        return finRemarks;
    }

    public void setFinRemarks(String finRemarks) {
        this.finRemarks = finRemarks;
    }

    public String getMngrApprovedTime() {
        return mngrApprovedTime;
    }

    public void setMngrApprovedTime(String mngrApprovedTime) {
        this.mngrApprovedTime = mngrApprovedTime;
    }

    public String getFinApprovedTime() {
        return finApprovedTime;
    }

    public void setFinApprovedTime(String finApprovedTime) {
        this.finApprovedTime = finApprovedTime;
    }

    @SerializedName("exp_item_id")
    @Expose
    private String expItemId;
    @SerializedName("exp_id")
    @Expose
    private String expId;
    @SerializedName("exp_item_date")
    @Expose
    private String expItemDate;
    @SerializedName("exp_type")
    @Expose
    private String expType;
    @SerializedName("exp_mode")
    @Expose
    private String expMode;
    @SerializedName("from_loc")
    @Expose
    private String fromLoc;
    @SerializedName("to_loc")
    @Expose
    private String toLoc;
    @SerializedName("approx_km")
    @Expose
    private String approxKm;
    @SerializedName("exp_price")
    @Expose
    private String expPrice;
    @SerializedName("exp_m_approved_amt")
    @Expose
    private String expMApprovedAmt;
    @SerializedName("exp_approved_amt")
    @Expose
    private String expApprovedAmt;
    @SerializedName("exp_remarks")
    @Expose
    private String expRemarks;
    @SerializedName("check_in_date")
    @Expose
    private String checkInDate;
    @SerializedName("check_out_date")
    @Expose
    private String checkOutDate;
    @SerializedName("hotel_name")
    @Expose
    private String hotelName;
    @SerializedName("hotel_address")
    @Expose
    private String hotelAddress;
    @SerializedName("exp_item_date_created")
    @Expose
    private String expItemDateCreated;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("tr_id")
    @Expose
    private String trId;
    @SerializedName("approved_by_m")
    @Expose
    private String approvedByM;
    @SerializedName("approved_by_fin")
    @Expose
    private String approvedByFin;
    @SerializedName("exp_item_updated_on")
    @Expose
    private String expItemUpdatedOn;
    @SerializedName("exp_status")
    @Expose
    private String expStatus;
    @SerializedName("exp_img1")
    @Expose
    private String expImg1;
    @SerializedName("manager_remarks")
    @Expose
    private String managerRemarks;
    @SerializedName("fin_remarks")
    @Expose
    private String finRemarks;
    @SerializedName("mngr_approved_time")
    @Expose
    private String mngrApprovedTime;
    @SerializedName("fin_approved_time")
    @Expose
    private String finApprovedTime;

}
