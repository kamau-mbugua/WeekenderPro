package com.example.weekenderpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EventDetail extends AppCompatActivity {

    private static final int CALL_PERMISSION = 30;
    ImageView iveventlImage ;
    TextView tvMapUrlLoaccation, tvEventRatings,tvEventTime,tvEventDate, tvEventLocation, tvEventName,tvEventype,tvEventPrice,
    tvEventContacts,tvEventDescription;

    String mtvEventRatings,mtvEventTime,mtvEventDate, mtvEventLocation, mtvEventName,mtvEventype,mtvEventPrice,
            mtvEventContacts,mtvEventDescription, mEventImage;
    /*String mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
            mhotelNames,mtagsList, mhotelImage, mhotelPrice, mhotelMapUrl,mhotelWebsite;*/
    WebView tvHotelDirection;

    private void initializeWidgets(){
        iveventlImage        = findViewById(R.id.ivEventImage);
        tvEventRatings           = findViewById(R.id.tvEventRatings);
        tvEventTime        = findViewById(R.id.tvEventTime);
        tvEventDate        = findViewById(R.id.tvEventDate);
        tvHotelDirection    = findViewById(R.id.googleMapView);
        tvEventLocation     = findViewById(R.id.tvEventLocation);
        tvEventName        = findViewById(R.id.tvEventName);
        tvEventype          = findViewById(R.id.tvEventype);
        tvEventPrice        = findViewById(R.id.tvEventPrice);
        tvEventContacts  = findViewById(R.id.tvEventContacts);
        tvEventDescription      = findViewById(R.id.tvEventDescription);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        initializeWidgets();
        recieveIntents();

        tvEventContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoning();

            }
        });
    }

    private void phoning() {
        if (Build.VERSION.SDK_INT >= 23){
            if (checkedPermission()){
//                Permission Already Granted
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + mtvEventContacts));
                startActivity(phoneIntent);
            }
            else {
                requestPermission();
            }
        }
        if (checkedPermission()){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + mtvEventContacts));
            startActivity(phoneIntent);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(EventDetail.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
    }

    private boolean checkedPermission() {
        int callPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return callPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void recieveIntents() {
        if (getIntent().hasExtra("eventLocation1")
                && getIntent().hasExtra("eventName1")
                && getIntent().hasExtra("eventRating1")
                && getIntent().hasExtra("eventType1")
                && getIntent().hasExtra("imageUri1")
                && getIntent().hasExtra("eventPrice1")
                && getIntent().hasExtra("eventContacts1")
                && getIntent().hasExtra("eventDate1")
                && getIntent().hasExtra("evenTime1")
                && getIntent().hasExtra("eventDescription1")) {

           /* String mtvEventRatings,mtvEventTime,mtvEventDate, mtvEventLocation, mtvEventName,mtvEventype,mtvEventPrice,
                    mtvEventContacts,mtvEventDescription;*/

            mtvEventLocation = getIntent().getStringExtra("eventLocation1");
            mtvEventName = getIntent().getStringExtra("eventName1");
            mtvEventRatings = getIntent().getStringExtra("eventRating1");
            mtvEventype = getIntent().getStringExtra("eventType1");
            mEventImage = getIntent().getStringExtra("imageUri1");
            mtvEventPrice = getIntent().getStringExtra("eventPrice1");
            mtvEventContacts = getIntent().getStringExtra("eventContacts1");
            mtvEventDate = getIntent().getStringExtra("eventDate1");
            mtvEventTime = getIntent().getStringExtra("evenTime1");
            mtvEventDescription = getIntent().getStringExtra("eventDescription1");
           

            provision(mtvEventLocation,
                    mtvEventName,
                    mtvEventRatings,
                    mtvEventype,
                    mEventImage,
                    mtvEventTime,
                    mtvEventDate,
                    mtvEventContacts,
                    mtvEventDescription,
                    mtvEventPrice);
        }
    }

    private void provision(String mtvEventLocation, String mtvEventName, String mtvEventRatings, String mtvEventype, String mEventImage, String mtvEventTime, String mtvEventDate, String mtvEventContacts, String mtvEventDescription, String mtvEventPrice) {


        tvEventRatings.setText(mtvEventRatings);
        tvEventTime.setText(mtvEventTime);
        tvEventDate.setText(mtvEventDate);
        tvEventLocation.setText(mtvEventLocation);
        tvEventName.setText(mtvEventName);
        tvEventype.setText(mtvEventype);
        tvEventPrice.setText(mtvEventPrice);
        tvEventContacts.setText(mtvEventContacts);
        tvEventDescription.setText(mtvEventDescription);

        Picasso.get().load(mEventImage).fit().placeholder(R.drawable.placeholder).into(iveventlImage);
        Toast.makeText(this, "Url : " + mEventImage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission For Calling has been Accepted...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission For Calling has been Denied...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}