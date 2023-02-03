package com.Tejaysdr.msrc.activitys.emp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.dataModel.FileModel;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.Tejaysdr.msrc.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NewraiseExpensesActivity extends AppCompatActivity {
    private static final String TAG = "";
    Toolbar toolbar;
    TextInputEditText date_edtv,from_edtv,to_edtv,approx_edtv,exp_date_edtv,
            price_edtv,receipt_edtv,remarks_edtv,checkin_edtv,checkout_edtv,hotelname_edtv,address_edtv;
    Spinner traveltype_spnr,trnsp_spnr;
    LinearLayout ll_accomidation,ll_travel;
    TextInputLayout checkin_tv,checkout_tv;
    String transporttype,traveltypespnr;
    private Calendar myCalendar,cal,cal1,cal2;
   int i;
    int count=0;
    String ba1;
    private String expensesdays="0";
    private JSONObject customersObj;
    private ProgressDialog progressdialogpmt;
    Button btn_sctpic,submit_btn;
    private int REQUEST_IMAGE=100;
    private String encodedstring;
    ImageView img_preview;
  Bundle val;
    String closed_img_name = "";
    public static String ImageUploadURL;
    ArrayList<FileModel>filearraylist=new ArrayList<>();
  private  String FCMToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newraise_expenses);
        toolbar=findViewById(R.id.toolbar);
        setTitle("Raise Expenses");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        traveltype_spnr=findViewById(R.id.traveltype_spnr);
        trnsp_spnr=findViewById(R.id.trnsp_spnr);
        date_edtv=findViewById(R.id.date_edtv);
        from_edtv=findViewById(R.id.from_edtv);
        to_edtv=findViewById(R.id.to_edtv);
        approx_edtv=findViewById(R.id.approx_edtv);
        exp_date_edtv=findViewById(R.id.exp_date_edtv);
        price_edtv=findViewById(R.id.price_edtv);
        receipt_edtv=findViewById(R.id.receipt_edtv);
        remarks_edtv=findViewById(R.id.remarks_edtv);
        checkin_edtv=findViewById(R.id.checkin_edtv);
        checkout_edtv=findViewById(R.id.checkout_edtv);
        hotelname_edtv=findViewById(R.id.hotelname_edtv);
        address_edtv=findViewById(R.id.address_edtv);
        ll_accomidation=findViewById(R.id.ll_accomidation);
        ll_travel=findViewById(R.id.ll_travel);
        checkin_tv=findViewById(R.id.checkin_tv);
        checkout_tv=findViewById(R.id.checkout_tv);
        btn_sctpic=findViewById(R.id.btn_selectpic);
        submit_btn=findViewById(R.id.submit_btn);
        img_preview=findViewById(R.id.img_preview);
        val = getIntent().getExtras();
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
        }        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref1.edit();
        pref1.getString("FCMToken", FCMToken);
        editor.putString("FCM", FCMToken);
        editor.commit();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        date_edtv.setText(formattedDate);
//        getBusinessInfo();



        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Date = date_edtv.getText().toString();
                final String expdate = exp_date_edtv.getText().toString();
                final String From = from_edtv.getText().toString();
                final String To = to_edtv.getText().toString();
                final String Approxkms = approx_edtv.getText().toString();
                final String Checkindate = checkin_edtv.getText().toString();
                final String Checkoutdate = checkout_edtv.getText().toString();
                final String HotelName = hotelname_edtv.getText().toString();
                final String HotelAddress = address_edtv.getText().toString();
                final String Billno = receipt_edtv.getText().toString();
                final String Price = price_edtv.getText().toString();
                final String Remarks = remarks_edtv.getText().toString();
                if (traveltypespnr.equals("Travel")) {
                    ll_travel.setVisibility(View.VISIBLE);
                    ll_accomidation.setVisibility(View.GONE);

                    if (transporttype.equals("Select Transport Type")) {
                        Toast.makeText(NewraiseExpensesActivity.this, "Please Select Atleast One Transport Type", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (From.isEmpty()) {
                        from_edtv.setError("Please Enter From ");
                    } else if (To.isEmpty()) {
                        to_edtv.setError("Please Enter To ");
                    } else if (Approxkms.isEmpty()) {
                        approx_edtv.setError("Please Enter Approx Kms");
                    }
                } else if (traveltypespnr.equals("Food")) {
                    ll_travel.setVisibility(View.GONE);
                    ll_accomidation.setVisibility(View.VISIBLE);
                    checkin_tv.setVisibility(View.GONE);
                    checkout_tv.setVisibility(View.GONE);

                    if (Checkindate.isEmpty()) {
                        checkin_edtv.setError("Please Enter Check In Date");
                    } else if (Checkoutdate.isEmpty()) {
                        checkin_edtv.setError("Please Enter Check Out Date");
                    } else if (HotelName.isEmpty()) {
                        hotelname_edtv.setError("Please Enter Hotel Name");
                    } else if (HotelAddress.isEmpty()) {
                        address_edtv.setError("Please Enter Hotel Address");
                    }
                } else if (traveltypespnr.equals("Accommodation")) {
                    ll_travel.setVisibility(View.GONE);
                    ll_accomidation.setVisibility(View.VISIBLE);
                    if (HotelName.isEmpty()) {
                        hotelname_edtv.setError("Please Enter Hotel Name");
                    } else if (HotelAddress.isEmpty()) {
                        address_edtv.setError("Please Enter Hotel Address");
                    }
                } else if (traveltypespnr.equals("Others")) {
                    ll_travel.setVisibility(View.GONE);
                    ll_accomidation.setVisibility(View.VISIBLE);
                    checkin_tv.setVisibility(View.GONE);
                    checkout_tv.setVisibility(View.GONE);
                }
                if (Date.isEmpty()) {
                    date_edtv.setError("Please Select Date");
                } else if (traveltypespnr.equals("Select Type")) {
                    Toast.makeText(NewraiseExpensesActivity.this, "Please Select Atleast One Type", Toast.LENGTH_SHORT).show();
                    return;
                } else if (expdate.isEmpty()) {
                    exp_date_edtv.setError("Please Enter Date ");
                } else if (Billno.isEmpty()) {
                    receipt_edtv.setError("Please Enter Receipt No");
                } else if (Price.isEmpty()) {
                    price_edtv.setError("Please Enter Price");
                } else if (Remarks.isEmpty()) {
                    remarks_edtv.setError("Please Enter Remarks");
                } else {
                    if (traveltypespnr.equals("Travel")) {
                        ll_travel.setVisibility(View.VISIBLE);
                        ll_accomidation.setVisibility(View.GONE);
                    } else if (traveltypespnr.equals("Food")) {
                        ll_travel.setVisibility(View.GONE);
                        ll_accomidation.setVisibility(View.VISIBLE);
                        checkin_tv.setVisibility(View.GONE);
                        checkout_tv.setVisibility(View.GONE);
                    } else if (traveltypespnr.equals("Accommodation")) {
                        ll_travel.setVisibility(View.GONE);
                        ll_accomidation.setVisibility(View.VISIBLE);
                    } else if (traveltypespnr.equals("Others")) {
                        ll_travel.setVisibility(View.GONE);
                        ll_accomidation.setVisibility(View.VISIBLE);
                        checkin_tv.setVisibility(View.GONE);
                        checkout_tv.setVisibility(View.GONE);
                    }
                    new AlertDialog.Builder(NewraiseExpensesActivity.this)
                            .setTitle("Are you sure you want to Raise this entry?")
                            .setMessage(Html.fromHtml("<font color='#FF0000'>"+"Date : "+exp_date_edtv.getText().toString()+" & "+"Type : "+traveltypespnr+"</font>"))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new AddExpenses().execute("");
                                    Imageuploading(ImageUploadURL);

                                }
                                private void Imageuploading(final  String imageName) {
                                    final ProgressDialog dialog = new ProgressDialog(NewraiseExpensesActivity.this);
                                    dialog.show();
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setMessage("Please Wait..");
                                    ImageUploadURL = BaseUrl.getBaseurl() +"uploads/upload.php";
                                    int finalI = i;
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST,ImageUploadURL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            dialog.dismiss();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");
                                                if (success.equals("true")) {
                                                    Toast.makeText(NewraiseExpensesActivity.this, "" + response, Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(NewraiseExpensesActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
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
                                    RequestQueue requestQueue = Volley.newRequestQueue(NewraiseExpensesActivity.this);
                                    requestQueue.add(stringRequest);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        img_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(NewraiseExpensesActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }

                        }).check();
            }
        });

        approx_edtv.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(transporttype.equals("Bike")) {
                    price_edtv.setFocusableInTouchMode(false);
                    final String aproxkms = approx_edtv.getText().toString().trim();
                    final String price = String.valueOf(1.75);
                    if (aproxkms.length() != 0 && price.length() != 0) {
                 //       double totalprice = Double.parseDouble(aproxkms) * Double.parseDouble(price);
                        double totalprice = Double.parseDouble(aproxkms) * Double.parseDouble(SaveAppData.getOperatorLoginData().getPetrol_price());
                        price_edtv.setText(String.valueOf(totalprice));
                    } else {
                        price_edtv.setText("0");
                    }
                }else{
                    price_edtv.setFocusableInTouchMode(true);
                    price_edtv.setText("0");
                }

            }
        });
        checkin_edtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                int yy = myCalendar.get(Calendar.YEAR);
                int mm = myCalendar.get(Calendar.MONTH);
                int dd = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(NewraiseExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(year) +"-"+ String.valueOf(monthOfYear+ 1)
                                +"-"+ String.valueOf(dayOfMonth);
                        checkin_edtv.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        cal = Calendar.getInstance();
        cal1 = Calendar.getInstance();
        cal2 = Calendar.getInstance();
        exp_date_edtv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                DatePickerDialog dialog = new DatePickerDialog(NewraiseExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        exp_date_edtv.setText(""+arg1+"-"+(arg2+1)+"-"+arg3);
                        //  Toast.makeText(NewRaiseExpensesActivity.this, ""+arg1+"/"+(arg2+1)+"/"+arg3, Toast.LENGTH_SHORT).show();
                    }
                },
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                cal1.add(Calendar.DAY_OF_MONTH, 0);
                dialog.getDatePicker().setMaxDate(cal1.getTimeInMillis());
                cal2.add(Calendar.DAY_OF_MONTH,-7);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * Long.parseLong(expensesdays));
                dialog.show();
            }
        });
        checkout_edtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                int yy = myCalendar.get(Calendar.YEAR);
                int mm = myCalendar.get(Calendar.MONTH);
                int dd = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(NewraiseExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(year) +"-"+ String.valueOf(monthOfYear+ 1)
                                +"-"+ String.valueOf(dayOfMonth);
                        checkout_edtv.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        trnsp_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transporttype = trnsp_spnr.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        traveltype_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                traveltypespnr = traveltype_spnr.getSelectedItem().toString();
                if(traveltypespnr.equals("Travel")){
                    count++;
                    exp_date_edtv.setHint("Travel Date");
                    ll_travel.setVisibility(View.VISIBLE);
                    ll_accomidation.setVisibility(View.GONE);
                }else if(traveltypespnr.equals("Food")){
                    count++;
                    exp_date_edtv.setHint("Food Date");
                    trnsp_spnr.setSelection(0);
                    ll_travel.setVisibility(View.GONE);
                    ll_accomidation.setVisibility(View.VISIBLE);
                    checkin_tv.setVisibility(View.GONE);
                    checkout_tv.setVisibility(View.GONE);
                }else if(traveltypespnr.equals("Accommodation")){
                    count++;
                    exp_date_edtv.setHint("Accommodation Date");
                    trnsp_spnr.setSelection(0);
                    ll_travel.setVisibility(View.GONE);
                    checkin_tv.setVisibility(View.VISIBLE);
                    checkout_tv.setVisibility(View.VISIBLE);
                    ll_accomidation.setVisibility(View.VISIBLE);
                }else if(traveltypespnr.equals("Others")){
                    count++;
                    exp_date_edtv.setHint("Others");
                    trnsp_spnr.setSelection(0);
                    ll_travel.setVisibility(View.GONE);
                    checkin_tv.setVisibility(View.GONE);
                    checkout_tv.setVisibility(View.GONE);
                    ll_accomidation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(NewraiseExpensesActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(NewraiseExpensesActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encodedstring = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    ba1  = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    closed_img_name =  + System.currentTimeMillis() + count + ".png";
                    Log.d("closed_img_name",closed_img_name);
                    filearraylist.add(new FileModel(ba1,closed_img_name));
                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadProfile(String url) {
        //Log.d(TAG, "Image cache path: " + url);

        Glide.with(this).load(url)
                .into(img_preview);
        img_preview.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewraiseExpensesActivity.this);
        builder.setTitle("Permissions");
        builder.setMessage("Allow Permissions for Camera");
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //All of the fun happens inside the CustomListener now.
                //I had to move it to enable data validation.
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //All of the fun happens inside the CustomListener now.
                //I had to move it to enable data validation.
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


//    private void getBusinessInfo() {
//        try {
//            final ProgressDialog progressdialog = BaseUrl.createProgressDialog(this);
//            progressdialog.show();
//            progressdialog.setCancelable(false);
//            final String url = BaseUrlClass.getBaseUrl() + "business_info.php";
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            System.out.println(response);
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(response);
//                                try {
//                                    String message = jsonObject.getString("message");
//                                    String token_expiry = jsonObject.getString("token_expiry");
//                                    if(token_expiry.equals("Yes")){
//                                        Logout();
//                                    }
//                                    if (message.equalsIgnoreCase("success")) {
//                                        Iterator<String> keys = jsonObject.keys();
//                                        while (keys.hasNext()) {
//                                            String key = keys.next();
//                                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
//                                                BusinessinfoModel taggroupData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<BusinessinfoModel>() {
//                                                }.getType());
//                                                expensesdays=taggroupData.getExpenseDays();
//                                            }
//                                        }
//
//                                        progressdialog.dismiss();
//                                    } else {
//
//                                        progressdialog.dismiss();
//                                    }
//                                    progressdialog.dismiss();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    progressdialog.dismiss();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                progressdialog.dismiss();
//                                //progressdialog.dismiss();
//                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            progressdialog.dismiss();
//                            VolleyLog.e("Error: ", error.getMessage());
//                        }
//                    }){
//                @Override
//                protected java.util.Map<String, String> getParams() throws com.android.volley.AuthFailureError {
//                    String android_id = Settings.Secure.getString(getContentResolver(),
//                            Settings.Secure.ANDROID_ID);
//                    java.util.Map<String, String> params = new java.util.HashMap<String, String>();
//                    params.put("token", SaveAppData.getusename());
//                    return params;
//                }
//            };
//            new DefaultRetryPolicy(
//                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            );
//            stringRequest.setRetryPolicy(new RetryPolicy() {
//                @Override
//                public int getCurrentTimeout() {
//                    return 50000;
//                }
//
//                @Override
//                public int getCurrentRetryCount() {
//                    return 50000;
//                }
//
//                @Override
//                public void retry(VolleyError error) throws VolleyError {
//                }
//            });
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            requestQueue.add(stringRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private class AddExpenses extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                // Get the file data
                HttpURLConnection urlConnection;
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(closed_img_name);
                obj.accumulate("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                obj.accumulate("token",FCMToken);
                obj.accumulate("exp_date", date_edtv.getText().toString());
               obj.accumulate("exp_img1", closed_img_name);
                obj.accumulate(Utils.imageList, jsonArray);
                JSONObject object = new JSONObject();
                object.accumulate("exp_item_date", exp_date_edtv.getText().toString());
                object.accumulate("exp_type", traveltypespnr);
                object.accumulate("exp_mode", transporttype);
                object.accumulate("from_loc", from_edtv.getText().toString());
                object.accumulate("to_loc",to_edtv.getText().toString());
                object.accumulate("approx_km", approx_edtv.getText().toString());
                object.accumulate("exp_price", price_edtv.getText().toString());
                object.accumulate("exp_remarks", remarks_edtv.getText().toString());
                object.accumulate("check_in_date", checkin_edtv.getText().toString());
                object.accumulate("check_out_date", checkout_edtv.getText().toString());
                object.accumulate("hotel_name", hotelname_edtv.getText().toString());
                object.accumulate("receipt_no", receipt_edtv.getText().toString());
                object.accumulate("hotel_address", address_edtv.getText().toString());
                array.put(object);
                obj.accumulate("ExpenseItems", array);
                String data = obj.toString();
                String result = null;
                try {
                    String Url = BaseUrl.getBaseurl()+"employee/expenses_add_new";
                    //Connect
                    urlConnection = (HttpURLConnection) ((new URL(Url).openConnection()));
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.connect();
                    //Write
                    OutputStream outputStream = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(data);
                    writer.close();
                    outputStream.close();
                    //Read
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                    result = sb.toString();
                    customersObj = new JSONObject(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Expenses", "Success");
            progressdialogpmt.dismiss();
            //    Toast.makeText(NewRaiseExpensesActivity.this, result, Toast.LENGTH_SHORT).show();
            try {
                progressdialogpmt.dismiss();
                Iterator<String> keys = customersObj.keys();
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                String token_expiry = customersObj.getString("token_expiry");
                if(token_expiry.equals("Yes")){
                    Logout();
                }
                if (message.equalsIgnoreCase("success")) {
                    Intent i1=new Intent(NewraiseExpensesActivity.this, ExpensesActivity.class);
                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i1);
                    finish();
                    Toast.makeText(NewraiseExpensesActivity.this, text, Toast.LENGTH_SHORT).show();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            Toast.makeText(NewraiseExpensesActivity.this, "Expenses Added Successfully.", Toast.LENGTH_SHORT).show();
                            /*Intent i=new Intent(EnterReturnsActivity.this, ReturnsActivity.class);
                            startActivity(i);*/

                        }

                    }
                }
                else {
                    Toast.makeText(NewraiseExpensesActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            progressdialogpmt = new ProgressDialog(NewraiseExpensesActivity.this);
            progressdialogpmt.setTitle("Please Wait");
            progressdialogpmt.show();
            progressdialogpmt.setCancelable(false);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }



    private void Logout() {
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String formattedDate = df.format(c);
            final String url = BaseUrl.getBaseurl() + "emp_logout.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                SaveAppData.saveOperatorLoginData(null);
                                SaveAppData.setusename("");
//                                EMPHomeActivity.getInstance().removeLocationUpdates();
                                Intent i = new Intent(NewraiseExpensesActivity.this,EMPLoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                    /*if(longit!=null&latitude!=null){
                        params.put("gps_lat", latitude);
                        params.put("gps_lang", longit);
                    }else{
                        params.put("gps_lat", "0.0");
                        params.put("gps_lang", "0.0");
                    }*/
                    params.put("gps_lat", "0.0");
                    params.put("gps_lang", "0.0");
                    params.put("dateCreated", formattedDate);
                    params.put("Address", "");
                    params.put("token", SaveAppData.getusename());
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

