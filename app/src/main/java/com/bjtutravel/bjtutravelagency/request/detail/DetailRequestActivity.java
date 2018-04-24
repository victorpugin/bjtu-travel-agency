package com.bjtutravel.bjtutravelagency.request.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.create_request_activity);

        // Get Request and bind to view
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            Request request = (Request) extras.get("request");
            if (request != null) {
                TextView titleText = (TextView) findViewById(R.id.request_title);
                TextView messageText = (TextView) findViewById(R.id.request_message);
                TextView dateText = (TextView)findViewById(R.id.request_date);

                titleText.setText(request.getTitle());
                messageText.setText(request.getMessage());
                dateText.setText(request.getDate());
            }
        }
    }

    // ACTION BAR
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
