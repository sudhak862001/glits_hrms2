package com.Tejaysdr.msrc.activitys.emp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import com.Tejaysdr.msrc.adapter.UsedInventoryAdapter;
import com.Tejaysdr.msrc.dataModel.UsedInventoryModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.dataModel.FileModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.*;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.dataModel.CategoryListData;
import com.Tejaysdr.msrc.dataModel.ClosedcompData;
import com.Tejaysdr.msrc.dataModel.ComplaintData;
import com.Tejaysdr.msrc.dataModel.InventoryData;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.Tejaysdr.msrc.R.color.orange;

public class UpdateComplaintActivity extends BaseActivity {
    UsedInventoryAdapter adapter;
    ComplaintData complaintData;
    String  getCount;
    TextView empname, complaint_stats, t;
    ArrayList<CategoryListData> categoryList;
    LinearLayout Add_complaints_definition, section_inventory, sub1_section_inventory, sub2_section_inventory, li_reading;
    boolean isOpen;
    EditText Add_complaints, used_qty, used_qty1, used_qty2;
    Spinner sp_pending_complaints, spin_serial,select_inventory, select_inventory1, sp_closed_complaints, select_inventory2,select_inventoryalert;
    String[] pending_complaints;
    LinearLayout linearLayout1,linearLayout2;
    ArrayList<InventoryData> inventoryDataList = new ArrayList<>();
    ArrayList<InventoryData> inventoryDataList1 = new ArrayList<>();
    CheckBox check_used_inventory,is_hardware_usd;
    String name,avl_quantity=null;
    RelativeLayout sub_section_inventory;
    GPSTracker gps;
    String complaint_id, comp_status, getComplaint_id, getempID, remarks, closed_img_name = "", closed_img_name1 = "",
            closed_img_name2 = "",closed_img_name3 = "",closed_img_name4 = "",closed_img_name5 = "", gio_loc, ba1,ba2,ba3,ba4,ba5,ba6, picturePath = "",
            picture1Path = "", picture2Path = "",picture3Path = "",picture4Path = "",picture5Path = "",
            getcomp_cat, cos_data, getclosedcompID, getselectinventory1 = "", getselectinventory2 = "",
            getselectinventory3 = "", getused_qty, getused_qty1, getused_qty2;
    Uri selectedImage, fileUri;
    Bitmap photo;
    String getcomplaint;
    String q1 = "0";
    String q2 = "0";
    int inventoryID;
    public static String ImageUploadURL;
    ImageView imageView, Imageprev1, Imageprev2,imageView3,imageView4,imageView5;
    ArrayList<ClosedcompData> listclosedcompData = new ArrayList<>();
    public static Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    FloatingActionButton FAB,FAB_hardware;
    ScrollView scroll_move;
    int count,count1;
    private OperatorCode operatorCode;
    private String mainURL;
    private String uri;
//    private String inventoryID;
    private String closedImageName;
    private double latitude, longitude;
    private Bitmap bitmap;

    private Bitmap bitmap1;
    private String datetime, currentDate, currentTime;
    String timeMilli;
    private String setselectused_quy,setselectissue;
    private  float  batteryperct;
    Toolbar toolbar_update;
    EditText Quality1, Quality2, et_serialno,Quality1_alert,Quality2_alert;
    TextView et_result;
    LinearLayout li_metersused,ll_img1,ll_img2,ll_img,ll_hardwareimgs,lissuefound;
    private String getselectinventory1name;
    private String getselectinventory2name;
    private String invgroup;
    private String invgroup123=null;

    View view;
    String quantity1 ;
    String quantity2 ;
    String serialno ;
int x=0,y=0;
    Button button, button1, button2;
    private String spinnerstatus, complaintcatstatus, complaintissue1, complaintdate1, datecreated;
    TextView complaintissue, complaintdate;
    private String complaint;
    String[] Assigned = {"Assigned","Accepted","InTransit","Onsite","Work In Progress","Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] Accepted = {"Accepted","InTransit","Onsite","Work In Progress","Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] InTransit = {"InTransit","Onsite","Work In Progress","Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] Onsite = {"Onsite","Work In Progress","Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] WorkInProgress = {"Work In Progress","Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] PendingForSpares = {"Pending For Spares","Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] PendingForCustomerDependency = {"Pending For Customer Dependency","Pending For Others","Resolved"};
    String[] PendingForOthers = {"Pending For Others","Resolved"};
    String[] Resolved = {"Resolved"};
    String[] Qualitycheck = {"Quality Check"};
    String[] Closed = {""};
    ArrayList<FileModel>filearraylist=new ArrayList<>();
    private Bitmap bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;

    RecyclerView rv_inventorylist;
    AlertDialog dialog;
    LinearLayout isMaterial;
    List<UsedInventoryModel> modelList=new ArrayList<>();

    @SuppressLint({"RestrictedApi", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_complaint);


        t = findViewById(R.id.issuefound);
        button2 = findViewById(R.id.tvPaymentName);
        complaintdate = findViewById(R.id.datecreated);
        et_result = findViewById(R.id.et_result);
        Quality1=findViewById(R.id.Quality1);
        Quality2 = findViewById(R.id.Quality2);
        li_metersused = findViewById(R.id.li_metersused);
        ll_img1 = findViewById(R.id.ll_img1_hardware);
        ll_img2 = findViewById(R.id.ll_img2_hardware);
        ll_img = findViewById(R.id.ll_img_hardware);
        ll_hardwareimgs = findViewById(R.id.ll_ishardwareremovedimages);
        et_serialno = findViewById(R.id.et_serialno);
        isMaterial = findViewById(R.id.ismaterialused);
        li_reading = findViewById(R.id.li_reading);
        rv_inventorylist=findViewById(R.id.rv_no_of_inventories);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_inventorylist.setLayoutManager(mLayoutManager);

        /*Quality1=findViewById(R.id.Quality1);*/

      /*  q1=Quality1.getText().toString();
        q2=Quality2.getText().toString();*/
        // et_result.setText(String.valueOf(q1)+"/"+String.valueOf(q2)+" " +  "Requests");
        // et_result.setText(Integer.parseInt(q1)-Integer.parseInt(q2));
        isOpen = false;
        complaintData = (ComplaintData) getIntent().getExtras().getSerializable("complaintData");
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // Toast.makeText(UpdateComplaintActivity.this, ""+currentDate, Toast.LENGTH_SHORT).show();
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        toolbar_update = findViewById(R.id.toolbar_update);
        view = findViewById(R.id.view);
        setSupportActionBar(toolbar_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_update.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Complaint Details");
        //Toast.makeText(UpdateComplaintActivity.this, ""+currentTime, Toast.LENGTH_SHORT).show();
//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatus = UpdateComplaintActivity.this.registerReceiver(null, ifilter);
//        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//        float batteryPct = (level * 100) / (float)scale;
//    Toast.makeText(UpdateComplaintActivity.this, "Battery Percentage "+batteryPct, Toast.LENGTH_SHORT).show();
        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        String mainURL = BaseUrl.getBaseurl();
        if (SaveAppData.getSessionDataInstance().getOperatorData() != null || SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
            // OperatorCode opCode = null;
          /*  opCode = SaveAppData.getSessionDataInstance().getOperatorData();
            mainURL = opCode.getop_url();*/

        }

        ImageUploadURL = BaseUrl.getBaseurl() + "uploads/upload.php";
        getempID = operatorCode.getEmp_id();
        getempID = SaveAppData.getOperatorLoginData().getEmp_id();
        //getComplaintCategoryList();
       /* Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(COMPLAINTS_CATEGORYHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_CATEGORY_LIST.ordinal());
        startService(dataIntent);*/
        empname = (TextView) findViewById(R.id.in_empname);
        complaint_stats = (TextView) findViewById(R.id.complaint_stats);
        complaintdate = findViewById(R.id.datecreated);
        complaintissue = (TextView) findViewById(R.id.compl_desc);
        Add_complaints_definition = (LinearLayout) findViewById(R.id.Add_complaints_definition);
        Add_complaints = (EditText) findViewById(R.id.Add_complaints);
        sp_pending_complaints = (Spinner) findViewById(R.id.sp_pending_complaints);
        sp_closed_complaints = (Spinner) findViewById(R.id.sp_closed_complaints);
        section_inventory = (LinearLayout) findViewById(R.id.section_inventory);
        lissuefound = (LinearLayout) findViewById(R.id.lissuefound);
        select_inventory = (Spinner) findViewById(R.id.select_inventory);
        select_inventory1 = (Spinner) findViewById(R.id.select_inventory1);
        select_inventory2 = (Spinner) findViewById(R.id.select_inventory2);
        used_qty = (EditText) findViewById(R.id.used_qty);
        used_qty1 = (EditText) findViewById(R.id.used_qty1);
        used_qty2 = (EditText) findViewById(R.id.used_qty2);
        check_used_inventory = (CheckBox) findViewById(R.id.check_used_inventory);
        is_hardware_usd = (CheckBox) findViewById(R.id.cb_ishardwareremoved);
        sub_section_inventory = (RelativeLayout) findViewById(R.id.sub_section_inventory);
        imageView = (ImageView) findViewById(R.id.Imageprev);
        Imageprev1 = (ImageView) findViewById(R.id.Imageprev1);
        Imageprev2 = (ImageView) findViewById(R.id.Imageprev2);
        imageView3 = (ImageView) findViewById(R.id.Imageprev_hardware);
        imageView4 = (ImageView) findViewById(R.id.Imageprev1_hardware);
        imageView5 = (ImageView) findViewById(R.id.Imageprev2_hardware);
        scroll_move = (ScrollView) findViewById(R.id.scroll_move);
        button1 = findViewById(R.id.btnimg);
        button1.setBackgroundColor(orange);
        sub1_section_inventory = (LinearLayout) findViewById(R.id.sub1_section_inventory);
        sub2_section_inventory = (LinearLayout) findViewById(R.id.sub2_section_inventory);
        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB_hardware = (FloatingActionButton) findViewById(R.id.fab_hardware);
        FAB.setVisibility(View.GONE);
        FAB_hardware.setVisibility(View.GONE);
        count = 0;
        count1 = 0;
        complaint_id=complaintData.getComplaintId();
        Intent intent = getIntent();
        spinnerstatus = intent.getStringExtra("spinnerstatus");
        complaintissue1 = String.valueOf(intent.getStringArrayExtra("complaintdesc"));
        complaintissue.setText(complaintissue1);
        complaintdate1 = intent.getStringExtra("complaintdate");
        complaintdate.setText(complaintdate1);

        // complaint = intent.getStringExtra("complaint");
        complaintcatstatus = String.valueOf(intent.getStringArrayExtra("complaintstatus"));

        if (spinnerstatus.equals("Qualitycheck")) {

            section_inventory.setVisibility(View.GONE);
            lissuefound.setVisibility(View.GONE);

        }

//        if (quantity1.equals("") || quantity1.equals("0")) {
//            quantity1 = "0";
//        } else {
//            quantity2 = Quality2.getText().toString();
//        }
//        if (quantity2.equals("") || quantity2.equals("0")) {
//            quantity2 = "0";
//        } else {
//            if (Integer.parseInt(quantity2) > Integer.parseInt(quantity1)) {
//                quantity2 = Quality2.getText().toString();
//                Toast.makeText(UpdateComplaintActivity.this, "Reading two always greater then reading one", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (serialno.equals("") || quantity2.equals("0")) {
//            serialno = "0";
//        } else {
//            serialno = et_serialno.getText().toString();
//        }

        FAB_hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll_move.fullScroll(ScrollView.FOCUS_DOWN);
                count1 = count1 + 1;
                if (count1 == 1) {
                    ll_img1.setVisibility(View.VISIBLE);
                } else if (count1 == 2) {
                    ll_img2.setVisibility(View.VISIBLE);
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), "Only Three Edit Options", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll_move.fullScroll(ScrollView.FOCUS_DOWN);
                count = count + 1;
                if (count == 1) {
                    sub1_section_inventory.setVisibility(View.VISIBLE);
                } else if (count == 2) {
                    sub2_section_inventory.setVisibility(View.VISIBLE);
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), "Only Three Edit Options", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });



//        cos_data=complaintData.getfirst_name()+" ("+complaintData.getcustom_customer_no()+") "+complaintData.getmobile_no()+", "+complaintData.getcity();
//        getcomp_cat=complaintData.getcomp_cat();
        empname.setText(cos_data);
        complaintdate1 = complaintData.getCompDateCreated();
        complaintdate.setText(complaintdate1);
        complaintissue1 = complaintData.getCompCategory();
        complaintissue.setText(complaintissue1);
//        cos_data = complaintData.getFirstName() + (" " + complaintData.getCompNo());




   if(complaintData.getComusertype().equals("1")){
       cos_data = complaintData.getComnmaeteleco() + (" " + complaintData.getCompNo());
   }
   else{
       cos_data = complaintData.getCompany_name() + (" " + complaintData.getCompNo());
   }
               empname.setText(cos_data);
//    spending_complaints = getResources().getStringArray(R.array.PendingComplaintsList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint, pending_complaints);
//        sp_pending_complaints.setAdapter(adapter);
        if (spinnerstatus.equals("Assigned")) {
//            int seleectdeposition = adapter.getPosition("Assigned");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Assigned);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Accepted")) {
//            int seleectdeposition = adapter.getPosition("Accepted");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Accepted);
            sp_pending_complaints.setAdapter(adapter);

//            sp_pending_complaints.setSelection(seleectdeposition);

        } else if (spinnerstatus.equals("InTransit")) {
//            int seleectdeposition = adapter.getPosition("InTransit");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,InTransit);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Onsite")) {
//            int seleectdeposition = adapter.getPosition("Onsite");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Onsite);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Work In Progress")) {
//            int seleectdeposition = adapter.getPosition("Work In Progress");
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.spinner_complaint,WorkInProgress);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Pending For Spares")) {
//            int seleectdeposition = adapter.getPosition("Pending For Spares");
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.spinner_complaint, PendingForSpares);
            sp_pending_complaints.setAdapter(adapter);
        } else if (spinnerstatus.equals("Pending For Customer Dependency")) {
//            int seleectdeposition = adapter.getPosition("Pending For Customer Dependency");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,PendingForCustomerDependency);
            sp_pending_complaints.setAdapter(adapter);


        } else if (spinnerstatus.equals("Pending For Others")) {
//            int seleectdeposition = adapter.getPosition("Pending For Others");
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.spinner_complaint,PendingForOthers);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Resolved")) {
//            int seleectdeposition = adapter.getPosition("Resolved");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Resolved);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Qualitycheck")) {
//            int seleectdeposition = adapter.getPosition("Resolved");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Qualitycheck);
            sp_pending_complaints.setAdapter(adapter);

        } else if (spinnerstatus.equals("Closed")) {
//            int seleectdeposition = adapter.getPosition("Closed");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint,Closed);
            sp_pending_complaints.setAdapter(adapter);


        }
        //String getcomp_status=complaintData.getcomp_status();
       /* int position=Integer.parseInt(getcomp_status);
        if(position==0){
            sp_pending_complaints.setSelection(0);
        }else if(position==1){
            sp_pending_complaints.setSelection(1);
        }else if(position==2){
            sp_pending_complaints.setSelection(2);
        }*/

        gps = new GPSTracker(UpdateComplaintActivity.this);
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        }

        startTimer();
        sp_pending_complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String getcomplaint = pending_complaints[position];
                 getcomplaint=sp_pending_complaints.getSelectedItem().toString();
                if (getcomplaint.equalsIgnoreCase("Assigned")) {
                    comp_status = "Assigned";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);

                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Accepted")) {
                    comp_status = "Accepted";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);

                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("InTransit")) {
                    comp_status = "InTransit";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                } else if (getcomplaint.equalsIgnoreCase("Onsite")) {
                    comp_status = "Onsite";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Work In Progress")) {
                    comp_status = "Workinprogress";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Pending For Spares")) {
                    comp_status = "Pendingforspares";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Pending For Customer Dependency")) {
                    comp_status = "Pendingforcustomerdependency";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Pending for Others")) {
                    comp_status = "Pendingforothers";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Resolved")) {
                    comp_status = "Resolved";
                    t.setVisibility(View.VISIBLE);
                    sp_closed_complaints.setVisibility(View.VISIBLE);
                    section_inventory.setVisibility(View.VISIBLE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    et_serialno.setVisibility(view.GONE);
                    used_qty.setVisibility(View.VISIBLE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                    name = "";
                } else if (getcomplaint.equalsIgnoreCase("Quality Check")) {
                    comp_status = "Qualitycheck";

                } else if (getcomplaint.equalsIgnoreCase("Closed")) {
                    comp_status = "Closed";
                    t.setVisibility(View.GONE);
                    sp_closed_complaints.setVisibility(View.GONE);
                    section_inventory.setVisibility(View.GONE);
                    select_inventory.setSelection(View.GONE);
                    select_inventory1.setVisibility(View.GONE);
                    select_inventory2.setVisibility(View.GONE);
                    // li_metersused.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    used_qty.setVisibility(View.GONE);
                    used_qty2.setVisibility(View.GONE);
                    used_qty1.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
//                     button2.setVisibility(View.GONE);
                    name = "";
                }

//                if(getcomplaint.equalsIgnoreCase("New Complaint")){
//                    comp_status="0";
//                    section_inventory.setVisibility(View.GONE);
//                    select_inventory1.setVisibility(View.GONE);
//                    select_inventory2.setVisibility(View.GONE);
//                    //li_metersused.setVisibility(View.GONE);
//                    li_reading.setVisibility(View.GONE);
//                    name="";
//                }else if(getcomplaint.equalsIgnoreCase("Inprogress")){
//                    comp_status="1";
//                    section_inventory.setVisibility(View.GONE);
//                    select_inventory1.setVisibility(View.GONE);
//                    select_inventory2.setVisibility(View.GONE);
//                    // li_metersused.setVisibility(View.GONE);
//                    li_reading.setVisibility(View.GONE);
//                    used_qty.setVisibility(View.GONE);
//                    used_qty2.setVisibility(View.GONE);
//                    used_qty1.setVisibility(View.GONE);
//                    name="";
//                }
//                else if(getcomplaint.equalsIgnoreCase("Pending")){
//                    comp_status="2";
//                    section_inventory.setVisibility(View.GONE);
//                    select_inventory1.setVisibility(View.GONE);
//                    select_inventory2.setVisibility(View.GONE);
//                    used_qty.setVisibility(View.GONE);
//                    used_qty2.setVisibility(View.GONE);
//                    used_qty1.setVisibility(View.GONE);
//                    li_metersused.setVisibility(View.GONE);
//                    li_reading.setVisibility(View.GONE);
//                    name="";
//                }else if(getcomplaint.equalsIgnoreCase("Resolved")){
//                    comp_status="7";
//                    section_inventory.setVisibility(View.VISIBLE);
//                    select_inventory1.setVisibility(View.VISIBLE);
//                    select_inventory2.setVisibility(View.VISIBLE);
//                    used_qty.setVisibility(View.VISIBLE);
//                    used_qty2.setVisibility(View.VISIBLE);
//                    used_qty1.setVisibility(View.VISIBLE);
//                    li_metersused.setVisibility(View.GONE);
//                    li_reading.setVisibility(View.GONE);
//                }
//                else if(getcomplaint.equalsIgnoreCase("Completed")){
//                    comp_status="9";
//                    section_inventory.setVisibility(View.GONE);
//                    select_inventory1.setVisibility(View.GONE);
//                    select_inventory2.setVisibility(View.GONE);
//                    // li_metersused.setVisibility(View.GONE);
//                    li_reading.setVisibility(View.GONE);
//                    used_qty.setVisibility(View.GONE);
//                    used_qty2.setVisibility(View.GONE);
//                    used_qty1.setVisibility(View.GONE);
//                    name="";
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        is_hardware_usd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_hardwareimgs.setVisibility(View.VISIBLE);
                    ll_img.setVisibility(View.VISIBLE);
                    y=1;
//                    Toast.makeText(UpdateComplaintActivity.this, ""+y, Toast.LENGTH_SHORT).show();

                } else {
                    ll_hardwareimgs.setVisibility(View.GONE);
                    ll_img.setVisibility(View.GONE);
                    y=0;
                   /* if (!closed_img_name3.isEmpty()||!closed_img_name4.isEmpty()||!closed_img_name5.isEmpty()){
                        closed_img_name3="";
                        closed_img_name4="";
                        closed_img_name5="";
//                        Toast.makeText(UpdateComplaintActivity.this, "wertyui"+y, Toast.LENGTH_SHORT).show();
                    }*/
//                    Toast.makeText(UpdateComplaintActivity.this, ""+y, Toast.LENGTH_SHORT).show();
                }
            }
        });
        check_used_inventory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sub_section_inventory.setVisibility(View.GONE);
                    et_serialno.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    isMaterial.setVisibility(View.VISIBLE);
//                    view.setVisibility(View.VISIBLE);

                } else {
                    sub_section_inventory.setVisibility(View.GONE);
                    et_serialno.setVisibility(View.GONE);
                    li_reading.setVisibility(View.GONE);
                    isMaterial.setVisibility(View.GONE);
//                    view.setVisibility(view.GONE);
                }
            }
        });
        getCloseComplaintComt();
        /*sp_closed_complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // getclosedcompID=listclosedcompData.get(position).getclosed_id();
                getclosedcompID = listclosedcompData.get(position).getSubCatName();
                // sp_closed_complaints.setSelection(Integer.parseInt(complaint));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getclosedcompID = "";
            }

        });*/
//        Toast.makeText(this, "getselectinventory"+getselectinventory1, Toast.LENGTH_SHORT).show();
       // serialnumapiintegration();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Add Inventory");
        getInventoryList();
        View view = getLayoutInflater().inflate(R.layout.custom_usedinventory,null);
        TextView buttonOk = view.findViewById(R.id.buttonOk);
        LinearLayout ll_alert= view.findViewById(R.id.ll_inventory_alert);
         select_inventoryalert = view.findViewById(R.id.select_inventoryalert);
         RelativeLayout relativeLayout=view.findViewById(R.id.relativelayout);
          spin_serial = view.findViewById(R.id.spin_serial);
        EditText used_qty= view.findViewById(R.id.used_qty);
        ImageView iv_delete= view.findViewById(R.id.iv_delete);
        Quality1_alert= view.findViewById(R.id.Quality1_alert);
        Quality2_alert= view.findViewById(R.id.Quality2_alert);
        Button addInventory = view.findViewById(R.id.add_inventory);
        LinearLayout customcard= view.findViewById(R.id.ll_customcard);
         linearLayout1=view.findViewById(R.id.li_reading1);
        linearLayout2=view.findViewById(R.id.linearserial);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_serial.setAdapter(adapter);*/

        ll_alert.setVisibility(View.VISIBLE);
        iv_delete.setVisibility(View.GONE);
        addInventory.setVisibility(View.VISIBLE);
        customcard.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        builder.setView(view);
        dialog = builder.create();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
       dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.7f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        dialog.getWindow().setAttributes(lp);

        dialog.setCanceledOnTouchOutside(false);
        addInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spin_select = select_inventoryalert.getSelectedItem().toString();
                String srlnum = spin_serial.getSelectedItem().toString();
                String usdqty1 = used_qty.getText().toString();
                String rdng1 = Quality1_alert.getText().toString();
                String rdng2 = Quality2_alert.getText().toString();
//                used_qty.getText().clear();
//                 spin_serial.setSelection(0);

                if (spin_select.equalsIgnoreCase("Select")) {
                    Toast.makeText(getApplicationContext(), "please select spinner", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(usdqty1) > Integer.parseInt(avl_quantity)) {
                    used_qty.setError("Insufficient Quantity");
                }
//              else if (getselectinventory1.equalsIgnoreCase("1")){
////                    else if(invgroup123.equals("77")){
//                    if (rdng1.equals("")) {
//                    Quality1_alert.setError("Please enter start reading");
//
//                } else if (rdng2.equals("")) {
//                    Quality2_alert.setError("Please enter end reading");
//                }else {
//                        addtomodellist(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
//                    }
//                }else if (getselectinventory1.equalsIgnoreCase("5")){
////                    if(invgroup123.equals("84"))
//                    if (srlnum.equalsIgnoreCase("select")) {
//                       Toast.makeText(UpdateComplaintActivity.this,"please select spinner",Toast.LENGTH_LONG).show();
//                    }else {
//                        addtomodellist(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
//                    }
//                }
              else if(invgroup123.equals("77")) {
                    if (rdng1.equals("")) {
                    Quality1_alert.setError("Please enter start reading");

                } else if (rdng2.equals("")) {
                        Quality2_alert.setError("Please enter end reading");
                    }
                    else
                    addtomodellist(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
                }
              else {
                    if (srlnum.equalsIgnoreCase("select")) {
                       Toast.makeText(UpdateComplaintActivity.this,"please select spinner",Toast.LENGTH_LONG).show();
                    }else {
                        addtomodellist(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
                    }
                }


//                select_inventoryalert.setSelection(0);
//                else if(rdng1.equals(Quality1_alert.getText().toString())){
//                    Quality1.setError("Please enter start reading");
//                }
//                else if (rdng1.equals("")) {
//                    Quality1_alert.setError("Please enter start reading");
//
//                } else if (rdng2.equals("")) {
//                    Quality2_alert.setError("Please enter end reading");
//                }
//                else {
//                    addtomodellist(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
//                }
            }




        });


        isMaterial.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
//                getInventoryList();
                dialog.show();
            }
        });



    }

    private void addtomodellist(String getselectinventory1, String spin_select, String usdqty1, String rdng1, String rdng2, String srlnum) {
//        for(:modelList){
//            if(spin_select.startsWith("C")){
//                Toast.makeText(UpdateComplaintActivity.this,"already added",Toast.LENGTH_LONG).show();
//            }
//        }
        dialog.dismiss();
        UsedInventoryModel model = new UsedInventoryModel(getselectinventory1, spin_select, usdqty1, rdng1, rdng2, srlnum);
        rv_inventorylist.setVisibility(View.VISIBLE);


//                    if (modelList.contains(model)==true) {
//                        Toast.makeText(UpdateComplaintActivity.this, "already added", Toast.LENGTH_SHORT).show();
//                    } else

        modelList.add(model);
        adapter = new UsedInventoryAdapter(UpdateComplaintActivity.this, modelList);
        rv_inventorylist.setAdapter(adapter);

        select_inventoryalert.setSelection(0);
        Quality1_alert.getText().clear();
        Quality2_alert.getText().clear();

        return;
    }

    private void serialnumapiintegration(String getselectinventory1) {
        /*final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();*/
//        Toast.makeText(UpdateComplaintActivity.this, "Task Updated inventory"+getselectinventory1, Toast.LENGTH_SHORT).show();
        //       uri = BaseUrl.getBaseurl() + "Employee/emp_inv_list_items";
        uri = BaseUrl.getBaseurl() + "employee/inventory_wise_serial_numbers";
     //   uri = "https://tejays.digitalrupay.com/webservices/employee/inventory_wise_serial_numbers";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   dialog.dismiss();
//                Toast.makeText(UpdateComplaintActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(InventoryListActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String response12 = jsonObject.getString("response");
                    /*InventoryData data2=new InventoryData();
                    data2.setAssign_inv_sl_no("Select");
                    inventoryDataList1.add(data2);*/
//                    data2.setId("0");
//                   data2.setInvGroup("0");

                    if (response12.equalsIgnoreCase("Success")) {
                        inventoryDataList1.clear();
//                        Toast.makeText(UpdateComplaintActivity.this, "Task Updated 1", Toast.LENGTH_SHORT).show();
                        InventoryData inventoryData1=new InventoryData();
                        inventoryData1.setAssign_inv_sl_no("Select");
                        inventoryDataList1.add(inventoryData1);

                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("msg") && !key.equalsIgnoreCase("response")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<InventoryData>() {
                                }.getType());
                                inventoryDataList1.add(inventoryData);
                          //      invgroup = inventoryData.getInvGroup();
                            }

                            String[] inventorydata= new String[inventoryDataList1.size()];
                            for(int i=0;i<inventorydata.length;i++){
                                inventorydata[i]=inventoryDataList1.get(i).getAssign_inv_sl_no();
                            }

                            ArrayAdapter arrayAdapter=new ArrayAdapter(UpdateComplaintActivity.this,R.layout.spinner_text,inventorydata);
                            arrayAdapter.setDropDownViewResource(R.layout.spinner_complaint);
                            spin_serial.setAdapter(arrayAdapter);



                            //                            ComplaintInventoryListAdapter adapter = new ComplaintInventoryListAdapter(UpdateComplaintActivity.this, inventoryDataList);
//                            select_inventory.setAdapter(adapter);
//                            select_inventory1.setAdapter(adapter);
//                            select_inventory2.setAdapter(adapter);


                            spin_serial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    /*getselectinventory1 = inventoryDataList.get(position).getInvId();
                                    getselectinventory1name = inventoryDataList.get(position).getInvName();
                                    invgroup123 = inventoryDataList.get(position).getInvGroup();
                                    avl_quantity = inventoryDataList.get(position).getAvlqty();
                                    if (invgroup123.equals("77")) {
                                        li_metersused.setVisibility(View.VISIBLE);
                                        li_reading.setVisibility(View.VISIBLE);
                                    } else {
                                        li_metersused.setVisibility(View.GONE);
                                        li_reading.setVisibility(View.GONE);

                                    }*/
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });



                        }
                    } else {
//                        dialog.dismiss();
                        // Toast.makeText(UpdateComplaintActivity.this, ""+response12, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    dialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   dialog.dismiss();
                InventoryData data2=new InventoryData();
                data2.setAssign_inv_sl_no("Select");
//                    data2.setId("0");
//                   data2.setInvGroup("0");
                inventoryDataList1.add(data2);
                String[] inventorydata= new String[inventoryDataList1.size()];
                for(int i=0;i<inventorydata.length;i++){
                    inventorydata[i]=inventoryDataList1.get(i).getAssign_inv_sl_no();
                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(UpdateComplaintActivity.this,R.layout.spinner_text,inventorydata);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_complaint);
                spin_serial.setAdapter(arrayAdapter);


//                Toast.makeText(UpdateComplaintActivity.this, "error"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
             //   params.put("emp_id", String.valueOf(17));
                params.put("emp_id",getempID);
                params.put("inv_id",getselectinventory1);
             //   params.put("inv_id", String.valueOf(4));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    private void getComplaintCategoryList() {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Please Wait..");
//        dialog.show();
//
//       /* operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
//        mainURL = operatorCode.getop_url();
//        uri=mainURL+"get_comp_cat.php";*/
//        //String  getcategorylist= BaseUrl.getBaseurl()+"Employee/complaint_issues_info";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    dialog.dismiss();
//                    String message = jsonObject.getString("message");
//                    if (message.equalsIgnoreCase("success")) {
//                        categoryList = new ArrayList<>();
//                        Iterator<String> keys = jsonObject.keys();
//                        while (keys.hasNext()) {
//                            String key = keys.next();
//                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
//                                CategoryListData categoryListData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<CategoryListData>() {
//                                }.getType());
//                                categoryList.add(categoryListData);
//                            }
//                        }
//                        for (int i = 0; i < categoryList.size(); i++) {
//                            String comp_id = categoryList.get(i).getid();
//                            if (comp_id.equalsIgnoreCase(getcomp_cat)) {
//                                String comp_messege = categoryList.get(i).getcategory();
//                                complaint_stats.setText(comp_messege);
//                            }
//                        }
//                    } else {
//                        dialog.dismiss();
//                        // Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//
//    }

    private void getCloseComplaintComt() {
        getClosedComplaintslist();
      /*  Intent dataIntent1 = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(CLOSEDCOMPHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.CLOSEDCOMP.ordinal());
        startService(dataIntent1);*/
    }

    private void getClosedComplaintslist() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Please Wait..");
        /*operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        mainURL = operatorCode.getop_url();
        String uri=mainURL+"get_closed_comp_cat.php";*/
        String getcategorylist = BaseUrl.getBaseurl() + "Employee/complaint_issues_info";
       /* StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ClosedcompData closedcompData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<ClosedcompData>() {}.getType());
                                listclosedcompData.add(closedcompData);
                            }
                            SpinnerClosedCompAdapter adapter=new SpinnerClosedCompAdapter(UpdateComplaintActivity.this,listclosedcompData);
                            sp_closed_complaints.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


        }
    });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getcategorylist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(UpdateComplaintActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("response");
                    if (message.equalsIgnoreCase("Success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ClosedcompData closedcompData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<ClosedcompData>() {
                                }.getType());
                                listclosedcompData.add(closedcompData);
                            }
                            String[] listcompaints=new String[listclosedcompData.size()];
                            for(int i =0;i<listcompaints.length;i++){
                                listcompaints[i]=listclosedcompData.get(i).getSubCatName();
                            }

                            ArrayAdapter arrayAdapter=new ArrayAdapter(UpdateComplaintActivity.this,R.layout.spinner_text,listcompaints);
                            arrayAdapter.setDropDownViewResource(R.layout.spinner_complaint);
                            sp_closed_complaints.setAdapter(arrayAdapter);
                            // SpinnerClosedCompAdapter adapter = new SpinnerClosedCompAdapter(UpdateComplaintActivity.this, listclosedcompData);

                        }
                    } else {
                        dialog.dismiss();
                        // Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                // Toast.makeText(UpdateComplaintActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }

            ;
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id", getempID);
                params.put("comp_id",complaint_id);
                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /* private Handler CLOSEDCOMPHandler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             Bundle   bundle = msg.getData();
             String response = bundle.getString("data");
             try{
                 JSONObject responseObj = new JSONObject(response);
                 String message = responseObj.getString("message");
                 if (message.equalsIgnoreCase("success")) {
                     Iterator<String> keys = responseObj.keys();
                     while (keys.hasNext()) {
                         String key = keys.next();
                         if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                             ClosedcompData closedcompData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<ClosedcompData>() {}.getType());
                             listclosedcompData.add(closedcompData);
                         }
                         SpinnerClosedCompAdapter adapter=new SpinnerClosedCompAdapter(UpdateComplaintActivity.this,listclosedcompData);
                         sp_closed_complaints.setAdapter(adapter);
                     }
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }

         }
     };*/
    private void getInventoryList() {
        getInventoryListinVolley();
    /*    Intent dataIntent1 = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(InventoryHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.EMPLOYEE_INVENTORY.ordinal());
        dataIntent1.putExtra("jsonObject", getempID);
        startService(dataIntent1);*/
    }

    private void getInventoryListinVolley() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
 //       uri = BaseUrl.getBaseurl() + "Employee/emp_inv_list_items";
        uri = BaseUrl.getBaseurl() + "Employee/emp_inv_lists";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    dialog.dismiss();
                    //Toast.makeText(InventoryListActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String response12 = jsonObject.getString("response");
                    InventoryData data1=new InventoryData();
                    data1.setInvName("Select");
                    data1.setId("0");
                    data1.setInvGroup("0");
                   inventoryDataList.add(data1);
                   if (response12.equalsIgnoreCase("Success")) {
//                       inventoryDataList.clear();
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<InventoryData>() {
                                }.getType());
                                inventoryDataList.add(inventoryData);
                                invgroup = inventoryData.getInvGroup();
                            }
                            String[] inventorydata= new String[inventoryDataList.size()];
                            for(int i=0;i<inventorydata.length;i++){
                                inventorydata[i]=inventoryDataList.get(i).getInvName();
                            }

                            ArrayAdapter arrayAdapter=new ArrayAdapter(UpdateComplaintActivity.this,R.layout.spinner_text,inventorydata);
                            arrayAdapter.setDropDownViewResource(R.layout.spinner_complaint);
                            select_inventoryalert.setAdapter(arrayAdapter);
//                            arrayAdapter.notifyDataSetChanged();


//                           select_inventoryalert.setAdapter(null);

//                            arrayAdapter.notifyDataSetChanged();



                            //                            ComplaintInventoryListAdapter adapter = new ComplaintInventoryListAdapter(UpdateComplaintActivity.this, inventoryDataList);
//                            select_inventory.setAdapter(adapter);
//                            select_inventory1.setAdapter(adapter);
//                            select_inventory2.setAdapter(adapter);


                            select_inventoryalert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    getselectinventory1 = inventoryDataList.get(position).getInvId();
                                    getselectinventory1name = inventoryDataList.get(position).getInvName();
                                    invgroup123 = inventoryDataList.get(position).getInvGroup();
                                    avl_quantity = inventoryDataList.get(position).getAvlqty();
//                                    Toast.makeText(UpdateComplaintActivity.this, ""+invgroup123, Toast.LENGTH_SHORT).show();
                                    serialnumapiintegration(getselectinventory1);
                                    if (invgroup123.equals("77")) {
//                                     Quality1_alert.setVisibility(View.VISIBLE);
//                                    Quality2_alert.setVisibility(View.VISIBLE);
//                                    li_metersused.setVisibility(View.VISIBLE);
                                    linearLayout1.setVisibility(View.VISIBLE);
                                 linearLayout2.setVisibility(View.GONE);

                                    } else {
//                                       Quality1_alert.setVisibility(View.GONE);
//                                      Quality2_alert.setVisibility(View.GONE);
//                                      li_metersused.setVisibility(View.GONE);
                                     linearLayout1.setVisibility(View.GONE);
                                       linearLayout2.setVisibility(View.VISIBLE);

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                                
                            });
                            select_inventory1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    getselectinventory2 = inventoryDataList.get(position).getInvId();
                                    getselectinventory2name = inventoryDataList.get(position).getInvName();
                                    invgroup123 = inventoryDataList.get(position).getInvGroup();

                                    if (invgroup123.equals("77")) {

//                                        li_metersused.setVisibility(View.VISIBLE);
//                                        li_reading.setVisibility(View.VISIBLE);
                                    } else {
//                                        li_metersused.setVisibility(View.GONE);
//                                        li_reading.setVisibility(View.GONE);

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            select_inventory2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    getselectinventory3 = inventoryDataList.get(position).getInvId();
                                    getselectinventory1name = inventoryDataList.get(position).getInvName();
                                    invgroup123 = inventoryDataList.get(position).getInvGroup();
                                    if (invgroup123.equals("77")) {
                                        li_metersused.setVisibility(View.VISIBLE);
                                        li_reading.setVisibility(View.VISIBLE);
                                    } else {
                                        li_metersused.setVisibility(View.INVISIBLE);
                                        li_reading.setVisibility(View.INVISIBLE);

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    } else {
                        dialog.dismiss();
                        // Toast.makeText(UpdateComplaintActivity.this, ""+response12, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id", getempID);
                params.put("comp_id",complaint_id);
                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       /* operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"emp_inventory.php?emp_id="+objectempcode;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {

                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<InventoryData>() {}.getType());
                                inventoryDataList.add(inventoryData);
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                            ComplaintInventoryListAdapter adapter=new ComplaintInventoryListAdapter(UpdateComplaintActivity.this,inventoryDataList);
                            select_inventory.setAdapter(adapter);
                            select_inventory1.setAdapter(adapter);
                            select_inventory2.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


        }
    });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

    }

    /*private Handler InventoryHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {

                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            InventoryData inventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<InventoryData>() {}.getType());
                            inventoryDataList.add(inventoryData);
                        }
                        ComplaintInventoryListAdapter adapter=new ComplaintInventoryListAdapter(UpdateComplaintActivity.this,inventoryDataList);
                        select_inventory.setAdapter(adapter);
                        select_inventory1.setAdapter(adapter);
                        select_inventory2.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/
   /* private Handler COMPLAINTS_CATEGORYHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    categoryList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            CategoryListData categoryListData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<CategoryListData>() {}.getType());
                            categoryList.add(categoryListData);
                        }
                    }
                    for(int i=0; i<categoryList.size();i++){
                        String comp_id=categoryList.get(i).getid();
                        if(comp_id.equalsIgnoreCase(getcomp_cat)){
                            String comp_messege=categoryList.get(i).getcategory();
                            complaint_stats.setText(comp_messege);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };*/

    public void captureImage(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            100);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supportedImageprev", Toast.LENGTH_LONG).show();
        }
    }

    public void captureImage1(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile1();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            101);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    public void captureImage2(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile2();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            102);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    public void captureImage3(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile3();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            103);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    public void captureImage4(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile4();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            104);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    public void captureImage5(View view) {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile5();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            105);
                }
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }



    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picturePath = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picturePath));
        return image;
    }
    private File createImageFile1() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picture1Path = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picture1Path));
        return image;
    }
    private File createImageFile2() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picture2Path = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picture2Path));
        return image;
    }
    private File createImageFile3() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picture3Path = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picture3Path));
        return image;
    }
    private File createImageFile4() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picture4Path = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picture4Path));
        return image;
    }
    private File createImageFile5() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        picture5Path = image.getAbsolutePath();
        fileUri = Uri.fromFile(new File(picture5Path));
        return image;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 100 && resultCode == RESULT_OK) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap1 = BitmapFactory.decodeFile(picturePath,
                        options);
                  /*ExifInterface exif = new ExifInterface(picturePath);
                int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                int rotationInDegrees = exifToDegrees(rotation);
               Matrix matrix = new Matrix();
             if (rotation != 0) {
                 matrix.postRotate(rotationInDegrees);
               }
              bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, 500, 600, matrix, true);*/

                bitmap1=getResizedBitmap(bitmap1, 400);

                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap1);
                canvas.drawBitmap(bitmap1, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap1);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name = getempID + "_" + System.currentTimeMillis() + count + ".png";
                Log.d("closed_img_name",closed_img_name);
                filearraylist.add(new FileModel(ba1,closed_img_name));
                FAB.setVisibility(View.VISIBLE);
            } else if (requestCode == 101 && resultCode == RESULT_OK) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap2 = BitmapFactory.decodeFile(picture1Path,
                        options);
                bitmap2=getResizedBitmap(bitmap2, 400);
                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap2);
                canvas.drawBitmap(bitmap2, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                Imageprev1.setVisibility(View.VISIBLE);
                Imageprev1.setImageBitmap(bitmap2);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name1 = getempID + "_" + System.currentTimeMillis() + count + ".png";
                filearraylist.add(new FileModel(ba2,closed_img_name1));

            } else if (requestCode == 102 && resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap3 = BitmapFactory.decodeFile(picture2Path,
                        options);
                bitmap3=getResizedBitmap(bitmap3, 400);
                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap3);
                canvas.drawBitmap(bitmap3, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                Imageprev2.setVisibility(View.VISIBLE);
                Imageprev2.setImageBitmap(bitmap3);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba3 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name2 = getempID + "_" + System.currentTimeMillis() + count + ".png";
                filearraylist.add(new FileModel(ba3,closed_img_name2));
            }else if (requestCode == 103 && resultCode == RESULT_OK) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap4 = BitmapFactory.decodeFile(picture3Path,
                        options);
                  /*ExifInterface exif = new ExifInterface(picturePath);
                int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                int rotationInDegrees = exifToDegrees(rotation);
               Matrix matrix = new Matrix();
             if (rotation != 0) {
                 matrix.postRotate(rotationInDegrees);
               }
              bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, 500, 600, matrix, true);*/

                bitmap4=getResizedBitmap(bitmap4, 400);

                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap4);
                canvas.drawBitmap(bitmap4, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                imageView3.setVisibility(View.VISIBLE);
                imageView3.setImageBitmap(bitmap4);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap4.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba4 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name3 = getempID + "_" + System.currentTimeMillis() + count1 + ".png";
                Log.d("closed_img_name",closed_img_name3);
                filearraylist.add(new FileModel(ba4,closed_img_name3));
                FAB_hardware.setVisibility(View.VISIBLE);

            }else if (requestCode == 104 && resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap5 = BitmapFactory.decodeFile(picture4Path,
                        options);
                bitmap5=getResizedBitmap(bitmap5, 400);
                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap5);
                canvas.drawBitmap(bitmap5, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                imageView4.setVisibility(View.VISIBLE);
                imageView4.setImageBitmap(bitmap5);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap5.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba5 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name4 = getempID + "_" + System.currentTimeMillis() + count1 + ".png";
                filearraylist.add(new FileModel(ba5,closed_img_name4));
            }else if (requestCode == 105 && resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap6 = BitmapFactory.decodeFile(picture5Path,
                        options);
                bitmap6=getResizedBitmap(bitmap6, 400);
                double latitude = 0.0, longitude = 0.0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude == 0.0 && longitude == 0.0) {
                        gps = new GPSTracker(UpdateComplaintActivity.this);
                    } else {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                } else {
                    gps.showSettingsAlert();
                }
                gio_loc = latitude + "," + longitude;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap6);
                canvas.drawBitmap(bitmap6, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                imageView5.setVisibility(View.VISIBLE);
                imageView5.setImageBitmap(bitmap6);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap6.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba6 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                closed_img_name5 = getempID + "_" + System.currentTimeMillis() + count1 + ".png";
                filearraylist.add(new FileModel(ba6,closed_img_name5));
                FAB_hardware.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation ==   ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }


        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void upload(String picturePath, String ImageName,String status) {





        uploadToServer(UpdateComplaintActivity.this, picturePath, ImageName,status);



        //------------------------last one----
//        Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(picturePath);
//        //Bitmap bitmap = ImageUtils.resizeBitmap(picturePath);
//        int nh = (int) ( bitmap.getHeight() * (1024.0 / bitmap.getWidth()) );
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        scaled.compress(Bitmap.CompressFormat.PNG, 0, bao);
//        byte[] ba = bao.toByteArray();
//        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        //--------------------
       /* BitmapFactory.Options options = new BitmapFactory.Options();
        //bitmap= BitmapFactory.decodeFile(picturePath, options);
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        // bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        // Bitmap.createScaledBitmap(picturePath, 100, 100, true);
        byte[] ba=byteArrayOutputStream.toByteArray();
        ba1=android.util.Base64.encodeToString(ba,Base64.DEFAULT);*/
        /*BitmapFactory.Options options = new BitmapFactory.Options();
        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 20;
        bitmap= BitmapFactory.decodeFile(picturePath,
                options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);*/


        // uploadToServer(UpdateComplaintActivity.this,picturePath,ImageName);
        //new uploadToServer(UpdateComplaintActivity.this,picturePath,ImageName).execute();
    }

    private void uploadToServer(final UpdateComplaintActivity updateComplaintActivity, String picturePath, final String imageName,String status) {
//        Toast.makeText(updateComplaintActivity, ""+ba1+ba2+ba3+ba4+ba5+ba6, Toast.LENGTH_SHORT).show();
        for(int i=0;i<filearraylist.size();i++) {
            this.name = imageName;
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please Wait..");
            ImageUploadURL = BaseUrl.getBaseurl() + "uploads/upload.php";
            int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ImageUploadURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("true")) {
//                            Toast.makeText(UpdateComplaintActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        /*if (picture1Path.length() != 0) {
                            if (status.equals("one")) {
                                picture1Path = "";
                                upload(picture2Path, closed_img_name2, "two");
                            } else if (status.equals("two")) {
                                picture2Path = "";
                                closedImageName = closed_img_name + "," + closed_img_name1 + "," + closed_img_name2;
                                ComplaintEditlist();
                            }else {
                                upload(picture2Path, closed_img_name2, "one");
                            }
                        }*/
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(updateComplaintActivity, "Image uploaded", Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    dialog.dismiss();
//                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(updateComplaintActivity, "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("base64",ba1);
                    params.put("base64", filearraylist.get(finalI).getPath());
                    params.put("ImageName", filearraylist.get(finalI).getName());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        ComplaintEditlist();
    }

    /*  public class uploadToServer extends AsyncTask<Void, Void, String> {
          Context context;
          String name;
          private ProgressDialog pd = new ProgressDialog(UpdateComplaintActivity.this);

          public uploadToServer(Context con,String path,String ImageName) {
              this.context=con;
              this.name=ImageName;
          }
          protected void onPreExecute() {
              super.onPreExecute();
              pd.setMessage("Wait image uploading!");
              pd.setCancelable(false);
              pd.show();
          }
          @Override
          protected String doInBackground(Void... params) {
              ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
              nameValuePairs.add(new BasicNameValuePair("base64", ba1));
              nameValuePairs.add(new BasicNameValuePair("ImageName", name));
              try {
                  ComplaintEditlist();
                  HttpClient httpclient = new DefaultHttpClient();
                  HttpPost httppost = new HttpPost(ImageUploadURL);
                  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                  HttpResponse response = httpclient.execute(httppost);
                  String st = EntityUtils.toString(response.getEntity());
                  Log.e("Success","Upload image"+name);
              } catch (Exception e) {
                  e.printStackTrace();
                  resetData();
                  Toast.makeText(UpdateComplaintActivity.this,"Upload Fail try again",Toast.LENGTH_LONG).show();
              }
              return "Success";
          }
          protected void onPostExecute(String result) {
              super.onPostExecute(result);
              pd.hide();
              pd.dismiss();
          }

      }*/

    public void submit(View view) {
//        String remarks=Add_complaints.getText().toString();
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = UpdateComplaintActivity.this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        getused_qty = used_qty.getText().toString();
        getused_qty1 = used_qty1.getText().toString();
        getused_qty2 = used_qty2.getText().toString();
        remarks = Add_complaints.getText().toString();
//        if(remarks.isEmpty()){
//            Add_complaints.setError("please enter remarks");
//        }
        quantity1 = Quality1.getText().toString();
        quantity2 = Quality2.getText().toString();
        setselectused_quy = getused_qty;
        serialno = et_serialno.getText().toString();
        setselectissue = sp_closed_complaints.getSelectedItem().toString();
        inventoryID = select_inventory.getSelectedItemPosition();
//        Toast.makeText(this,""+inventoryID, Toast.LENGTH_SHORT).show();
        batteryperct = (level * 100) / (float) scale;
        if (check_used_inventory.isChecked()) {
            x = 1;
        } else {
            x = 0;
        }
//         Toast.makeText(getApplicationContext(),""+x,Toast.LENGTH_SHORT).show();
        double latitude = 0.0, longitude = 0.0;
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (latitude == 0.0 && longitude == 0.0) {
                gps = new GPSTracker(UpdateComplaintActivity.this);
            } else {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }
        } else {
            gps.showSettingsAlert();
        }
        gio_loc = latitude + "," + longitude;
        getused_qty = used_qty.getText().toString();
        getused_qty1 = used_qty1.getText().toString();
        getused_qty2 = used_qty2.getText().toString();

        if (remarks.isEmpty()) {
            Add_complaints.setError("please enter remarks");
        } else if (remarks.length() < 25) {
            Add_complaints.setError("Remarks Should be minimum 25 letters");
        } else if (comp_status.equalsIgnoreCase("Qualitycheck")) {
            if (ba1 ==null){
                Toast.makeText(this, "Please upload images", Toast.LENGTH_SHORT).show();
            }else {
                remarksvalidation();
                return;
            }

        }else if (getcomplaint.equalsIgnoreCase("Resolved")){
            if (y==1) {
                if (ba4 == null) {
                    Toast.makeText(this, "Please upload images", Toast.LENGTH_SHORT).show();
                } else {
                    remarksvalidation();
                    return;
                }
            } else {
                remarksvalidation();
                return;
            }
        }else {
            remarksvalidation();
        }
    }
        private void remarksvalidation() {
        if (picturePath.length() == 0) {
            ComplaintEditlist();
           /* Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
            Messenger dataMessenger = new Messenger(complaintsHandler);
            dataIntent.putExtra("MESSENGER", dataMessenger);
            dataIntent.putExtra("type", DataLoader.DataType.COMPLAINT_EDIT.ordinal());
            dataIntent.putExtra("complaint_id", complaint_id);
            dataIntent.putExtra("comp_status", comp_status);
            dataIntent.putExtra("emp_id", getempID);
            dataIntent.putExtra("remarks", remarks);
            dataIntent.putExtra("closed_img", "");
            dataIntent.putExtra("getclosedcompID", getclosedcompID);
            dataIntent.putExtra("gio_loc", gio_loc);
            dataIntent.putExtra("used_qty",setselectused_quy);
            dataIntent.putExtra("inventoryID",inventoryID);
            startService(dataIntent);*/

        } else {
            //ComplaintEditlist();

//            for(int i=0;i<filearraylist.size();i++){

            uploadToServer(UpdateComplaintActivity.this, picturePath, closed_img_name,"zero");

            //upload(filearraylist.get(i), closed_img_name,"zero");
//            }
//            ComplaintEditlist();
            //upload(picturePath, closed_img_name,"zero");


            /*Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
            Messenger dataMessenger = new Messenger(complaintsHandler);
            dataIntent.putExtra("MESSENGER", dataMessenger);
            dataIntent.putExtra("type", DataLoader.DataType.COMPLAINT_EDIT.ordinal());
            dataIntent.putExtra("complaint_id", complaint_id);
            dataIntent.putExtra("comp_status", comp_status);
            dataIntent.putExtra("emp_id", getempID);
            dataIntent.putExtra("remarks", remarks);
            dataIntent.putExtra("closed_img", closedImageName);
            dataIntent.putExtra("getclosedcompID",getclosedcompID);
            dataIntent.putExtra("gio_loc", gio_loc);
            dataIntent.putExtra("used_qty",setselectused_quy);
            dataIntent.putExtra("inventoryID",inventoryID);
            startService(dataIntent);*/
        }
    }


    private void ComplaintEditlist() {
        Toast.makeText(UpdateComplaintActivity.this, "update api 1", Toast.LENGTH_SHORT).show();
        String uri = BaseUrl.getBaseurl() + "employee/emp_gps_loc";
        JSONArray array1 = new JSONArray();
        for (int i = 0; i < modelList.size(); i++) {
            JSONObject object1 = new JSONObject();
            try {
               object1.accumulate("inventoryname", modelList.get(i).getInvName());
                object1.accumulate("inventoryid",modelList.get(i).getInvid());
                object1.accumulate("used_qty", modelList.get(i).getUsdQty());
                object1.accumulate("reading1", modelList.get(i).getSrtrdng());
                object1.accumulate("reading2", modelList.get(i).getEndrdng());
                object1.accumulate("serialno", modelList.get(i).getSrlnum());
                array1.put(object1);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.accumulate("inventoryid", array1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d("CHINNARAYUDU", "rayududu");
//        Log.d("CHINNARAYUDU", array1.toString());
//        Log.d("CHINNARAYUDU", jsonObject.toString());

        JSONObject map = new JSONObject();
        Toast.makeText(UpdateComplaintActivity.this, "update api 2", Toast.LENGTH_SHORT).show();
        try {
            map.put("emp_id",getempID);
            map.put("comp_id",complaint_id);
           map.put("comp_status",comp_status);
            map.put("closed_img",closed_img_name+","+closed_img_name1+","+closed_img_name2);
            map.put("hardware_removed_img",closed_img_name3+","+closed_img_name4+","+closed_img_name5);
           map.put("gps_lang",String.valueOf(longitude));
            map.put("remarks",remarks);
            map.put("gps_lat",String.valueOf(latitude));
            // params.put("inventoryid",String.valueOf(inventoryID));
//                params.put("inventoryid",getselectinventory1);
           map.put("UsedInventoryid",array1);
//           map.put("used_qty",setselectused_quy);
//           map.put("serialno",serialno);
//            map.put("reading1",quantity1);
//           map.put("reading2",quantity2);
          map.put("issue_found",setselectissue);
            map.put("action_taken",String.valueOf(x));
           map.put("is_hardware_removed",String.valueOf(y));
            map.put("batterypercent",String.valueOf(batteryperct));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        final ProgressDialog dialog = new ProgressDialog(this);
//       dialog.show();
//      dialog.setMessage("Please Wait");
//   String uri = "https://enterprise.apsfl.in/development/webservices/Employee/emp_gps_loc";
        Log.d("PAYLOADS",map.toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        Toast.makeText(UpdateComplaintActivity.this, "update api 3", Toast.LENGTH_SHORT).show();
        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, uri, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                    dialog.dismiss();
                            String message = response.getString("msg");
//                            Toast.makeText(UpdateComplaintActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            Toast.makeText(UpdateComplaintActivity.this, "update api 4", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateComplaintActivity.this, EMPHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                    } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(UpdateComplaintActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(UpdateComplaintActivity.this, "update api 5", Toast.LENGTH_SHORT).show();
            }


        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
//  headers.put("Content-Type","application/json");
                headers.put("x-access-token", SaveAppData.getOperatorLoginData().getAccessToken());
                return headers;
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("emp_id",getempID);
//                params.put("comp_id",complaint_id);
//                params.put("comp_status",comp_status);
//                params.put("closed_img",closed_img_name+","+closed_img_name1+","+closed_img_name2);
//                params.put("hardware_removed_img",closed_img_name3+","+closed_img_name4+","+closed_img_name5);
//                params.put("gps_lang",String.valueOf(longitude));
//                params.put("remarks",remarks);
//                params.put("gps_lat",String.valueOf(latitude));
//                // params.put("inventoryid",String.valueOf(inventoryID));
////                params.put("inventoryid",getselectinventory1);
//                params.put("inventoryid", String.valueOf(array1));
//                params.put("used_qty",setselectused_quy);
//                params.put("serialno",serialno);
//                params.put("reading1",quantity1);
//                params.put("reading2",quantity2);
//                params.put("issue_found",setselectissue);
//                params.put("action_taken",String.valueOf(x));
//                params.put("is_hardware_removed",String.valueOf(y));
//                params.put("batterypercent",String.valueOf(batteryperct));
//                params.put("comp_closed_cat", getclosedcompID);
//                return params;
//            }
//        };


//                params.put("used_qty",setselectused_quy);
//                params.put("reading1",quantity1);
//                params.put("reading2",quantity2);
//                params.put("serialno",serialno);

//
//        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//
//            public void checkClientTrusted(X509Certificate[] certs, String authType) {
//            }
//
//            public void checkServerTrusted(X509Certificate[] certs, String authType) {
//            }
//        }};
//        SSLContext sc = null;
//        try {
//            sc = SSLContext.getInstance("SSL");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        try {
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        // Create all-trusting host name verifier
//        HostnameVerifier allHostsValid = new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        };
//        // Install the all-trusting host verifier
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
       /* JSONObject object1 = new JSONObject();
        JSONArray array1 = new JSONArray();
        if (modelList.size()!=0){
           for (int i = 0 ; i < modelList.size() ;i++){
               try {
                   object1.put("Inventory name",modelList.get(i).getInvName());
                   object1.put("Used Quantity",modelList.get(i).getUsdQty());
                   object1.put("Start reading",modelList.get(i).getSrtrdng());
                   object1.put("End reading",modelList.get(i).getEndrdng());
                   object1.put("Serial num",modelList.get(i).getSrlnum());
                   array1.put(object1);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
           Log.d("CHINNARAYUDU",array1.toString());
           Log.d("CHINNARAYUDU",array1.toString());

        }*/

       /* final ProgressDialog dialog=new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Please Wait");
        uri=mainURL+"complaints_edit.php?complaint_id="+complaint_id+"&comp_status="+comp_status+"&emp_id="+getempID+"&remarks="+remarks+"&comp_closed_cat="+getclosedcompID+"&closed_img="+name+""+"&gio_loc="+gio_loc+"&inventoryid="+inventoryID+"&used_qty="+setselectused_quy+"";
        Log.d("Complaintedit",uri);
        Log.e("Complaintedit",uri);
        System.out.println(uri);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        final Dialog dialog = new Dialog(UpdateComplaintActivity.this);
                        dialog.setContentView(R.layout.dialogcomplaint);
                        dialog.setCancelable(false);
                        Button button = (Button) dialog.findViewById(R.id.Redirecttodashboard);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        dialog.show();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(UpdateComplaintActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


        }
    });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/



    /* private Handler complaintsHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             Bundle bundle = msg.getData();
             String response = bundle.getString("data");
             try {
                 JSONObject responseObj = new JSONObject(response);
                 String message = responseObj.getString("message");
                 if (message.equalsIgnoreCase("success")) {
                     final Dialog dialog = new Dialog(UpdateComplaintActivity.this);
                     dialog.setContentView(R.layout.dialogcomplaint);
                     dialog.setCancelable(false);
                     Button button = (Button) dialog.findViewById(R.id.Redirecttodashboard);
                     button.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             dialog.cancel();
                             finish();
                         }
                     });
                     dialog.show();
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }

         }
     };*/
        };
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jobReq);
    }
    private void resetData() {
        imageView.setImageResource(0);
        imageView.setVisibility(View.GONE);
        Add_complaints.setText("");
    }

    private void stopTimer() {
        if (mTimer1 != null) {
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    private void startTimer() {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //TODO
                        latitude = 0.0;
                        longitude = 0.0;
                        if (gps.canGetLocation()) {
                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            if (latitude == 0.0 && longitude == 0.0) {
                                gps = new GPSTracker(UpdateComplaintActivity.this);
                            } else {
                                Log.e("GetLocation", "latitude :- " + latitude + "\n longitude :- " + longitude);
//                           EmployeeGpsLatLog();
                               /* Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
                                Messenger dataMessenger = new Messenger(hEMPGPSLOC);
                                dataIntent.putExtra("MESSENGER", dataMessenger);
                                dataIntent.putExtra("type", DataLoader.DataType.EMPGPSLOC.ordinal());
                                dataIntent.putExtra("gps_lang",""+longitude);
                                dataIntent.putExtra("gps_lat",""+latitude);
                                dataIntent.putExtra("emp_id",getempID);
                                startService(dataIntent);*/
                            }
                        }
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 30000);
    }

    private void EmployeeGpsLatLog() {
//        final ProgressDialog dialog=new ProgressDialog(this);
//        dialog.setMessage("Please Wait..");
//        dialog.show();
        String uri = BaseUrl.getBaseurl() + "Employee/emp_gps_loc";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(UpdateComplaintActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                dialog.dismiss();
                Toast.makeText(UpdateComplaintActivity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
//                params.put("comp_id",complaint_id);
//                params.put("comp_status",comp_status);
//                params.put("closed_img","");
//                params.put("gps_lang",String.valueOf(longitude));
//               params.put("remarks",remarks);
//                params.put("gps_lat",String.valueOf(latitude));
//              params.put("inventoryid","");
                //params.put("emp_statuscode",MSRCApplication.EMPStatusCode);

                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        /*operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"emp_gps_loc.php?emp_id="+objectempcode+"&gps_lat="+latitude+"&gps_lang="+longitude+"&emp_statuscode="+ MSRCApplication.EMPStatusCode+"";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    //String message=jsonObject.getString("");
                    //String response = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


        }
    });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }
    /*private Handler hEMPGPSLOC=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
        }
    };*/

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

}