package com.Tejaysdr.msrc.activitys.emp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.Tejaysdr.msrc.ImagepickerActivity;
import com.Tejaysdr.msrc.activitys.RejectActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.BuildConfig;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.MSRCApplication;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.activitys.MyPeriodicWork;
import com.Tejaysdr.msrc.activitys.ProfileActivity;
import com.Tejaysdr.msrc.activitys.Utils_location;
import com.Tejaysdr.msrc.activitys.admin.AdminHomeActivity;
import com.Tejaysdr.msrc.activitys.stockist.StockistHomeActivity;
import com.Tejaysdr.msrc.backendServices.ConnectivityReceiver;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.backendServices.SendGPSServicesLocation;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class EMPHomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    private static final String TAG = "";
    TextView empname, empmobile, empemail, empdesignation, empusername, tv_marquee, tv_logintime, cart_badge;
    private GPSTracker gps;
    public static Timer mTimer1;
    private TimerTask mTt1;
    boolean isConnected;
    Gploc gpcLoc;
    public static EMPHomeActivity instance;
    private Handler mTimerHandler = new Handler();
    String getempID;
    MenuItem menu_myAccount, menu_logout, menu_version;
    Menu menu;
    SQLiteDatabase database;
    double latitude = 0.0, longitude = 0.0;
    ImageView notification;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String token;
    AlertDialog dialog;
    //    RecyclerView rvhorizontalslider;
    ArrayList<BannerModel> bannerModels = new ArrayList<>();
    private Handler handler;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private PeriodicWorkRequest mPeriodicWorkRequest;
    public static final int REQUEST_CHECK_SETTINGS = 20001;
    ArrayList<Gploc> gpcLocArrayList = new ArrayList<>();
    Gploc gploc;
    private int count1, position;
    BannerAdapter bannerAdapter;
    final int duration = 10;
    final int pixelsToMove = 45;
    LinearLayout li_materials, lv_complaints;
    private boolean callphone;
    private String roleid = null;
    private MenuItem menu_complaint;
    private MenuItem menu_helpandsupport;
    private MenuItem menu_payhistory;
    private String android_id1;
    int recordsupdated = 0;
    int count;
    String gps_lati, gps_longit, gps_datecapture, gps_address, gps_id;
    private LocationRequest mLocationRequest;
    private boolean cameraPermission, readgpsfile, readExternalFile;
    FusedLocationProviderClient mFusedLocationClient;
    private static final long UPDATE_INTERVAL = 60000; // Every 60 seconds.
    private static final long FASTEST_UPDATE_INTERVAL = 30000; // Every 30 seconds
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 2; // Every 5 minutes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emphome);

        notification = findViewById(R.id.notification);
        cart_badge = findViewById(R.id.cart_badge);
        lv_complaints = findViewById(R.id.lv_complaints);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

        getempID = operatorCode.getRole_id();
        empname = (TextView) findViewById(R.id.empname);
        empmobile = findViewById(R.id.empmobile);
        empemail = findViewById(R.id.empemail);
        empdesignation = findViewById(R.id.empdesignation);
        empusername = findViewById(R.id.empusername);
        tv_marquee = findViewById(R.id.tv_marquee);
        tv_logintime = findViewById(R.id.tv_logintime);
        li_materials = findViewById(R.id.li_materials);
        tv_logintime = findViewById(R.id.tv_logintime);
        handler = new Handler();
//     rvhorizontalslider = findViewById(R.id.rvhorizontalslider);
        String getempname = operatorCode.getFullname();
        empmobile.setText(operatorCode.getMobile());
        empemail.setText(operatorCode.getEmail());
        empname.setText(getempname);
        // int cart=Integer.parseInt(operatorCode.getNoti_status());
        empusername.setText(operatorCode.getLogin_username());
        empdesignation.setText(operatorCode.getDesignation());
        String marqueedata = SaveAppData.getOperatorLoginData().getScroll_msg();
        String supportno = SaveAppData.getOperatorLoginData().getSupport_no();
        String imgstatus = SaveAppData.getOperatorLoginData().getImgstatus();
        //Toast.makeText(EMPHomeActivity.this, ""+supportno, Toast.LENGTH_SHORT).show();
        MSRCApplication.EMPStatusCode = "1";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  tv_marquee.setText(Html.fromHtml("<font color='#F44336'>" + marqueedata + "</font>" + " " + "  " + " " + " " + "<font color='#F44336'>" + marqueedata + "</font>"));
        tv_marquee.setText(Html.fromHtml("<font color='#F44336'>" + marqueedata + "</font>"));
        tv_marquee.setSelected(true);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = df.format(c);
        tv_logintime.setText(formattedDate);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        android_id1 = Settings.Secure.getString(EMPHomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        Toast.makeText(this, ""+android_id1, Toast.LENGTH_SHORT).show();
        //For Battery information we are using this
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        checkstatus();
        instance = this;
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = EMPHomeActivity.this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float) scale;

//        Toast.makeText(EMPHomeActivity.this, "Battery Percentage " + batteryPct, Toast.LENGTH_SHORT).show();
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EMPHomeActivity.this, NotificationActivity.class);
                startActivity(intent);
//                Toast.makeText(EMPHomeActivity.this,"coming soon",Toast.LENGTH_SHORT).show();
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        //this for horizontal recyclerview
        final Handler mHandler = new Handler(Looper.getMainLooper());
//        final Runnable SCROLLING_RUNNABLE = new Runnable() {
//
//            @Override
//            public void run() {
//                rvhorizontalslider.smoothScrollBy(pixelsToMove, 0);
//                mHandler.postDelayed(this, duration);
//            }
//        };
//        Runnable runnable = new Runnable() {
//            public void run() {
//             /*   if (myCustomPagerAdapter.getCount() == page) {
//                    page = 0;
//                } else {
//                    page++;
//                }
//                db_viewpager.setCurrentItem(page, true);
//                handler.postDelayed(this, delay);*/
//            }
//        };
//        bannerModels.add(new BannerModel(1, R.drawable.frmsimg));
//        bannerModels.add(new BannerModel(2, R.drawable.frmsimg));
//        bannerModels.add(new BannerModel(3, R.drawable.frmsimg));
//        bannerAdapter = new BannerAdapter(EMPHomeActivity.this, bannerModels);
//        //Adding the Adapter to horizontal recycler view
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EMPHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        rvhorizontalslider.setLayoutManager(linearLayoutManager);
//        rvhorizontalslider.setAdapter(bannerAdapter);
//        rvhorizontalslider.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                if (lastItem == linearLayoutManager.getItemCount() - 1) {
//                    handler.removeCallbacks(SCROLLING_RUNNABLE);
//                    Handler postHandler = new Handler();
//                    postHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            rvhorizontalslider.setAdapter(null);
//                            rvhorizontalslider.setAdapter(bannerAdapter);
//                            handler.postDelayed(SCROLLING_RUNNABLE, 2000);
//                        }
//                    }, 2000);
//                }
//            }
//        });
//        handler.postDelayed(SCROLLING_RUNNABLE, 2000);

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            // check if GPS enabled
            if (!isGPSEnabled(EMPHomeActivity.this)) {
                displayLocationSettingsRequest(EMPHomeActivity.this);
            } else {
                requestLocationUpdates(null);
                mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class, 2, TimeUnit.SECONDS)
                        .addTag("periodicWorkRequest")
                        .build();
                WorkManager.getInstance().enqueue(mPeriodicWorkRequest);
            }
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        // Toast.makeText(gps, ""+token, Toast.LENGTH_SHORT).show();

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
//        if(op)
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        menu_myAccount = menu.findItem(R.id.menu_myAccount);
        menu_logout = menu.findItem(R.id.menu_logout);
        menu_version = menu.findItem(R.id.tv_version);
        menu_version.setTitle("Version  " + com.Tejaysdr.msrc.BuildConfig.VERSION_NAME);
        menu_complaint = menu.findItem(R.id.menu_complaint);
        menu_helpandsupport = menu.findItem(R.id.menu_helpandsupport);
        menu_payhistory = menu.findItem(R.id.menu_payhistory);

        roleid = SaveAppData.getOperatorLoginData().getRole_id();
        if (roleid.equals("36")) {
            tv_logintime.setVisibility(View.VISIBLE);
            lv_complaints.setVisibility(View.GONE);
            li_materials.setVisibility(View.GONE);
            menu_complaint.setVisible(false);
            menu_helpandsupport.setVisible(false);
            menu_payhistory.setVisible(false);

        } else {
            tv_logintime.setVisibility(View.GONE);
            lv_complaints.setVisibility(View.VISIBLE);
            li_materials.setVisibility(View.VISIBLE);
            menu_complaint.setVisible(true);
            menu_helpandsupport.setVisible(true);
            menu_payhistory.setVisible(true);

        }

        gps = new GPSTracker(EMPHomeActivity.this);
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        }
//        startTimer();
        Intent intent = new Intent(this, SendGPSServicesLocation.class);
        startService(intent);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.action_home);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                item.setIcon(R.drawable.nav_home);
                                menu.findItem(R.id.action_quickpay).setIcon(R.drawable.nav_help_and_support);
                                menu.findItem(R.id.action_referandearn).setIcon(R.drawable.logout);
                                menu.findItem(R.id.action_customercare).setIcon(R.drawable.user);
                                break;
                            case R.id.action_quickpay:
                                item.setIcon(R.drawable.nav_help_and_support);
                                Intent intent1 = new Intent(Intent.ACTION_DIAL);

                                //intent1.setData(Uri.parse("tel:0891-5100108"));
                                intent1.setData(Uri.parse("tel:" + SaveAppData.getOperatorLoginData().getSupport_no()));
                                startActivity(intent1);
                               /* Intent intent=new Intent(EMPHomeActivity.this,TaskActivity1.class);
                                startActivity(intent);
                                finish();*/
                                menu.findItem(R.id.action_home).setIcon(R.drawable.nav_home);
                                menu.findItem(R.id.action_referandearn).setIcon(R.drawable.logout);
                                menu.findItem(R.id.action_customercare).setIcon(R.drawable.user);
                                break;
                            case R.id.action_customercare:
                                Intent intent2 = new Intent(EMPHomeActivity.this, ProfileActivity.class);
                                startActivity(intent2);
                                finish();
                                item.setIcon(R.drawable.user);
                                menu.findItem(R.id.action_home).setIcon(R.drawable.nav_home);
                                menu.findItem(R.id.action_quickpay).setIcon(R.drawable.nav_help_and_support);
                                menu.findItem(R.id.action_referandearn).setIcon(R.drawable.logout);
                                break;
                            case R.id.action_referandearn:
                                androidx.appcompat.app.AlertDialog paymentAlertDialog = new androidx.appcompat.app.AlertDialog.Builder(EMPHomeActivity.this).setTitle("Logout")
                                        .setMessage("Are you sure want to Logout ?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                logoutmethod();
                                               /* dialog.dismiss();
                                                SaveAppData.saveOperatorLoginData(null);
                                                //SaveAppData.saveCustomerToken(null);
                                                Intent i = new Intent(EMPHomeActivity.this, EMPLoginActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();*/
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
//                                resetAllViews();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    }
                });


        requestLocationUpdates(null);
        CallLocalSaving();
        isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
//            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            LOcationUpdateForJSonObject();
        }
//       if(SaveAppData.getOperatorLoginData().getImgstatus().equals("1")){
//           Intent intent5=new Intent(EMPHomeActivity.this,EMPHomeActivity.class);
//           startActivity(intent5);
//      }
//     else {
//           Intent intent5=new Intent(EMPHomeActivity.this,ImagepickerActivity.class);
//           startActivity(intent5);
//      }

//    }


    }

    private void alertDialogForAppointmentletter() {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.custom_usedinventory1, null);

        Button btn=view.findViewById(R.id.image_yes);
        Button preview1=view.findViewById(R.id.preview1);
        preview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),PreviewActivity.class);
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.8f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

       /* final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system

//                Toast.makeText(getApplicationContext(), "dialog dismissed automatically", Toast.LENGTH_SHORT).show();
            }
        }, 5000); // the timer will count 5 seconds....*/
        
    }

    private void checkstatus() {
        String baseurl = BaseUrl.getBaseurl() + "employee/single_emp_details";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("response");
                    if (msg.equals("Success")) {
                        String imgstatus2 = jsonObject.getString("emp_login_img_status");
//                        Toast.makeText(EMPHomeActivity.this,""+imgstatus2,Toast.LENGTH_SHORT).show();
                        if (imgstatus2.equals("1")) {
                        } else {
                            Intent intent6 = new Intent(EMPHomeActivity.this, ImagepickerActivity.class);
                            startActivity(intent6);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                progressDialog.dismiss();
//                Toast.makeText(ImagepickerActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(ImagepickerActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    /*private void bussinessInfo() {
        progressDialog.show();
        progressDialog.setMessage("Please wait...");

        String baseurl =BaseUrl.getBaseurl()+"Employee/business_info";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject  jsonObject=new JSONObject(response);
                String response12=jsonObject.getString("response");
                if (response12.equalsIgnoreCase("Successfull")) {
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EMPHomeActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }*/

    private void logoutmethod() {
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        //       String baseurl ="http://crm.nettlinx.com/webservices/Employee/emp_logout";

        //battery percentage
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float)scale;
//        Toast.makeText(this, "Battery Percentage "+batteryPct, Toast.LENGTH_SHORT).show();
        String baseurl =BaseUrl.getBaseurl()+"Employee/emp_logout";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                EMPHomeActivity.getInstance().removeLocationUpdates();
                SaveAppData.setusename("");
                SaveAppData.saveOperatorLoginData(null);
                SaveAppData.saveOperatorData(null);
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                //               Toast.makeText(EMPHomeActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                MSRCApplication.EMPStatusCode="0";
                Intent login = new Intent(EMPHomeActivity.this, EMPLoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EMPHomeActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("token",token);
                params.put("gps_lat",String.valueOf(latitude));
                params.put("gps_lang",String.valueOf(longitude));
                params.put("battery_package",String.valueOf(batteryPct));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void LOcationUpdateForJSonObject() {
        final ProgressDialog progressdialog = new ProgressDialog(EMPHomeActivity.this);
        progressdialog.show();
        progressdialog.setCancelable(false);
        jsonArray=new JSONArray();
        for(int i=0;i<gpcLocArrayList.size();i++){
            position=i;
            jsonObject=new JSONObject();
            try {
                jsonObject.accumulate("gps_lat", gpcLocArrayList.get(i).getGps_latitude());
                jsonObject.accumulate("gps_lang", gpcLocArrayList.get(i).getGps_longitude());
                jsonObject.accumulate("dateCreated", gpcLocArrayList.get(i).getDatecaptured());
                jsonObject.accumulate("Address", gpcLocArrayList.get(i).getId());
                jsonObject.accumulate("description", "offline");
                jsonArray.put(jsonObject);
                database.execSQL("UPDATE gps_loc SET status='Old' WHERE status='New' AND Id='" + gpcLocArrayList.get(i).getId() + "'");
                database.execSQL("DELETE from gps_loc  WHERE status='Old' AND Id='" + gpcLocArrayList.get(i).getId() + "'");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String url =BaseUrl.getBaseurl()+"employee/emp_gps_loc_new";
        JSONObject map = new JSONObject();
        try {
            map.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
            map.put("Locations",jsonArray);
            map.put("token",SaveAppData.getOperatorLoginData().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: " + jsonObject.toString());
                        try {
                            String message=jsonObject.getString("message");
                            String text=jsonObject.getString("text");
                            String token_expiry=jsonObject.getString("token_expiry");

                            if(token_expiry.equals("No")){
                                if(message.equalsIgnoreCase("success")){
                                    database.execSQL("UPDATE gps_loc SET status='Old' WHERE status='New' AND Id='" + gpcLocArrayList.get(position).getId() + "'");
                                    database.execSQL("DELETE from gps_loc  WHERE status='Old' AND Id='" + gpcLocArrayList.get(position).getId() + "'");
                                    Toast.makeText(EMPHomeActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
//                                Logout();
                            }


                            progressdialog.dismiss();
                        }catch (Exception e){
                            progressdialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressdialog.dismiss();
                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jobReq);
    }



    private void CallLocalSaving() {
        try {
            Cursor c;
            database = openOrCreateDatabase("digitalrupay", Context.MODE_PRIVATE, null);
            //  c = database.rawQuery("Select * from gps_loc where  status='New' ORDER BY datecaptured ASC limit 0,2 ", null);
            c = database.rawQuery("Select * from gps_loc where  status='New' ORDER BY datecaptured ASC ", null);
            count = c.getCount();
            if (count > 0) {
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            recordsupdated++;
                            gpcLoc=new Gploc();
                            int i1 = c.getColumnIndex("gps_lat");
                            gps_lati = c.getString(i1);
                            gpcLoc.setGps_latitude(gps_lati);

                            int i2 = c.getColumnIndex("gps_long");
                            gps_longit = c.getString(i2);
                            gpcLoc.setGps_longitude(gps_longit);

                            int i3 = c.getColumnIndex("datecaptured");
                            gps_datecapture = c.getString(i3);
                            gpcLoc.setDatecaptured(gps_datecapture);

                            int i4 = c.getColumnIndex("address");
                            gps_address = c.getString(i4);
                            gpcLoc.setAddress(gps_address);
                            int i5 = c.getColumnIndex("Id");
                            gps_id = c.getString(i5);
                            gpcLoc.setId(gps_id);

                            // new DashboardActivity.MakePaymentStbWise().execute("");
                            //SaveLocationInDB(gps_lati,gps_longit,gps_datecapture,gps_address,gps_id);
                            Log.e("Update Customer", "Send To Server");
                            gpcLocArrayList.add(gpcLoc);


                        } while (c.moveToNext());
                    }

                }

            } else {
//                Toast.makeText(EMPHomeActivity.this,"Locations SYnced Already",Toast.LENGTH_SHORT).show();
            }
        }catch (SQLiteException e){
            if (e.getMessage().contains("no such table")){
                database.execSQL("create table if not exists gps_loc(Id varchar(100),gps_lat varchar(100),gps_long varchar(10),datecaptured varchar(100),address varchar(100),status varchar(10))");
                // Log.e(TAG, "Creating table " + digitalrupay + "because it doesn't exist!" );
                // create table
                // re-run query, etc.
            }
        }
    }



    private void requestPermissions() {
        boolean shouldProvideRationale =
                false;
        shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)||
                ActivityCompat.shouldShowRequestPermissionRationale
                        (this, Manifest.permission.CAMERA)||
                ActivityCompat.shouldShowRequestPermissionRationale
                        (this, Manifest.permission.READ_EXTERNAL_STORAGE)||
                ActivityCompat.shouldShowRequestPermissionRationale
                        (this, Manifest.permission.CALL_PHONE);
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            new AlertDialog.Builder(EMPHomeActivity.this)
                    .setMessage("You need to accept all Permissions?")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(EMPHomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.CAMERA,
                                            Manifest.permission.CALL_PHONE},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);

                        }
                    })
                    .show();

            Snackbar.make(findViewById(android.R.id.content), "Permission Rationalie",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(EMPHomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.CAMERA,
                                            Manifest.permission.CALL_PHONE},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(EMPHomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                readgpsfile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                readExternalFile = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                callphone = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                Log.i(TAG, "User interaction was cancelled.");
            } else if (cameraPermission && readExternalFile && readgpsfile && callphone) {
                if(!isGPSEnabled(EMPHomeActivity.this)) {
                    displayLocationSettingsRequest(EMPHomeActivity.this);
                }else {

                    requestLocationUpdates(null);
                    mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class, 2, TimeUnit.MINUTES)
                            //.setConstraints(myConstraints)
                            //.setInputData(source)
                            .addTag("periodicWorkRequest")
                            .build();

                    WorkManager.getInstance().enqueue(mPeriodicWorkRequest);
                }
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        "Permission Denied",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }




    private void requestLocationUpdates(View view) {
        try {
            Log.i(TAG, "Starting location updates");
            Utils_location.setRequestingLocationUpdates(this, true);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            Utils_location.setRequestingLocationUpdates(this, false);
            e.printStackTrace();
        }
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean isGPSEnabled(EMPHomeActivity empHomeActivity) {
        LocationManager locationManager = (LocationManager)
                empHomeActivity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }


    private void displayLocationSettingsRequest(EMPHomeActivity empHomeActivity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(empHomeActivity)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class, 2, TimeUnit.SECONDS)
                                .addTag("periodicWorkRequest")
                                .build();
                        WorkManager.getInstance().enqueue(mPeriodicWorkRequest);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            checkPermissions();
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(EMPHomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }



//            private void startTimer() {
//                mTimer1 = new Timer();
//                mTt1 = new TimerTask() {
//                    public void run() {
//                        mTimerHandler.post(new Runnable() {
//                            public void run() {
//                                //TODO
//
//                                if (gps.canGetLocation()) {
//                                    latitude = gps.getLatitude();
//                                    longitude = gps.getLongitude();
//                                    getLocationTracking();
//                                    Log.e("GetLocation", "latitude :- " + latitude + "\n longitude :- " + longitude);
//                                    getLatlong();
//                           /* Intent dataIntent = new Intent(EMPHomeActivity.this, DataLoader.class);
//                            Messenger dataMessenger = new Messenger(hEMPGPSLOC);
//                            dataIntent.putExtra("MESSENGER", dataMessenger);
//                            dataIntent.putExtra("type", DataLoader.DataType.LocationTracking.ordinal());
//                            dataIntent.putExtra("gps_lang", "" + longitude);
//                            dataIntent.putExtra("gps_lat", "" + latitude);
//                            dataIntent.putExtra("emp_id", getempID);
//                            startService(dataIntent);*/
//                                } else {
//                                    gps.showSettingsAlert();
//                                }
//                            }
//                        });
//                    }
//                };
//                mTimer1.schedule(mTt1, 1, 30000);

        });
    }

            public void goInventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(EMPHomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) +
                ActivityCompat.checkSelfPermission(EMPHomeActivity.this,
                        Manifest.permission.CAMERA) +
                ActivityCompat.checkSelfPermission(EMPHomeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) +
                ActivityCompat.checkSelfPermission(EMPHomeActivity.this,
                        Manifest.permission.CALL_PHONE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void goComplaints(View view) {
        Intent intent = new Intent(this, ComplaintListCatActivity.class);
        startActivity(intent);
    }

    public void goExpenses(View view) {
        Intent intent = new Intent(this, ExpensesActivity.class);
        startActivity(intent);
    }

    public void goLeave(View view) {
        Intent intent = new Intent(this, LeaveActivity.class);
        startActivity(intent);
    }

    public static  EMPHomeActivity getInstance(){
        return instance;
    }

    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        Utils_location.setRequestingLocationUpdates(this, false);
        mFusedLocationClient.removeLocationUpdates(getPendingIntent());
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils_location.getRequestingLocationUpdates(this);
        Utils_location.getLocationUpdatesResult(this);
        //  Toast.makeText(this, Utils_location.getLocationUpdatesResult(this), Toast.LENGTH_SHORT).show();
    }
    public void redirectToLoginActivity(View view) {
        AlertDialog logoutDialog = new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        logout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

//    private void logout() {
//        if (AdminHomeActivity.mTimer1 != null) {
//            AdminHomeActivity.mTimer1.cancel();
//        }
//        if (StockistHomeActivity.mTimer1 != null) {
//            StockistHomeActivity.mTimer1.cancel();
//        }
//        if (SendGPSServicesLocation.mTimer1 != null) {
//            SendGPSServicesLocation.mTimer1.cancel();
//        }
//        if (EMPHomeActivity.mTimer1 != null) {
//            EMPHomeActivity.mTimer1.cancel();
//        }
//        if (UpdateComplaintActivity.mTimer1 != null) {
//            UpdateComplaintActivity.mTimer1.cancel();
//        }
//        SaveAppData.saveOperatorData(null);
//        SaveAppData.saveOperatorLoginData(null);
//        Intent login = new Intent(this, EMPLoginActivity.class);
//        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(login);
//        finish();
//    }


    public void redirectToHomeActivity(View view) {
        AlertDialog homeDialog = new AlertDialog.Builder(this).setTitle("Home")
                .setMessage("Are you sure want to navigate to home screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        home();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

    private void home() {
        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        String type = operatorCode.getRole_id();
        if (type.equalsIgnoreCase("28")) {
            Intent intent = new Intent(this, EMPHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("1")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("2")) {
            Intent intent = new Intent(this, StockistHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_myAccount) {
        /*  Intent intent=new Intent(ActivityNewDashboard.this,ActivityNewDashboard.class);
          startActivity(intent);*/
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.menu_notification) {
            /*Intent intent=new Intent(EMPHomeActivity.this,NotificationActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.menu_helpandsupport) {
            Intent intent = new Intent(EMPHomeActivity.this, InventoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_changepassword) {
           /* Intent intent=new Intent(EMPHomeActivity.this, ChangePasswordActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.menu_payhistory) {
            Intent intent = new Intent(EMPHomeActivity.this, ComplaintListCatActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_complaint) {
            Intent intent = new Intent(EMPHomeActivity.this, ProfileActivity.class);
          /*  intent.putExtra("customername",customerprofilemodel.getCstmrNm());
            intent.putExtra("customerphone",customerprofilemodel.getMblNu());*/
            startActivity(intent);
//        } else if (id == R.id.menu_speedtest) {
//            Intent intent = new Intent(EMPHomeActivity.this, SpeedTestActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_aboutus) {
            Intent intent = new Intent(EMPHomeActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_logout) {
            androidx.appcompat.app.AlertDialog paymentAlertDialog = new androidx.appcompat.app.AlertDialog.Builder(EMPHomeActivity.this).setTitle("Logout")
                    .setMessage("Are you sure want to Logout ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logoutmethod();
                          /*  dialog.dismiss();
                            SaveAppData.saveOperatorLoginData(null);
                            SaveAppData.saveOperatorData(null);
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                            Intent i = new Intent(EMPHomeActivity.this, EMPLoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();*/
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                                resetAllViews();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();
       // notificationcount();

        alertdialogshowforappointment();

       /* if (SaveAppData.getOperatorLoginData().getEmp_appointment_acceptance().equals("0")){
        alertDialogForAppointmentletter();
        }*/

            ProgressDialog progressDialog=new ProgressDialog(EMPHomeActivity.this);
            progressDialog.show();
//            String url="https://crm.tejays.in/webservices/employee/emp_notification_cnt";
            String url=BaseUrl.getBaseurl()+"employee/emp_notification_cnt";
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String count=jsonObject.getString("noti_status");
                      //  Toast.makeText(EMPHomeActivity.this, "hello"+count, Toast.LENGTH_SHORT).show();
                        if (count.equals("0")){
                            cart_badge.setVisibility(View.GONE);
                        }else {
                            cart_badge.setVisibility(View.VISIBLE);
                            cart_badge.setText(count);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(EMPHomeActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<>();
                    //params.put("base64",ba1);
                    params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }

    private void alertdialogshowforappointment() {

        ProgressDialog progressDialog=new ProgressDialog(EMPHomeActivity.this);
        progressDialog.show();

        String url=BaseUrl.getBaseurl()+"employee/check_emp_appointment_letter";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String msg = jsonObject.getString("response");
                    String emp_appntmnt = jsonObject.getString("emp_appointment_letter");
                    String emp_appntmnt_accptnce = jsonObject.getString("emp_appointment_acceptance");

//                      Toast.makeText(EMPHomeActivity.this, "hello"+msg, Toast.LENGTH_SHORT).show();
//                      Toast.makeText(EMPHomeActivity.this, "appointment "+emp_appntmnt, Toast.LENGTH_SHORT).show();
                    if (msg.equals("Success")){
//                        Toast.makeText(EMPHomeActivity.this, "Success"+msg, Toast.LENGTH_SHORT).show();
                        if (!emp_appntmnt.equals("")){
//                            Toast.makeText(EMPHomeActivity.this, "letter"+msg, Toast.LENGTH_SHORT).show();
                            if (emp_appntmnt_accptnce.equals("0")){
//                                Toast.makeText(EMPHomeActivity.this, "showing alert dialog", Toast.LENGTH_SHORT).show();
                                alertDialogForAppointmentletter();
                            }
                        }

                    }else {
                        cart_badge.setVisibility(View.VISIBLE);
                        cart_badge.setText(count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EMPHomeActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

