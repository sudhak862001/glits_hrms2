package com.Tejaysdr.msrc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.dataModel.FileModel;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ImagepickerActivity extends AppCompatActivity {
    Context context;
    Button button,upload_btn;
    private static final String TAG = "";
    private Bitmap bitmap1;
    double latitude = 0.0, longitude = 0.0;
    String ba1, picturePath = "", closed_img_name = "";
    ArrayList<FileModel> filearraylist = new ArrayList<>();
    Uri selectedImage, fileUri;
    ImageView imageView,imageView1,imageView2;
    int count1;
    public static String ImageUploadURL;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String token;
    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepicker);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        imageView = findViewById(R.id.login_image1);
        imageView1=findViewById(R.id.imagelogout);
        imageView2=findViewById(R.id.uploadimage1);
//        button = findViewById(R.id.uploadimage1);
        upload_btn = findViewById(R.id.upload_btn);
//        int requestCode = 200;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(permissions, requestCode);
//        }

//Check if permission is already granted
//thisActivity is your activity. (e.g.: MainActivity.this)
            if (ContextCompat.checkSelfPermission(ImagepickerActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Give first an explanation, if needed.
                if (ActivityCompat.shouldShowRequestPermissionRationale(ImagepickerActivity.this,
                        Manifest.permission.CAMERA)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(ImagepickerActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }
            }

//        upload_btn.setEnabled(false);
//        upload_btn.setBackgroundColor(R.color.colorPrimary);
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
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//logoutmetod();
                androidx.appcompat.app.AlertDialog paymentAlertDialog = new androidx.appcompat.app.AlertDialog.Builder(ImagepickerActivity.this).setTitle("Logout")
                        .setMessage("Are you sure want to Logout ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(ImagepickerActivity.this,EMPLoginActivity.class);
                                startActivity(intent);
//                                logoutmethod();
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
//                        drawer.closeDrawer(GravityCompat.START);
//                        return true;


    });


        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(ba1==null){
                    Toast.makeText(ImagepickerActivity.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
                }
                uploadimage();
            }
        });
      imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagecapture();
//                uploadimage();
            }

           /* private void Imagecapture() {
                Intent pictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
//            pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                    //Create a file to store the image
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(context, getPackageName() + ".fileprovider", photoFile);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                photoURI);
                        startActivityForResult(pictureIntent,
                                100);
                    }


                } else {
                    Toast.makeText(getApplication(), "Camera not supportedImageprev", Toast.LENGTH_LONG).show();
                }


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
////        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }*/
        });
    }

    private void logoutmetod() {
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
                Intent login = new Intent(ImagepickerActivity.this, EMPLoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ImagepickerActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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



    private void Imagecapture() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
      pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        100);
            }
        } else {
            Toast.makeText(getApplication(), "Camera not Supported", Toast.LENGTH_LONG).show();
        }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
////        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100 && resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap1 = BitmapFactory.decodeFile(picturePath,
                        options);
                bitmap1 = getResizedBitmap(bitmap1, 400);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Canvas canvas = new Canvas(bitmap1);
                canvas.drawBitmap(bitmap1, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setTextSize(14);
                paint.setStyle(Paint.Style.FILL);
//                canvas.drawText(gio_loc, 30, 250, paint);
                canvas.drawText(currentDateandTime, 30, 270, paint);
                //   login_upload.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap1);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                imageView2.setVisibility(View.VISIBLE);
                upload_btn.setVisibility(View.VISIBLE);
                closed_img_name = System.currentTimeMillis() + count1 + ".png";
                Log.d("closed_img_name", closed_img_name);
                filearraylist.add(new FileModel(ba1, closed_img_name));

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
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


    private void uploadimage() {
        for (int i = 0; i < filearraylist.size(); i++) {
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
                            Toast.makeText(ImagepickerActivity.this, "" + response, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ImagepickerActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
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
        loginimageuploadAPI();
    }

    private void loginimageuploadAPI() {
//        progressDialog.show();
//        progressDialog.setMessage("Please wait...");
//        Toast.makeText(EMPLoginActivity.this, "emp id2"+SaveAppData.getOperatorLoginData().getEmp_id(), Toast.LENGTH_SHORT).show();
//        String baseurl = "https://tejays.digitalrupay.com/webservices/employee/check_login_pic_of_employee";
        String baseurl = BaseUrl.getBaseurl() + "employee/check_login_pic_of_employee";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String msg= jsonObject.getString("response");
                    if(msg.equals("Success")) {
                        String imgstatus1 = jsonObject.getString("emp_login_img_status");
                        if (imgstatus1.equals("1")) {
                            Intent intent7 = new Intent(ImagepickerActivity.this, EMPHomeActivity.class);
                            startActivity(intent7);
                            finish();
                        } else {
//                            Intent intent6 = new Intent(ImagepickerActivity.this, ImagepickerActivity.class);
//                            startActivity(intent6);
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
                params.put("closed_img",closed_img_name);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

    }
}
