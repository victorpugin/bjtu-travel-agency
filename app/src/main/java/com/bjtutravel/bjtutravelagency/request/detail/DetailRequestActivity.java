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
    private Request mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.detail_request_activity);

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            mUserIsAdmin = extras.getBoolean(KEY_ADMIN);
            mRequest = (Request) extras.get("request");

            bindDataView();
        }

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
                    in.putExtras(getIntent());
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
        if (mRequest != null) {
            TextView userText = (TextView) findViewById(R.id.info_plan_user);
            TextView titleText = (TextView) findViewById(R.id.info_plan_title);
            TextView messageText = (TextView) findViewById(R.id.request_message);
            TextView dateText = (TextView)findViewById(R.id.info_plan_date);

            if (mUserIsAdmin) {
                userText.setText(mRequest.getUserName());
                userText.setVisibility(View.VISIBLE);
            } else {
                userText.setVisibility(View.GONE);
            }
            titleText.setText(mRequest.getTitle());
            messageText.setText(mRequest.getMessage());
            dateText.setText(mRequest.getDate());
        }
    }
}
