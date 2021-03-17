package com.example.weekenderpro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddEventsActivity extends AppCompatActivity {
    ImageView ivBack, eventImage;
    EditText etLocation, etEventName, etRating, etEventType, etPrice, etEventContact,etEventTime,etEventDescription,etEventDate;
    Button btnSave;
    TextView tvUpload;
    Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static final String TAG = "AddHotelActivity";

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private StorageTask mUploadTask;
    private ProgressBar mProgress;

    Events eventss;

    long maxid = 0;

    String mEventLocation, mEventName, mEventRating,mEventPrice,mEventDate,mEventContacts,mEventDescription,mEventTime, mEventType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        storageReference = FirebaseStorage.getInstance().getReference("hotelProducts");
        databaseReference = FirebaseDatabase.getInstance().getReference("hotelProducts");

        etEventDate = findViewById(R.id.etEventDate);
        ivBack = findViewById(R.id.ivBack);
        eventImage = findViewById(R.id.eventImage);
        etLocation = findViewById(R.id.etLocation);
        etEventName = findViewById(R.id.etEventName);
        etRating = findViewById(R.id.etRating);
        etEventType = findViewById(R.id.etEventType);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);
        tvUpload = findViewById(R.id.tvUpload);
        mProgress = findViewById(R.id.progressBar);
        etEventContact = findViewById(R.id.etEventContacts);
        etEventTime = findViewById(R.id.etEventTime);
        etEventDescription = findViewById(R.id.etEventDescription);

        eventss = new Events();

        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(AddEventsActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                    } else {

                        selectImage();

                    }

                } else {
                    selectImage();
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveEntries();


            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });

    }

    private void uploadFile() {

        if (image_uri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(image_uri));

            /*uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);*/

            UploadTask uploadTask = fileReference.putFile(image_uri);
            Toast.makeText(this, "UP " + uploadTask, Toast.LENGTH_SHORT).show();

            // Register observers to listen for when the download is done or if it fails

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddEventsActivity.this, "Failed to Upload...", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddEventsActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                    if (taskSnapshot.getMetadata() != null)
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(AddEventsActivity.this, "Proceed...", Toast.LENGTH_LONG).show();
                                    String sImage = uri.toString();

                                    mProgress.setVisibility(View.VISIBLE);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgress.setProgress(0);
                                        }
                                    }, 500);
                                    Toast.makeText(AddEventsActivity.this, "Upload Successful..." + sImage, Toast.LENGTH_SHORT).show();

                                    eventss = new Events(mEventLocation, mEventName, mEventRating,mEventPrice,mEventDate,mEventContacts,mEventDescription,mEventTime, mEventType, sImage);
                                    String key = databaseReference.push().getKey();
                                    eventss.setID(key);
                                    databaseReference.child(key).setValue(eventss);

                                    Toast.makeText(AddEventsActivity.this, "Success Key retention...", Toast.LENGTH_LONG).show();
                                    mProgress.setVisibility(View.INVISIBLE);
                                    backToProfile(mEventLocation, mEventName, mEventRating,mEventPrice,mEventDate,mEventContacts,mEventDescription,mEventTime, mEventType, sImage);
                                    etEventName.setText("");
                                    etLocation.setText("");
                                    etPrice.setText("");
                                    etEventType.setText("");
                                    etEventDate.setText("");
                                    etEventDescription.setText("");
                                    etEventTime.setText("");
                                    etEventContact.setText("");
                                    Picasso.get().load("null").placeholder(R.drawable.placeholder).into(eventImage);

                                    /*ImageView ivBack, eventImage;
        EditText etLocation, etEventName, etRating, etEventType, etPrice, etEventContact,etEventTime,etEventDescription,etEventDate;
        Button btnSave;
        TextView tvUpload;
        Uri image_uri;*/
                                }
                            });
                            result.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddEventsActivity.this, "Database Fail...", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                }
            });


        }
        else {
            Toast.makeText(this, "Image Url Missing", Toast.LENGTH_SHORT).show();
        }


    }

    private void backToProfile(String mEventLocation, String mEventName, String mEventRating, String mEventPrice, String mEventDate, String mEventContacts, String mEventDescription, String mEventTime, String mEventType, String sImage) {
    //startActivity(backToProfile);
    }

    /*private void backToProfile(String mEventLocation,String mEventName,String mEventRating,String mEventPrice,String mEventDate,String mEventContacts,String mEventDescription,String mEventTime,String  mEventType) {

        *//*Intent backIntent = new Intent(this, ProfileActivity.class);
        backIntent.putExtra("hotelLocation1",mEventLocation );
        backIntent.putExtra("hotelName1",mEventName );
        backIntent.putExtra("hotelRating1",mEventRating );
        backIntent.putExtra("hotelListTag1",mEventPrice );
        backIntent.putExtra("imageUri1",mEventDate);
        backIntent.putExtra("email1", mEventDate);
        backIntent.putExtra("phone1", mEventContacts);
        backIntent.putExtra("mapUrl1",mEventDescription );
        backIntent.putExtra("websiteUrl1", mEventTime);
        backIntent.putExtra("hotelPricePerHour1", mEventType);

        startActivity(backIntent);*//*
    }*/


    private void receiveEntries() {


        /*String mEventLocation, mEventName, mEventRating,mEventPrice,mEventDate,mEventContacts,mEventDescription,mEventTime, mEventType;*/
        mEventLocation = etLocation.getText().toString().trim();
        mEventName = etEventName.getText().toString().trim();
        mEventRating= etRating.getText().toString().trim();
        mEventPrice= etPrice.getText().toString().trim();
        mEventContacts= etEventContact.getText().toString().trim();
        mEventTime= etEventTime.getText().toString().trim();
        mEventDescription= etEventDescription.getText().toString().trim();
        mEventDate= etEventDate.getText().toString().trim();
        mEventType=etEventType.getText().toString().trim();

        checkFields();
    }

    private void checkFields() {
        if (etLocation.getText().toString().isEmpty()) {
            etLocation.setError("Location of The Event is required.");
        } else if (etEventName.getText().toString().isEmpty()) {
                etEventName.setError("Name of The Event is required.");
        } else if (etRating.getText().toString().isEmpty()) {
            etRating.setError("Rating of The Event is required.");
        } else if (etEventType.getText().toString().isEmpty()) {
            etEventType.setError("Type of The Event is required.");
        } else if (etPrice.getText().toString().isEmpty()) {
            etPrice.setError("Price  of The Event is required.");

        } else {

            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(AddEventsActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
            } else {

                if (isNetworkConnected()) {
                    uploadFile();


                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else {

                    Toast.makeText(AddEventsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_uri = data.getData();

            Picasso.get().load(image_uri).into(eventImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }
}