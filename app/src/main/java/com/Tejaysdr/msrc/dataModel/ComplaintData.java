package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by sridhar on 2/10/2017.
 */

public class ComplaintData  implements Serializable{
    @SerializedName("user_id")
    @Expose
    public String userId;

    public String getCircuitid() {
        return circuitid;
    }

    public void setCircuitid(String circuitid) {
        this.circuitid = circuitid;
    }

    @SerializedName("circuit_id")
    @Expose
    public String circuitid;
    public String getComusertype() {
        return comusertype;
    }

    public void setComusertype(String comusertype) {
        this.comusertype = comusertype;
    }

    @SerializedName("complaint_user_type")
    @Expose
    public String comusertype;

    public String getComnmaeteleco() {
        return comnmaeteleco;
    }

    public void setComnmaeteleco(String comnmaeteleco) {
        this.comnmaeteleco = comnmaeteleco;
    }

    @SerializedName("company_name_telco")
    @Expose
    public String comnmaeteleco;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("complaint_id")
    @Expose
    public String complaintId;
    @SerializedName("comp_no")
    @Expose
    public String compNo;
    @SerializedName("call_attend_by")
    @Expose
    public String callAttendBy;
    @SerializedName("call_type")
    @Expose
    public String callType;
    @SerializedName("comp_status")
    @Expose
    public String compStatus;
    @SerializedName("comp_category")
    @Expose
    public String compCategory;
    @SerializedName("comp_other_issue")
    @Expose
    public String compOtherIssue;
    @SerializedName("comp_owner")
    @Expose
    public String compOwner;
    @SerializedName("comp_date_created")
    @Expose
    public String compDateCreated;
    @SerializedName("comp_priority")
    @Expose
    public String compPriority;
    @SerializedName("tech_fte")
    @Expose
    public String techFte;
    @SerializedName("o_m_fte")
    @Expose
    public String oMFte;
    @SerializedName("zone_id")
    @Expose
    public String zoneId;
    @SerializedName("sno")
    @Expose
    public Integer sno;
    @SerializedName("zone")
    @Expose
    public String zone;
    @SerializedName("sub_cat_name")
    @Expose
    public String sub_cat_name;
    @SerializedName("company_name")
    @Expose
    public String company_name;
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getCompNo() {
        return compNo;
    }

    public void setCompNo(String compNo) {
        this.compNo = compNo;
    }

    public String getCallAttendBy() {
        return callAttendBy;
    }

    public void setCallAttendBy(String callAttendBy) {
        this.callAttendBy = callAttendBy;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCompStatus() {
        return compStatus;
    }

    public void setCompStatus(String compStatus) {
        this.compStatus = compStatus;
    }

    public String getCompCategory() {
        return compCategory;
    }

    public void setCompCategory(String compCategory) {
        this.compCategory = compCategory;
    }

    public String getCompOtherIssue() {
        return compOtherIssue;
    }

    public void setCompOtherIssue(String compOtherIssue) {
        this.compOtherIssue = compOtherIssue;
    }

    public String getCompOwner() {
        return compOwner;
    }

    public void setCompOwner(String compOwner) {
        this.compOwner = compOwner;
    }

    public String getCompDateCreated() {
        return compDateCreated;
    }

    public void setCompDateCreated(String compDateCreated) {
        this.compDateCreated = compDateCreated;
    }

    public String getCompPriority() {
        return compPriority;
    }

    public void setCompPriority(String compPriority) {
        this.compPriority = compPriority;
    }

    public String getTechFte() {
        return techFte;
    }

    public void setTechFte(String techFte) {
        this.techFte = techFte;
    }

    public String getoMFte() {
        return oMFte;
    }

    public void setoMFte(String oMFte) {
        this.oMFte = oMFte;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
