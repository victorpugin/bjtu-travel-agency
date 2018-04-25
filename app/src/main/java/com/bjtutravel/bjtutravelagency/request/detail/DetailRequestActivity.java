package com.bjtutravel.bjtutravelagency.request.detail;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.Request;
import com.bjtutravel.bjtutravelagency.plan.create.CreatePlanActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailRequestActivity extends AppCompatActivity {
    private static final String TAG = "DetailRequestActivity";

    public static final String KEY_ADMIN = "com.bjtutravel.bjtutravelagency.KEY_ADMIN";

    private boolean mUserIsAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.detail_request_activity);

        bindDataView();

        setFloatingButton();
    }

    // ACTION BAR
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // FLOATING BUTTON
    private void setFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (mUserIsAdmin) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(DetailRequestActivity.this, CreatePlanActivity.class);
                    startActivity(in);
                }
            });
        } else {
            fab.hide();
        }
    }

    // Bind data class to view
    private void bindDataView() {
        // Get Request and bind to view
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            Request request = (Request) extras.get("request");
            mUserIsAdmin = extras.getBoolean(KEY_ADMIN);
            if (request != null) {
                TextView userText = (TextView) findViewById(R.id.request_user);
                TextView titleText = (TextView) findViewById(R.id.request_title);
                TextView messageText = (TextView) findViewById(R.id.request_message);
                TextView dateText = (TextView)findViewById(R.id.request_date);

                if (mUserIsAdmin) {
                    userText.setText(request.getUserName());
                    userText.setVisibility(View.VISIBLE);
                } else {
                    userText.setVisibility(View.GONE);
                }
                titleText.setText(request.getTitle());
                messageText.setText(request.getMessage());
                dateText.setText(request.getDate());
            }
        }
    }
}
