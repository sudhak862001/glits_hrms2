package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LeaveHistoryModel implements Serializable {
    public String getL_req_id() {
        return l_req_id;
    }

    public void setL_req_id(String l_req_id) {
        this.l_req_id = l_req_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getTr_id() {
        return tr_id;
    }

    public void setTr_id(String tr_id) {
        this.tr_id = tr_id;
    }

    public String getReq_emp_id() {
        return req_emp_id;
    }

    public void setReq_emp_id(String req_emp_id) {
        this.req_emp_id = req_emp_id;
    }

    public String getLeave_from_date() {
        return leave_from_date;
    }

    public void setLeave_from_date(String leave_from_date) {
        this.leave_from_date = leave_from_date;
    }

    public String getLeave_to_date() {
        return leave_to_date;
    }

    public void setLeave_to_date(String leave_to_date) {
        this.leave_to_date = leave_to_date;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getApproved_remarks() {
        return approved_remarks;
    }

    public void setApproved_remarks(String approved_remarks) {
        this.approved_remarks = approved_remarks;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getEmp_first_name() {
        return emp_first_name;
    }

    public void setEmp_first_name(String emp_first_name) {
        this.emp_first_name = emp_first_name;
    }

    public String getEmp_last_name() {
        return emp_last_name;
    }

    public void setEmp_last_name(String emp_last_name) {
        this.emp_last_name = emp_last_name;
    }

    public String getTrainer_name() {
        return trainer_name;
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name = trainer_name;
    }

    @SerializedName("l_req_id")
    private String l_req_id;
    @SerializedName("type")
    private String type;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("tr_id")
    private String tr_id;
    @SerializedName("req_emp_id")
    private String req_emp_id;
    @SerializedName("leave_from_date")
    private String leave_from_date;
    @SerializedName("leave_to_date")
    private String leave_to_date;
    @SerializedName("leave_reason")
    private String leave_reason;
    @SerializedName("no_of_days")
    private String no_of_days;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("leave_status")
    private String leave_status;
    @SerializedName("updatedOn")
    private String updatedOn;
    @SerializedName("approved_by")
    private String approved_by;
    @SerializedName("approved_remarks")
    private String approved_remarks;
    @SerializedName("leaveStatus")
    private String leaveStatus;
    @SerializedName("emp_first_name")
    private String emp_first_name;
    @SerializedName("emp_last_name")
    private String emp_last_name;
    @SerializedName("trainer_name")
    private String trainer_name;
}
