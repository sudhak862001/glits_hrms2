package com.Tejaysdr.msrc.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.Tejaysdr.msrc.ImagepickerActivity;
import com.Tejaysdr.msrc.activitys.emp.ImagePickerActivity;
import com.Tejaysdr.msrc.activitys.emp.UpdateComplaintActivity;
import com.Tejaysdr.msrc.dataModel.FileModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.Tejaysdr.msrc.utils.ImageLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class EMPLoginActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private String TAG = "login";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1888;
    String ba1, picturePath = "", closed_img_name = "";
    private static final int REQUEST_CODE = 101;
    TextView busiName, tvSearchoperator;
    EditText Operator_username, Operator_password;
    private ImageView c_logo;
    double lat = 0.0, longi = 0.0;
    private OperatorCode operatorCode;
    private String mainURL;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private String getOperator_username, getOperator_password;
    ArrayList<OperatorLoginData> operatorLoginDatalist = new ArrayList<>();
    OperatorLoginData operatorLoginDatamodel;
    private GoogleMap mMap;
    Bundle val;
    private String FCMToken = "";
    public static final int REQUEST_CHECK_SETTINGS = 20001;
    Button tvPaymentName, tvImageView,login_image,upload_image;
    private String android_id1;
    String getempID,name;
    int count1;
    AlertDialog alertDialog;
    ImageView login_upload;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    private ImageView iv_visible, iv_invisible;
    private Bitmap bitmap1;
    ImageView imageprevf;
    public static String ImageUploadURL;
    Uri selectedImage, fileUri;
    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};
    ArrayList<FileModel> filearraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplogin);

        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        OperatorCode operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        android_id1 = Settings.Secure.getString(EMPLoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        Toast.makeText(this, "" + android_id1, Toast.LENGTH_SHORT).show();
        //String bName=operatorCode.getop_busiName();
        //String uri=operatorCode.getop_logo();
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        busiName = (TextView) findViewById(R.id.busiName);
        tvSearchoperator = (TextView) findViewById(R.id.tvSeachoperatorCode);
//        imageprevf = (ImageView) findViewById(R.id.imagepref);
        tvPaymentName = findViewById(R.id.tvPaymentName);
//        tvImageView = findViewById(R.id.tvimagebtn);
        iv_invisible = findViewById(R.id.iv_invisible);
        iv_visible = findViewById(R.id.iv_visible);
        ImageUploadURL = BaseUrl.getBaseurl() + "uploads/upload.php";
        tvSearchoperator.setPaintFlags(tvSearchoperator.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSearchoperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EMPLoginActivity.this, SearchOperatorCode.class);
                startActivity(intent);
                EMPLoginActivity.this.finish();
            }
        });
        Operator_username = (EditText) findViewById(R.id.Operator_username);
        Operator_password = (EditText) findViewById(R.id.Operator_password);

        // busiName.setText(bName);
        c_logo = (ImageView) findViewById(R.id.c_logo);
        //busiName.setText(bName);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        int loader = R.drawable.apsfl_logo;
        //imgLoader.DisplayImage(uri, loader, c_logo);
        val = getIntent().getExtras();
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        FCMToken = pref.getString("FCMToken", null);
        //  Log.e("",FCMToken);
        if (FCMToken == null) {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            FCMToken = task.getResult();

                            // Log and toast
                        }
                    });
        }
        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref1.edit();
        pref1.getString("FCMToken", FCMToken);
        editor.putString("FCM", FCMToken);
        editor.commit();
//        getempID = SaveAppData.getOperatorLoginData().getEmp_id();
        tvPaymentName.setOnClickListener(v -> {
            if (Operator_username.getText().toString().length() == 0) {
                Toast.makeText(EMPLoginActivity.this, "Please Enter Email/Mobile number", Toast.LENGTH_SHORT)
                        .show();
            } else if (Operator_password.getText().toString().length() == 0) {
                Toast.makeText(EMPLoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT)
                        .show();
            } else {
                getOperator_username = Operator_username.getText().toString().trim();
                getOperator_password = Operator_password.getText().toString().trim();
//                imagecustomalertdialog();
//                Imagecapture();
                Logindetailsupdate();
            }
        });

                /*builder.setTitle("Location Settings");

                //Setting message manually and performing action on button click
                builder.setMessage("For a better experience please choose Allow All The Time permission in location settings")
                        .setCancelable(false)
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    ActivityCompat.requestPermissions(EMPLoginActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,},
                                            MY_PERMISSIONS_REQUEST_LOCATION_bg);
                                }else {
                                   // CheckLoginCredentials(et_userName.getText().toString(),et_pwd.getText().toString());
                                    Logindetailsupdate();
                                }
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Location Settings");
                alert.show();*/


        // Logindetailsupdate();
          /*  Intent dataIntent = new Intent(EMPLoginActivity.this, DataLoader.class);
            Messenger dataMessenger = new Messenger(mHandler);
            dataIntent.putExtra("MESSENGER", dataMessenger);
            dataIntent.putExtra("type", DataLoader.DataType.EMPLOYEE_LOGIN.ordinal());
            dataIntent.putExtra("username", getOperator_username);
            dataIntent.putExtra("password", getOperator_password);
            startService(dataIntent);*/

        iv_invisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible.setVisibility(View.VISIBLE);
                iv_invisible.setVisibility(View.GONE);
                String pass = Operator_password.getText().toString();
                Operator_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Operator_password.setInputType(InputType.TYPE_CLASS_TEXT);
                Operator_password.setText(pass);
                Operator_password.setSelection(pass.length());
            }
        });

        iv_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible.setVisibility(View.GONE);
                iv_invisible.setVisibility(View.VISIBLE);
                String pass = Operator_password.getText().toString();
                Operator_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                Operator_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Operator_password.setText(pass);
                Operator_password.setSelection(pass.length());
            }
        });
    }
//        uploadimage();




//    private void uploadimage() {
//        for (int i = 0; i < filearraylist.size(); i++) {
//            final ProgressDialog dialog = new ProgressDialog(this);
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setMessage("Please Wait..");
//            ImageUploadURL = BaseUrl.getBaseurl() + "uploads/upload.php";
//            int finalI = i;
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, ImageUploadURL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    dialog.dismiss();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String success = jsonObject.getString("success");
//                        if (success.equals("true")) {
//                            Toast.makeText(EMPLoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
//                        /*if (picture1Path.length() != 0) {
//                            if (status.equals("one")) {
//                                picture1Path = "";
//                                upload(picture2Path, closed_img_name2, "two");
//                            } else if (status.equals("two")) {
//                                picture2Path = "";
//                                closedImageName = closed_img_name + "," + closed_img_name1 + "," + closed_img_name2;
//                                ComplaintEditlist();
//                            }else {
//                                upload(picture2Path, closed_img_name2, "one");
//                            }
//                        }*/
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
////                    Toast.makeText(updateComplaintActivity, "Image uploaded", Toast.LENGTH_SHORT).show();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                    dialog.dismiss();
////                }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    dialog.dismiss();
//                    Toast.makeText(EMPLoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    //params.put("base64",ba1);
//                    params.put("base64", filearraylist.get(finalI).getPath());
//                    params.put("ImageName", filearraylist.get(finalI).getName());
//                    return params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            requestQueue.add(stringRequest);
//        }
//        loginimageuploadAPI();
//    }

//    private void imagecustomalertdialog() {
//        AlertDialog.Builder builder1=new AlertDialog.Builder(this);
//        View view=getLayoutInflater().inflate(R.layout.image_upload_dialog,null);
//        login_upload = view.findViewById(R.id.login_image);
//        Button cancel = view.findViewById(R.id.image_no);
//        TextView buttonok=view.findViewById(R.id.buttonOk1);
//         upload_image = view.findViewById(R.id.image_yes);
//         login_image = view.findViewById(R.id.image_login);
//
//         login_image.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
////                 Toast.makeText(EMPLoginActivity.this, "emp id1"+SaveAppData.getOperatorLoginData().getEmp_id(), Toast.LENGTH_SHORT).show();
//                 Logindetailsupdate();
////                 loginimageuploadAPI();
//
////                 Logindetailsupdate();
//             }
//         });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        upload_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Imagecapture();
////                imageuploadIntent();
//            }
//        });
//buttonok.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        alertDialog.dismiss();
//    }
//});
//        builder1.setView(view);
//        alertDialog = builder1.create();
//        alertDialog.show();
//    }
//
//
//
//        private void loginimageuploadAPI () {
//            progressDialog.show();
//            progressDialog.setMessage("Please wait...");
////        Toast.makeText(EMPLoginActivity.this, "emp id2"+SaveAppData.getOperatorLoginData().getEmp_id(), Toast.LENGTH_SHORT).show();
//            String baseurl = "https://tejays.digitalrupay.com/webservices/employee/check_login_pic_of_employee";
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    progressDialog.dismiss();
//                    Toast.makeText(EMPLoginActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
//                    Toast.makeText(EMPLoginActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    //params.put("base64",ba1);
//                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
//                    params.put("closed_img",closed_img_name);
//                    return params;
//                }
//            };
//
//            requestQueue.add(stringRequest);
//        }
//
//        private void Imagecapture () {
//            Intent pictureIntent = new Intent(
//                    MediaStore.ACTION_IMAGE_CAPTURE);
////            pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
//                //Create a file to store the image
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                }
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
//                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            photoURI);
//                    startActivityForResult(pictureIntent,
//                            100);
//                }
//
//
//            } else {
//                Toast.makeText(getApplication(), "Camera not supportedImageprev", Toast.LENGTH_LONG).show();
//            }
//
//
////        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        //        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//////        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//////        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
////        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//        }
//
//        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            try {
//                if (requestCode == 100 && resultCode == RESULT_OK) {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    bitmap1 = BitmapFactory.decodeFile(picturePath,
//                            options);
//                    bitmap1 = getResizedBitmap(bitmap1, 400);
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
//                    String currentDateandTime = sdf.format(new Date());
//                    Canvas canvas = new Canvas(bitmap1);
//                    canvas.drawBitmap(bitmap1, 0, 0, null);
//                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                    paint.setColor(Color.RED);
//                    paint.setTextSize(14);
//                    paint.setStyle(Paint.Style.FILL);
////                canvas.drawText(gio_loc, 30, 250, paint);
//                    canvas.drawText(currentDateandTime, 30, 270, paint);
//                    //   login_upload.setVisibility(View.VISIBLE);
//                    login_upload.setImageBitmap(bitmap1);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream.toByteArray();
//                    ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                    upload_image.setVisibility(View.GONE);
//                    login_image.setVisibility(View.VISIBLE);
//                    closed_img_name = System.currentTimeMillis() + count1 + ".png";
//                    Log.d("closed_img_name", closed_img_name);
//                    filearraylist.add(new FileModel(ba1, closed_img_name));
//
//                }
//            } catch (Exception e) {
//                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//
//        private Bitmap getResizedBitmap (Bitmap image,int maxSize){
//            int width = image.getWidth();
//            int height = image.getHeight();
//            float bitmapRatio = (float) width / (float) height;
//            if (bitmapRatio > 1) {
//                width = maxSize;
//                height = (int) (width / bitmapRatio);
//            } else {
//                height = maxSize;
//                width = (int) (height * bitmapRatio);
//            }
//
//            return Bitmap.createScaledBitmap(image, width, height, true);
//        }
//
//
//        private File createImageFile () throws IOException {
//            String timeStamp =
//                    new SimpleDateFormat("yyyyMMdd_HHmmss",
//                            Locale.getDefault()).format(new Date());
//            String imageFileName = "IMG_" + timeStamp + "_";
//            File storageDir =
//                    getExternalCacheDir();
//            File image = File.createTempFile(
//                    imageFileName,  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//
//            picturePath = image.getAbsolutePath();
//            fileUri = Uri.fromFile(new File(picturePath));
//            return image;
//        }


    private void Logindetailsupdate () {
        String url = BaseUrl.getBaseurl() + "Employee";
        Log.d("LoginApi", url);
        Log.e("LoginApi1", url);
        System.out.println(url);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*Toast.makeText(EMPLoginActivity.this, "login success response" , Toast.LENGTH_SHORT).show();
                loginimageuploadAPI();*/
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String response12 = jsonObject.getString("response");
                    if (response12.equalsIgnoreCase("Successfull")) {
                        dialog.dismiss();
//                        Toast.makeText(EMPLoginActivity.this, "" + response12, Toast.LENGTH_SHORT).show();
                        operatorLoginDatalist = new ArrayList<>();
                        OperatorLoginData operatorLoginData = new OperatorLoginData();
                        String type = jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_id");
                        // Toast.makeText(EMPLoginActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                        // String type1=jsonObject.getJSONObject("0").getString("user_role");
                        try {
                            operatorLoginData.setFullname(jsonObject.getJSONObject("data").getString("fullname"));
                            operatorLoginData.setRole_id(jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_id"));
                            operatorLoginData.setFirstname(jsonObject.getJSONObject("data").getString("firstname"));
                            operatorLoginData.setMobile(jsonObject.getJSONObject("data").getString("mobile"));
                            operatorLoginData.setType(jsonObject.getJSONObject("data").getString("type"));
                            operatorLoginData.setEmail(jsonObject.getJSONObject("data").getString("email"));
                            operatorLoginData.setEmp_id(jsonObject.getJSONObject("data").getString("emp_id"));
                            operatorLoginData.setDesignation(jsonObject.getJSONObject("data").getString("designation"));
                            operatorLoginData.setRole_name(jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_name"));
                            operatorLoginData.setPerm_add_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_add_complaint"));
                            operatorLoginData.setPerm_edit_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_edit_complaint"));
                            operatorLoginData.setPerm_view_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_view_complaint"));
                            operatorLoginData.setPerm_del_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_del_complaint"));
                            operatorLoginData.setPerm_close_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_close_complaint"));
                            operatorLoginData.setPerm_complaint_tech(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_complaint_tech"));
                            operatorLoginData.setLogin_emp_id(jsonObject.getJSONObject("data").getString("login_emp_id"));
                            operatorLoginData.setLogin_username(jsonObject.getJSONObject("data").getString("login_username"));
                            operatorLoginData.setScroll_msg(jsonObject.getJSONObject("data").getString("scroll_msg"));
                            operatorLoginData.setSupport_no(jsonObject.getJSONObject("data").getString("support_no"));
                            operatorLoginData.setPetrol_price(jsonObject.getJSONObject("data").getString("petrol_price"));
                            operatorLoginData.setToken(jsonObject.getJSONObject("data").getString("token"));
                            operatorLoginData.setImgstatus(jsonObject.getJSONObject("data").getString("emp_login_img_status"));
                            operatorLoginData.setEmp_appointment_acceptance(jsonObject.getJSONObject("data").getString("emp_appointment_acceptance"));
                            operatorLoginData.setEmp_appointment_letter(jsonObject.getJSONObject("data").getString("emp_appointment_letter"));
                            operatorLoginDatalist.add(operatorLoginData);

//                                Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
//                                startActivity(intent);
                            EMPLoginActivity.this.finish();
                            SaveAppData.getSessionDataInstance().saveOperatorLoginData(operatorLoginData);
//                                Toast.makeText(EMPLoginActivity.this, "login success response", Toast.LENGTH_SHORT).show();
//                                loginimageuploadAPI();
//                            Intent intent1 = new Intent(EMPLoginActivity.this, ImagepickerActivity.class);
//                            startActivity(intent1);
//                            Toast.makeText(EMPLoginActivity.this,""+operatorLoginData.getImgstatus(),Toast.LENGTH_SHORT).show();
                            if(SaveAppData.getOperatorLoginData().getImgstatus().equals("1")){
                                Intent intent1 = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
                               startActivity(intent1);
                            }
                            else {
                                Intent intent2 = new Intent(EMPLoginActivity.this,ImagepickerActivity.class);
                                startActivity(intent2);
                            }
//                                 upload(String picturePath, String ImageName, String status)
//                                uploadimage();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(EMPLoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        //Toast.makeText(EMPLoginActivity.this, ""+operatorCode.getemp_first_name(), Toast.LENGTH_SHORT).show();
//                        if(type.equalsIgnoreCase("28")){
//                            Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
//                            startActivity(intent);
//                            EMPLoginActivity.this.finish();
//                        }else if(type.equalsIgnoreCase("36")){
//                            Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
//                            startActivity(intent);
//                            EMPLoginActivity.this.finish();
//                        }/*else if(type.equalsIgnoreCase("2")){
//                            Intent intent = new Intent(EMPLoginActivity.this, StockistHomeActivity.class);
//                            startActivity(intent);
//                            EMPLoginActivity.this.finish();
//                        }*/
                    } else {
                        dialog.dismiss();
                        Toast.makeText(EMPLoginActivity.this, "" + response12, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EMPLoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(EMPLoginActivity.this, "Error occured Please Try Again", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        }) {

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                 headers.put("Content-Type","application/json");
////                headers.put("x-access-token",SaveAppData.getOperatorLoginData().getAccessToken());
//                return headers;
//            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", getOperator_username);
                params.put("password", getOperator_password);
                params.put("token", FCMToken);
                params.put("imei_1", android_id1);
                params.put("imei_2", android_id1);
                params.put("device_id", "");
//                if(FCMToken==null){
//                    params.put("device_id","");
//                }else {
//                    params.put("device_id", FCMToken);
//                }
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
        /*StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                 *//*   Log.d("jsonobject1",jsonObject);
                    Log.e("jsonobject2",jsonObject);*//*
                    System.out.println(jsonObject);
                    Toast.makeText(EMPLoginActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    operatorLoginDatalist=new ArrayList<>();
                    OperatorLoginData operatorLoginData=new OperatorLoginData();
                    String type=jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_id");
                   // Toast.makeText(EMPLoginActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                   // String type1=jsonObject.getJSONObject("0").getString("user_role");
                    operatorLoginData.setFullname(jsonObject.getJSONObject("data").getString("fullname"));
                    operatorLoginData.setLastname(jsonObject.getJSONObject("data").getString("firstname"));
                    operatorLoginData.setMobile(jsonObject.getJSONObject("data").getString("mobile"));
                    operatorLoginData.setType(jsonObject.getJSONObject("data").getString("type"));
                    operatorLoginData.setEmail(jsonObject.getJSONObject("data").getString("email"));
                    operatorLoginData.setEmp_id(jsonObject.getJSONObject("data").getString("emp_id"));
                    operatorLoginData.setDesignation(jsonObject.getJSONObject("data").getString("designation"));
                    operatorLoginData.setRole_id(jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_id"));
                    operatorLoginData.setRole_name(jsonObject.getJSONObject("data").getJSONObject("roles").getString("role_name"));
                    operatorLoginData.setPerm_add_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_add_complaint"));
                    operatorLoginData.setPerm_edit_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_edit_complaint"));
                    operatorLoginData.setPerm_view_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_view_complaint"));
                    operatorLoginData.setPerm_del_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_del_complaint"));
                    operatorLoginData.setPerm_close_complaint(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_close_complaint"));
                    operatorLoginData.setPerm_complaint_tech(jsonObject.getJSONObject("data").getJSONObject("roles").getString("perm_complaint_tech"));
                    operatorLoginData.setLogin_emp_id(jsonObject.getJSONObject("data").getString("login_emp_id"));
                    operatorLoginData.setLogin_username(jsonObject.getJSONObject("data").getString("login_username"));
                    //operatorLoginDatalist.add(operatorLoginData);
                    SaveAppData.getSessionDataInstance().saveOperatorLoginData(operatorLoginData);
                    OperatorLoginData operatorCode=null;

                    operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                    //Toast.makeText(EMPLoginActivity.this, ""+operatorCode.getemp_first_name(), Toast.LENGTH_SHORT).show();
                    if(type.equalsIgnoreCase("28")){
                        Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
                        startActivity(intent);
                        EMPLoginActivity.this.finish();
                    }else if(type.equalsIgnoreCase("1")){
                        Intent intent = new Intent(EMPLoginActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        EMPLoginActivity.this.finish();
                    }else if(type.equalsIgnoreCase("2")){
                        Intent intent = new Intent(EMPLoginActivity.this, StockistHomeActivity.class);
                        startActivity(intent);
                        EMPLoginActivity.this.finish();
                    }
                    else {
                        Operator_username.setText("");
                        Operator_password.setText("");
                        Toast.makeText(EMPLoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(EMPLoginActivity.this, "Error While Parsing", Toast.LENGTH_SHORT).show();
            }
    }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String>params=new HashMap<>();
            params.put("username",getOperator_username);
            params.put("password",getOperator_password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }*/

   /* private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            Toast.makeText(EMPLoginActivity.this, ""+bundle, Toast.LENGTH_SHORT).show();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                Toast.makeText(EMPLoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                String message = responseObj.getString("message");
                String text = responseObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            OperatorLoginData operatorLoginData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<OperatorLoginData>() {}.getType());
                            SaveAppData.getSessionDataInstance().saveOperatorLoginData(operatorLoginData);
                            OperatorLoginData operatorCode=null;
                            operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                            String type=operatorCode.getuser_type();
                            if(type.equalsIgnoreCase("4")) {
                                Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }else if(type.equalsIgnoreCase("1")){
                                Intent intent = new Intent(EMPLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }else if(type.equalsIgnoreCase("2")){
                                Intent intent = new Intent(EMPLoginActivity.this, StockistHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }
                        }
                    }
                }else{
                    Operator_username.setText("");
                    Operator_password.setText("");
                    Toast.makeText(EMPLoginActivity.this,text,Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        lat=mLastLocation.getLatitude();
        longi=mLastLocation.getLongitude();
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
       /* LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latLng.latitude, latLng.longitude,
                getApplicationContext(), new GeocoderHandler());*/
        // getAddressFromLocation(latLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION_bg = 199;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            } else {
                // No explanation needed, we can request the permission.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_LOCATION_bg: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        if (grantResults[i] >= 0) {
//                            EMPLoginActivity(Operator_username.toString(),Operator_password.getText().toString());
                            // Logindetailsupdate();
                            Toast.makeText(this, "permission Accepted", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                displayLocationSettingsRequest(EMPLoginActivity.this);

                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            // address_edtv.setText(locationAddress);
        }
    }


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
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
                        checkLocationPermission();

                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        // displayLocationSettingsRequest(LoginActivity.this);
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(EMPLoginActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

}
