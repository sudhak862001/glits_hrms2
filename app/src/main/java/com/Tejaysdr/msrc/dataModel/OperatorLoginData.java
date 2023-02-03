package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/9/2017.
 */

public class OperatorLoginData implements Serializable {

    @SerializedName("petrol_price")
    private String petrol_price;
    @SerializedName("scroll_msg")
    private String scroll_msg;
    @SerializedName("support_no")
    private String support_no;
    @SerializedName("emp_appointment_acceptance")
    private String emp_appointment_acceptance;
    @SerializedName("emp_appointment_letter")
    private String emp_appointment_letter;

    public String getEmp_appointment_acceptance() {
        return emp_appointment_acceptance;
    }

    public void setEmp_appointment_acceptance(String emp_appointment_acceptance) {
        this.emp_appointment_acceptance = emp_appointment_acceptance;
    }

    public String getEmp_appointment_letter() {
        return emp_appointment_letter;
    }

    public void setEmp_appointment_letter(String emp_appointment_letter) {
        this.emp_appointment_letter = emp_appointment_letter;
    }

    public String getPetrol_price() {
        return petrol_price;
    }

    public void setPetrol_price(String petrol_price) {
        this.petrol_price = petrol_price;
    }

    public String getScroll_msg() {
        return scroll_msg;
    }

    public void setScroll_msg(String scroll_msg) {
        this.scroll_msg = scroll_msg;
    }

    public String getSupport_no() {
        return support_no;
    }

    public void setSupport_no(String support_no) {
        this.support_no = support_no;
    }

    @SerializedName("fullname")
    private String fullname;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("type")
    private String type;
    @SerializedName("email")
    private String email;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("designation")
    private String designation;
    @SerializedName("role_id")
    private String role_id;
    @SerializedName("role_name")
    private String role_name;
    @SerializedName("perm_add_employee")
    private String perm_add_employee;
    @SerializedName("perm_edit_employee")
    private String perm_edit_employee;
    @SerializedName("perm_view_employee")
    private String perm_view_employee;
    @SerializedName("perm_del_employee")
    private String perm_del_employee;
    @SerializedName("perm_add_lead")
    private String perm_add_lead;
    @SerializedName("perm_edit_lead")
    private String perm_edit_lead;
    @SerializedName("perm_view_lead")
    private String perm_view_lead;
    @SerializedName("perm_del_lead")
    private String perm_del_lead;
    @SerializedName("perm_all_leads")
    private String perm_all_leads;
    @SerializedName("perm_add_role")
    private String perm_add_role;
    @SerializedName("perm_edit_role")
    private String perm_edit_role;
    @SerializedName("perm_view_role")
    private String perm_view_role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImgstatus() {
        return imgstatus;
    }

    public void setImgstatus(String imgstatus) {
        this.imgstatus = imgstatus;
    }

    @SerializedName("emp_login_img_status")
    private String imgstatus;
    @SerializedName("perm_del_role")
    private  String token;
    @SerializedName("token")
    private String perm_del_role;
    @SerializedName("perm_add_lco")
    private String perm_add_lco;
    @SerializedName("perm_edit_lco")
    private String perm_edit_lco;
    @SerializedName("perm_view_lco")
    private String perm_view_lco;
    @SerializedName("perm_del_lco")
    private String perm_del_lco;
    @SerializedName("perm_add_settings")
    private String perm_add_settings;
    @SerializedName("perm_edit_settings")
    private String perm_edit_settings;
    @SerializedName("perm_view_settings")
    private String perm_view_settings;
    @SerializedName("perm_del_settings")
    private String perm_del_settings;
    @SerializedName("perm_lead_assign")
    private String perm_lead_assign;
    @SerializedName("perm_lead_feasibility")
    private String perm_lead_feasibility;
    @SerializedName("perm_lead_fiber")
    private String perm_lead_fiber;
    @SerializedName("perm_lead_rf")
    private String perm_lead_rf;
    @SerializedName("perm_lead_telco")
    private String perm_lead_telco;
    @SerializedName("perm_lead_router")
    private String perm_lead_router;
    @SerializedName("perm_add_complaint")
    private String perm_add_complaint;
    @SerializedName("perm_edit_complaint")
    private String perm_edit_complaint;
    @SerializedName("perm_view_complaint")
    private String perm_view_complaint;
    @SerializedName("perm_del_complaint")
    private String perm_del_complaint;
    @SerializedName("perm_close_complaint")
    private String perm_close_complaint;
    @SerializedName("perm_complaint_tech")
    private String perm_complaint_tech;
    @SerializedName("perm_complaint_o_m")
    private String perm_complaint_o_m;
    @SerializedName("perm_add_inv")
    private String perm_add_inv;
    @SerializedName("perm_edit_inv")
    private String perm_edit_inv;
    @SerializedName("perm_view_inv")
    private String perm_view_inv;
    @SerializedName("perm_del_inv")
    private String perm_del_inv;
    @SerializedName("perm_assign_inv")
    private String perm_assign_inv;
    @SerializedName("perm_add_user")
    private String perm_add_user;
    @SerializedName("perm_edit_user")
    private String perm_edit_user;
    @SerializedName("perm_view_user")
    private String perm_view_user;
    @SerializedName("perm_del_user")
    private String perm_del_user;
    @SerializedName("perm_all_users")
    private String perm_all_users;
    @SerializedName("perm_coll_user")
    private String perm_coll_user;
    @SerializedName("perm_add_invoice")
    private String perm_add_invoice;
    @SerializedName("perm_edit_invoice")
    private String perm_edit_invoice;
    @SerializedName("perm_view_invoice")
    private String perm_view_invoice;
    @SerializedName("perm_del_invoice")
    private String perm_del_invoice;
    @SerializedName("perm_add_renewals")
    private String perm_add_renewals;
    @SerializedName("perm_edit_renewals")
    private String perm_edit_renewals;
    @SerializedName("perm_view_renewals")
    private String perm_view_renewals;
    @SerializedName("perm_del_renewals")
    private String perm_del_renewals;
    @SerializedName("perm_all_complaint")
    private String perm_all_complaint;
    @SerializedName("role_date_created")
    private String role_date_created;
    @SerializedName("role_status")
    private String role_status;
    @SerializedName("role_show")
    private String role_show;
    @SerializedName("perm_list_coll")
    private String perm_list_coll;
    @SerializedName("perm_list_outstanding")
    private String perm_list_outstanding;
    @SerializedName("perm_download_reports")
    private String perm_download_reports;
    @SerializedName("perm_lead_project")
    private String perm_lead_project;
    @SerializedName("perm_lead_noc")
    private String perm_lead_noc;
    @SerializedName("perm_lead_billing")
    private String perm_lead_billing ;
    @SerializedName("perm_all_lead_project")
    private String perm_all_lead_project;
    @SerializedName("perm_all_lead_noc")
    private String perm_all_lead_noc;
    @SerializedName("perm_all_lead_billing")
    private String perm_all_lead_billing;
    @SerializedName("perm_all_lead_feasibility")
    private String perm_all_lead_feasibility;
    @SerializedName("perm_lead_documents")
    private String perm_lead_documents;
    @SerializedName("perm_lead_management")
    private String perm_lead_management;
    @SerializedName("perm_all_lead_management")
    private String perm_all_lead_management;
    @SerializedName("perm_all_lead_procurement")
    private String perm_all_lead_procurement;
    @SerializedName("perm_add_plan")
    private String perm_add_plan;
    @SerializedName("perm_edit_plan")
    private String perm_edit_plan;
    @SerializedName("perm_view_plan")
    private String perm_view_plan;
    @SerializedName("perm_del_plan")
    private String perm_del_plan;
    @SerializedName("perm_approve_plan")
    private String perm_approve_plan;
    @SerializedName("perm_add_organization")
    private String perm_add_organization;
    @SerializedName("perm_view_billing")
    private String perm_view_billing;
    @SerializedName("perm_edit_organization")
    private String perm_edit_organization;
    @SerializedName("perm_view_organization")
    private String perm_view_organization;
    @SerializedName("perm_del_organization")
    private String perm_del_organization;
    @SerializedName("perm_view_olt_dashboard")
    private String perm_view_olt_dashboard;
    @SerializedName("perm_add_olt_ring")
    private String perm_add_olt_ring;
    @SerializedName("perm_edit_olt_ring")
    private String perm_edit_olt_ring;
    @SerializedName("perm_view_olt_ring")
    private String perm_view_olt_ring;
    @SerializedName("perm_add_iptv_user")
    private String perm_add_iptv_user;
    @SerializedName("perm_edit_iptv_user")
    private String perm_edit_iptv_user;
    @SerializedName("perm_view_iptv_user")
    private String perm_view_iptv_user;
    @SerializedName("perm_del_iptv_user")
    private String perm_del_iptv_user;
    @SerializedName("perm_add_pop")
    private String perm_add_pop;
    @SerializedName("perm_edit_pop")
    private String perm_edit_pop;
    @SerializedName("perm_view_pop")
    private String perm_view_pop;
    @SerializedName("perm_del_pop")
    private String perm_del_pop;
    @SerializedName("perm_add_feasibility")
    private String perm_add_feasibility;
    @SerializedName("perm_edit_feasibility")
    private String perm_edit_feasibility;
    @SerializedName("perm_view_feasibility")
    private String perm_view_feasibility;
    @SerializedName("perm_accept_feasibility")
    private String perm_accept_feasibility;
    @SerializedName("perm_bulk_upload_feasibility")
    private String perm_bulk_upload_feasibility;
    @SerializedName("perm_view_iptv_invoice")
    private String perm_view_iptv_invoice;
    @SerializedName("perm_view_caf")
    private String perm_view_caf;
    @SerializedName("perm_view_circuit_status")
    private String perm_view_circuit_status;
    @SerializedName("perm_view_admin_status")
    private String perm_view_admin_status;
    @SerializedName("perm_view_operational_status")
    private String perm_view_operational_status;
    @SerializedName("perm_view_cpe_down_cause")
    private String perm_view_cpe_down_cause;
    @SerializedName("perm_view_cpe_up_status")
    private String perm_view_cpe_up_status;
    @SerializedName("perm_view_cpe_down_status")
    private String perm_view_cpe_down_status;
    @SerializedName("perm_view_complaints")
    private String perm_view_complaints;
    @SerializedName("perm_view_graphical")
    private String perm_view_graphical;
    @SerializedName("perm_view_recent_logs")
    private String perm_view_recent_logs;
    @SerializedName("perm_view_teleconf_users")
    private String perm_view_teleconf_users;
    @SerializedName("perm_view_teleconf_invoices")
    private String perm_view_teleconf_invoices;
    @SerializedName("perm_payment_approval")
    private String perm_payment_approval;
    @SerializedName("perm_add_credit_note")
    private String perm_add_credit_note;
    @SerializedName("perm_view_credit_note")
    private String perm_view_credit_note;
    @SerializedName("perm_add_change_request")
    private String perm_add_change_request;
    @SerializedName("perm_view_change_request")
    private String perm_view_change_request;
    @SerializedName("perm_edit_change_request")
    private String perm_edit_change_request;
    @SerializedName("perm_approve_change_request")
    private String perm_approve_change_request;
    @SerializedName("perm_preliminary_approve_cr")
    private String perm_preliminary_approve_cr;
    @SerializedName("perm_add_feedback_form")
    private String perm_add_feedback_form;
    @SerializedName("perm_view_feedback_form")
    private String perm_view_feedback_form;
    @SerializedName("perm_view_account_summary")
    private String perm_view_account_summary;
    @SerializedName("perm_dept_complaint")
    private String perm_dept_complaint;
    @SerializedName("perm_ent_complaint")
    private String perm_ent_complaint;
    @SerializedName("login_emp_id")
    private String login_emp_id;
    @SerializedName("login_username")
    private String login_username;


    public String getFullname() {
        return fullname;
    }
    public  String accesstoken;

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPerm_add_employee() {
        return perm_add_employee;
    }

    public void setPerm_add_employee(String perm_add_employee) {
        this.perm_add_employee = perm_add_employee;
    }

    public String getPerm_edit_employee() {
        return perm_edit_employee;
    }

    public void setPerm_edit_employee(String perm_edit_employee) {
        this.perm_edit_employee = perm_edit_employee;
    }

    public String getPerm_view_employee() {
        return perm_view_employee;
    }

    public void setPerm_view_employee(String perm_view_employee) {
        this.perm_view_employee = perm_view_employee;
    }

    public String getPerm_del_employee() {
        return perm_del_employee;
    }

    public void setPerm_del_employee(String perm_del_employee) {
        this.perm_del_employee = perm_del_employee;
    }

    public String getPerm_add_lead() {
        return perm_add_lead;
    }

    public void setPerm_add_lead(String perm_add_lead) {
        this.perm_add_lead = perm_add_lead;
    }

    public String getPerm_edit_lead() {
        return perm_edit_lead;
    }

    public void setPerm_edit_lead(String perm_edit_lead) {
        this.perm_edit_lead = perm_edit_lead;
    }

    public String getPerm_view_lead() {
        return perm_view_lead;
    }

    public void setPerm_view_lead(String perm_view_lead) {
        this.perm_view_lead = perm_view_lead;
    }

    public String getPerm_del_lead() {
        return perm_del_lead;
    }

    public void setPerm_del_lead(String perm_del_lead) {
        this.perm_del_lead = perm_del_lead;
    }

    public String getPerm_all_leads() {
        return perm_all_leads;
    }

    public void setPerm_all_leads(String perm_all_leads) {
        this.perm_all_leads = perm_all_leads;
    }

    public String getPerm_add_role() {
        return perm_add_role;
    }

    public void setPerm_add_role(String perm_add_role) {
        this.perm_add_role = perm_add_role;
    }

    public String getPerm_edit_role() {
        return perm_edit_role;
    }

    public void setPerm_edit_role(String perm_edit_role) {
        this.perm_edit_role = perm_edit_role;
    }

    public String getPerm_view_role() {
        return perm_view_role;
    }

    public void setPerm_view_role(String perm_view_role) {
        this.perm_view_role = perm_view_role;
    }

    public String getPerm_del_role() {
        return perm_del_role;
    }

    public void setPerm_del_role(String perm_del_role) {
        this.perm_del_role = perm_del_role;
    }

    public String getPerm_add_lco() {
        return perm_add_lco;
    }

    public void setPerm_add_lco(String perm_add_lco) {
        this.perm_add_lco = perm_add_lco;
    }

    public String getPerm_edit_lco() {
        return perm_edit_lco;
    }

    public void setPerm_edit_lco(String perm_edit_lco) {
        this.perm_edit_lco = perm_edit_lco;
    }

    public String getPerm_view_lco() {
        return perm_view_lco;
    }

    public void setPerm_view_lco(String perm_view_lco) {
        this.perm_view_lco = perm_view_lco;
    }

    public String getPerm_del_lco() {
        return perm_del_lco;
    }

    public void setPerm_del_lco(String perm_del_lco) {
        this.perm_del_lco = perm_del_lco;
    }

    public String getPerm_add_settings() {
        return perm_add_settings;
    }

    public void setPerm_add_settings(String perm_add_settings) {
        this.perm_add_settings = perm_add_settings;
    }

    public String getPerm_edit_settings() {
        return perm_edit_settings;
    }

    public void setPerm_edit_settings(String perm_edit_settings) {
        this.perm_edit_settings = perm_edit_settings;
    }

    public String getPerm_view_settings() {
        return perm_view_settings;
    }

    public void setPerm_view_settings(String perm_view_settings) {
        this.perm_view_settings = perm_view_settings;
    }

    public String getPerm_del_settings() {
        return perm_del_settings;
    }

    public void setPerm_del_settings(String perm_del_settings) {
        this.perm_del_settings = perm_del_settings;
    }

    public String getPerm_lead_assign() {
        return perm_lead_assign;
    }

    public void setPerm_lead_assign(String perm_lead_assign) {
        this.perm_lead_assign = perm_lead_assign;
    }

    public String getPerm_lead_feasibility() {
        return perm_lead_feasibility;
    }

    public void setPerm_lead_feasibility(String perm_lead_feasibility) {
        this.perm_lead_feasibility = perm_lead_feasibility;
    }

    public String getPerm_lead_fiber() {
        return perm_lead_fiber;
    }

    public void setPerm_lead_fiber(String perm_lead_fiber) {
        this.perm_lead_fiber = perm_lead_fiber;
    }

    public String getPerm_lead_rf() {
        return perm_lead_rf;
    }

    public void setPerm_lead_rf(String perm_lead_rf) {
        this.perm_lead_rf = perm_lead_rf;
    }

    public String getPerm_lead_telco() {
        return perm_lead_telco;
    }

    public void setPerm_lead_telco(String perm_lead_telco) {
        this.perm_lead_telco = perm_lead_telco;
    }

    public String getPerm_lead_router() {
        return perm_lead_router;
    }

    public void setPerm_lead_router(String perm_lead_router) {
        this.perm_lead_router = perm_lead_router;
    }

    public String getPerm_add_complaint() {
        return perm_add_complaint;
    }

    public void setPerm_add_complaint(String perm_add_complaint) {
        this.perm_add_complaint = perm_add_complaint;
    }

    public String getPerm_edit_complaint() {
        return perm_edit_complaint;
    }

    public void setPerm_edit_complaint(String perm_edit_complaint) {
        this.perm_edit_complaint = perm_edit_complaint;
    }

    public String getPerm_view_complaint() {
        return perm_view_complaint;
    }

    public void setPerm_view_complaint(String perm_view_complaint) {
        this.perm_view_complaint = perm_view_complaint;
    }

    public String getPerm_del_complaint() {
        return perm_del_complaint;
    }

    public void setPerm_del_complaint(String perm_del_complaint) {
        this.perm_del_complaint = perm_del_complaint;
    }

    public String getPerm_close_complaint() {
        return perm_close_complaint;
    }

    public void setPerm_close_complaint(String perm_close_complaint) {
        this.perm_close_complaint = perm_close_complaint;
    }

    public String getPerm_complaint_tech() {
        return perm_complaint_tech;
    }

    public void setPerm_complaint_tech(String perm_complaint_tech) {
        this.perm_complaint_tech = perm_complaint_tech;
    }

    public String getPerm_complaint_o_m() {
        return perm_complaint_o_m;
    }

    public void setPerm_complaint_o_m(String perm_complaint_o_m) {
        this.perm_complaint_o_m = perm_complaint_o_m;
    }

    public String getPerm_add_inv() {
        return perm_add_inv;
    }

    public void setPerm_add_inv(String perm_add_inv) {
        this.perm_add_inv = perm_add_inv;
    }

    public String getPerm_edit_inv() {
        return perm_edit_inv;
    }

    public void setPerm_edit_inv(String perm_edit_inv) {
        this.perm_edit_inv = perm_edit_inv;
    }

    public String getPerm_view_inv() {
        return perm_view_inv;
    }

    public void setPerm_view_inv(String perm_view_inv) {
        this.perm_view_inv = perm_view_inv;
    }

    public String getPerm_del_inv() {
        return perm_del_inv;
    }

    public void setPerm_del_inv(String perm_del_inv) {
        this.perm_del_inv = perm_del_inv;
    }

    public String getPerm_assign_inv() {
        return perm_assign_inv;
    }

    public void setPerm_assign_inv(String perm_assign_inv) {
        this.perm_assign_inv = perm_assign_inv;
    }

    public String getPerm_add_user() {
        return perm_add_user;
    }

    public void setPerm_add_user(String perm_add_user) {
        this.perm_add_user = perm_add_user;
    }

    public String getPerm_edit_user() {
        return perm_edit_user;
    }

    public void setPerm_edit_user(String perm_edit_user) {
        this.perm_edit_user = perm_edit_user;
    }

    public String getPerm_view_user() {
        return perm_view_user;
    }

    public void setPerm_view_user(String perm_view_user) {
        this.perm_view_user = perm_view_user;
    }

    public String getPerm_del_user() {
        return perm_del_user;
    }

    public void setPerm_del_user(String perm_del_user) {
        this.perm_del_user = perm_del_user;
    }

    public String getPerm_all_users() {
        return perm_all_users;
    }

    public void setPerm_all_users(String perm_all_users) {
        this.perm_all_users = perm_all_users;
    }

    public String getPerm_coll_user() {
        return perm_coll_user;
    }

    public void setPerm_coll_user(String perm_coll_user) {
        this.perm_coll_user = perm_coll_user;
    }

    public String getPerm_add_invoice() {
        return perm_add_invoice;
    }

    public void setPerm_add_invoice(String perm_add_invoice) {
        this.perm_add_invoice = perm_add_invoice;
    }

    public String getPerm_edit_invoice() {
        return perm_edit_invoice;
    }

    public void setPerm_edit_invoice(String perm_edit_invoice) {
        this.perm_edit_invoice = perm_edit_invoice;
    }

    public String getPerm_view_invoice() {
        return perm_view_invoice;
    }

    public void setPerm_view_invoice(String perm_view_invoice) {
        this.perm_view_invoice = perm_view_invoice;
    }

    public String getPerm_del_invoice() {
        return perm_del_invoice;
    }

    public void setPerm_del_invoice(String perm_del_invoice) {
        this.perm_del_invoice = perm_del_invoice;
    }

    public String getPerm_add_renewals() {
        return perm_add_renewals;
    }

    public void setPerm_add_renewals(String perm_add_renewals) {
        this.perm_add_renewals = perm_add_renewals;
    }

    public String getPerm_edit_renewals() {
        return perm_edit_renewals;
    }

    public void setPerm_edit_renewals(String perm_edit_renewals) {
        this.perm_edit_renewals = perm_edit_renewals;
    }

    public String getPerm_view_renewals() {
        return perm_view_renewals;
    }

    public void setPerm_view_renewals(String perm_view_renewals) {
        this.perm_view_renewals = perm_view_renewals;
    }

    public String getPerm_del_renewals() {
        return perm_del_renewals;
    }

    public void setPerm_del_renewals(String perm_del_renewals) {
        this.perm_del_renewals = perm_del_renewals;
    }

    public String getPerm_all_complaint() {
        return perm_all_complaint;
    }

    public void setPerm_all_complaint(String perm_all_complaint) {
        this.perm_all_complaint = perm_all_complaint;
    }

    public String getRole_date_created() {
        return role_date_created;
    }

    public void setRole_date_created(String role_date_created) {
        this.role_date_created = role_date_created;
    }

    public String getRole_status() {
        return role_status;
    }

    public void setRole_status(String role_status) {
        this.role_status = role_status;
    }

    public String getRole_show() {
        return role_show;
    }

    public void setRole_show(String role_show) {
        this.role_show = role_show;
    }

    public String getPerm_list_coll() {
        return perm_list_coll;
    }

    public void setPerm_list_coll(String perm_list_coll) {
        this.perm_list_coll = perm_list_coll;
    }

    public String getPerm_list_outstanding() {
        return perm_list_outstanding;
    }

    public void setPerm_list_outstanding(String perm_list_outstanding) {
        this.perm_list_outstanding = perm_list_outstanding;
    }

    public String getPerm_download_reports() {
        return perm_download_reports;
    }

    public void setPerm_download_reports(String perm_download_reports) {
        this.perm_download_reports = perm_download_reports;
    }

    public String getPerm_lead_project() {
        return perm_lead_project;
    }

    public void setPerm_lead_project(String perm_lead_project) {
        this.perm_lead_project = perm_lead_project;
    }

    public String getPerm_lead_noc() {
        return perm_lead_noc;
    }

    public void setPerm_lead_noc(String perm_lead_noc) {
        this.perm_lead_noc = perm_lead_noc;
    }

    public String getPerm_lead_billing() {
        return perm_lead_billing;
    }

    public void setPerm_lead_billing(String perm_lead_billing) {
        this.perm_lead_billing = perm_lead_billing;
    }

    public String getPerm_all_lead_project() {
        return perm_all_lead_project;
    }

    public void setPerm_all_lead_project(String perm_all_lead_project) {
        this.perm_all_lead_project = perm_all_lead_project;
    }

    public String getPerm_all_lead_noc() {
        return perm_all_lead_noc;
    }

    public void setPerm_all_lead_noc(String perm_all_lead_noc) {
        this.perm_all_lead_noc = perm_all_lead_noc;
    }

    public String getPerm_all_lead_billing() {
        return perm_all_lead_billing;
    }

    public void setPerm_all_lead_billing(String perm_all_lead_billing) {
        this.perm_all_lead_billing = perm_all_lead_billing;
    }

    public String getPerm_all_lead_feasibility() {
        return perm_all_lead_feasibility;
    }

    public void setPerm_all_lead_feasibility(String perm_all_lead_feasibility) {
        this.perm_all_lead_feasibility = perm_all_lead_feasibility;
    }

    public String getPerm_lead_documents() {
        return perm_lead_documents;
    }

    public void setPerm_lead_documents(String perm_lead_documents) {
        this.perm_lead_documents = perm_lead_documents;
    }

    public String getPerm_lead_management() {
        return perm_lead_management;
    }

    public void setPerm_lead_management(String perm_lead_management) {
        this.perm_lead_management = perm_lead_management;
    }

    public String getPerm_all_lead_management() {
        return perm_all_lead_management;
    }

    public void setPerm_all_lead_management(String perm_all_lead_management) {
        this.perm_all_lead_management = perm_all_lead_management;
    }

    public String getPerm_all_lead_procurement() {
        return perm_all_lead_procurement;
    }

    public void setPerm_all_lead_procurement(String perm_all_lead_procurement) {
        this.perm_all_lead_procurement = perm_all_lead_procurement;
    }

    public String getPerm_add_plan() {
        return perm_add_plan;
    }

    public void setPerm_add_plan(String perm_add_plan) {
        this.perm_add_plan = perm_add_plan;
    }

    public String getPerm_edit_plan() {
        return perm_edit_plan;
    }

    public void setPerm_edit_plan(String perm_edit_plan) {
        this.perm_edit_plan = perm_edit_plan;
    }

    public String getPerm_view_plan() {
        return perm_view_plan;
    }

    public void setPerm_view_plan(String perm_view_plan) {
        this.perm_view_plan = perm_view_plan;
    }

    public String getPerm_del_plan() {
        return perm_del_plan;
    }

    public void setPerm_del_plan(String perm_del_plan) {
        this.perm_del_plan = perm_del_plan;
    }

    public String getPerm_approve_plan() {
        return perm_approve_plan;
    }

    public void setPerm_approve_plan(String perm_approve_plan) {
        this.perm_approve_plan = perm_approve_plan;
    }

    public String getPerm_add_organization() {
        return perm_add_organization;
    }

    public void setPerm_add_organization(String perm_add_organization) {
        this.perm_add_organization = perm_add_organization;
    }

    public String getPerm_view_billing() {
        return perm_view_billing;
    }

    public void setPerm_view_billing(String perm_view_billing) {
        this.perm_view_billing = perm_view_billing;
    }

    public String getPerm_edit_organization() {
        return perm_edit_organization;
    }

    public void setPerm_edit_organization(String perm_edit_organization) {
        this.perm_edit_organization = perm_edit_organization;
    }

    public String getPerm_view_organization() {
        return perm_view_organization;
    }

    public void setPerm_view_organization(String perm_view_organization) {
        this.perm_view_organization = perm_view_organization;
    }

    public String getPerm_del_organization() {
        return perm_del_organization;
    }

    public void setPerm_del_organization(String perm_del_organization) {
        this.perm_del_organization = perm_del_organization;
    }

    public String getPerm_view_olt_dashboard() {
        return perm_view_olt_dashboard;
    }

    public void setPerm_view_olt_dashboard(String perm_view_olt_dashboard) {
        this.perm_view_olt_dashboard = perm_view_olt_dashboard;
    }

    public String getPerm_add_olt_ring() {
        return perm_add_olt_ring;
    }

    public void setPerm_add_olt_ring(String perm_add_olt_ring) {
        this.perm_add_olt_ring = perm_add_olt_ring;
    }

    public String getPerm_edit_olt_ring() {
        return perm_edit_olt_ring;
    }

    public void setPerm_edit_olt_ring(String perm_edit_olt_ring) {
        this.perm_edit_olt_ring = perm_edit_olt_ring;
    }

    public String getPerm_view_olt_ring() {
        return perm_view_olt_ring;
    }

    public void setPerm_view_olt_ring(String perm_view_olt_ring) {
        this.perm_view_olt_ring = perm_view_olt_ring;
    }

    public String getPerm_add_iptv_user() {
        return perm_add_iptv_user;
    }

    public void setPerm_add_iptv_user(String perm_add_iptv_user) {
        this.perm_add_iptv_user = perm_add_iptv_user;
    }

    public String getPerm_edit_iptv_user() {
        return perm_edit_iptv_user;
    }

    public void setPerm_edit_iptv_user(String perm_edit_iptv_user) {
        this.perm_edit_iptv_user = perm_edit_iptv_user;
    }

    public String getPerm_view_iptv_user() {
        return perm_view_iptv_user;
    }

    public void setPerm_view_iptv_user(String perm_view_iptv_user) {
        this.perm_view_iptv_user = perm_view_iptv_user;
    }

    public String getPerm_del_iptv_user() {
        return perm_del_iptv_user;
    }

    public void setPerm_del_iptv_user(String perm_del_iptv_user) {
        this.perm_del_iptv_user = perm_del_iptv_user;
    }

    public String getPerm_add_pop() {
        return perm_add_pop;
    }

    public void setPerm_add_pop(String perm_add_pop) {
        this.perm_add_pop = perm_add_pop;
    }

    public String getPerm_edit_pop() {
        return perm_edit_pop;
    }

    public void setPerm_edit_pop(String perm_edit_pop) {
        this.perm_edit_pop = perm_edit_pop;
    }

    public String getPerm_view_pop() {
        return perm_view_pop;
    }

    public void setPerm_view_pop(String perm_view_pop) {
        this.perm_view_pop = perm_view_pop;
    }

    public String getPerm_del_pop() {
        return perm_del_pop;
    }

    public void setPerm_del_pop(String perm_del_pop) {
        this.perm_del_pop = perm_del_pop;
    }

    public String getPerm_add_feasibility() {
        return perm_add_feasibility;
    }

    public void setPerm_add_feasibility(String perm_add_feasibility) {
        this.perm_add_feasibility = perm_add_feasibility;
    }

    public String getPerm_edit_feasibility() {
        return perm_edit_feasibility;
    }

    public void setPerm_edit_feasibility(String perm_edit_feasibility) {
        this.perm_edit_feasibility = perm_edit_feasibility;
    }

    public String getPerm_view_feasibility() {
        return perm_view_feasibility;
    }

    public void setPerm_view_feasibility(String perm_view_feasibility) {
        this.perm_view_feasibility = perm_view_feasibility;
    }

    public String getPerm_accept_feasibility() {
        return perm_accept_feasibility;
    }

    public void setPerm_accept_feasibility(String perm_accept_feasibility) {
        this.perm_accept_feasibility = perm_accept_feasibility;
    }

    public String getPerm_bulk_upload_feasibility() {
        return perm_bulk_upload_feasibility;
    }

    public void setPerm_bulk_upload_feasibility(String perm_bulk_upload_feasibility) {
        this.perm_bulk_upload_feasibility = perm_bulk_upload_feasibility;
    }

    public String getPerm_view_iptv_invoice() {
        return perm_view_iptv_invoice;
    }

    public void setPerm_view_iptv_invoice(String perm_view_iptv_invoice) {
        this.perm_view_iptv_invoice = perm_view_iptv_invoice;
    }

    public String getPerm_view_caf() {
        return perm_view_caf;
    }

    public void setPerm_view_caf(String perm_view_caf) {
        this.perm_view_caf = perm_view_caf;
    }

    public String getPerm_view_circuit_status() {
        return perm_view_circuit_status;
    }

    public void setPerm_view_circuit_status(String perm_view_circuit_status) {
        this.perm_view_circuit_status = perm_view_circuit_status;
    }

    public String getPerm_view_admin_status() {
        return perm_view_admin_status;
    }

    public void setPerm_view_admin_status(String perm_view_admin_status) {
        this.perm_view_admin_status = perm_view_admin_status;
    }

    public String getPerm_view_operational_status() {
        return perm_view_operational_status;
    }

    public void setPerm_view_operational_status(String perm_view_operational_status) {
        this.perm_view_operational_status = perm_view_operational_status;
    }

    public String getPerm_view_cpe_down_cause() {
        return perm_view_cpe_down_cause;
    }

    public void setPerm_view_cpe_down_cause(String perm_view_cpe_down_cause) {
        this.perm_view_cpe_down_cause = perm_view_cpe_down_cause;
    }

    public String getPerm_view_cpe_up_status() {
        return perm_view_cpe_up_status;
    }

    public void setPerm_view_cpe_up_status(String perm_view_cpe_up_status) {
        this.perm_view_cpe_up_status = perm_view_cpe_up_status;
    }

    public String getPerm_view_cpe_down_status() {
        return perm_view_cpe_down_status;
    }

    public void setPerm_view_cpe_down_status(String perm_view_cpe_down_status) {
        this.perm_view_cpe_down_status = perm_view_cpe_down_status;
    }

    public String getPerm_view_complaints() {
        return perm_view_complaints;
    }

    public void setPerm_view_complaints(String perm_view_complaints) {
        this.perm_view_complaints = perm_view_complaints;
    }

    public String getPerm_view_graphical() {
        return perm_view_graphical;
    }

    public void setPerm_view_graphical(String perm_view_graphical) {
        this.perm_view_graphical = perm_view_graphical;
    }

    public String getPerm_view_recent_logs() {
        return perm_view_recent_logs;
    }

    public void setPerm_view_recent_logs(String perm_view_recent_logs) {
        this.perm_view_recent_logs = perm_view_recent_logs;
    }

    public String getPerm_view_teleconf_users() {
        return perm_view_teleconf_users;
    }

    public void setPerm_view_teleconf_users(String perm_view_teleconf_users) {
        this.perm_view_teleconf_users = perm_view_teleconf_users;
    }

    public String getPerm_view_teleconf_invoices() {
        return perm_view_teleconf_invoices;
    }

    public void setPerm_view_teleconf_invoices(String perm_view_teleconf_invoices) {
        this.perm_view_teleconf_invoices = perm_view_teleconf_invoices;
    }

    public String getPerm_payment_approval() {
        return perm_payment_approval;
    }

    public void setPerm_payment_approval(String perm_payment_approval) {
        this.perm_payment_approval = perm_payment_approval;
    }

    public String getPerm_add_credit_note() {
        return perm_add_credit_note;
    }

    public void setPerm_add_credit_note(String perm_add_credit_note) {
        this.perm_add_credit_note = perm_add_credit_note;
    }

    public String getPerm_view_credit_note() {
        return perm_view_credit_note;
    }

    public void setPerm_view_credit_note(String perm_view_credit_note) {
        this.perm_view_credit_note = perm_view_credit_note;
    }

    public String getPerm_add_change_request() {
        return perm_add_change_request;
    }

    public void setPerm_add_change_request(String perm_add_change_request) {
        this.perm_add_change_request = perm_add_change_request;
    }

    public String getPerm_view_change_request() {
        return perm_view_change_request;
    }

    public void setPerm_view_change_request(String perm_view_change_request) {
        this.perm_view_change_request = perm_view_change_request;
    }

    public String getPerm_edit_change_request() {
        return perm_edit_change_request;
    }

    public void setPerm_edit_change_request(String perm_edit_change_request) {
        this.perm_edit_change_request = perm_edit_change_request;
    }

    public String getPerm_approve_change_request() {
        return perm_approve_change_request;
    }

    public void setPerm_approve_change_request(String perm_approve_change_request) {
        this.perm_approve_change_request = perm_approve_change_request;
    }

    public String getPerm_preliminary_approve_cr() {
        return perm_preliminary_approve_cr;
    }

    public void setPerm_preliminary_approve_cr(String perm_preliminary_approve_cr) {
        this.perm_preliminary_approve_cr = perm_preliminary_approve_cr;
    }

    public String getPerm_add_feedback_form() {
        return perm_add_feedback_form;
    }

    public void setPerm_add_feedback_form(String perm_add_feedback_form) {
        this.perm_add_feedback_form = perm_add_feedback_form;
    }

    public String getPerm_view_feedback_form() {
        return perm_view_feedback_form;
    }

    public void setPerm_view_feedback_form(String perm_view_feedback_form) {
        this.perm_view_feedback_form = perm_view_feedback_form;
    }

    public String getPerm_view_account_summary() {
        return perm_view_account_summary;
    }

    public void setPerm_view_account_summary(String perm_view_account_summary) {
        this.perm_view_account_summary = perm_view_account_summary;
    }

    public String getPerm_dept_complaint() {
        return perm_dept_complaint;
    }

    public void setPerm_dept_complaint(String perm_dept_complaint) {
        this.perm_dept_complaint = perm_dept_complaint;
    }

    public String getPerm_ent_complaint() {
        return perm_ent_complaint;
    }

    public void setPerm_ent_complaint(String perm_ent_complaint) {
        this.perm_ent_complaint = perm_ent_complaint;
    }

    public String getLogin_emp_id() {
        return login_emp_id;
    }

    public void setLogin_emp_id(String login_emp_id) {
        this.login_emp_id = login_emp_id;
    }

    public String getLogin_username() {
        return login_username;
    }

    public void setLogin_username(String login_username) {
        this.login_username = login_username;
    }

    public String getAccessToken() {
        return accesstoken;
    }

    public void setAccessToken(String accessToken) {
        this.accesstoken = accessToken;
    }
}

