package com.bjtutravel.bjtutravelagency.plan.detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bjtutravel.bjtutravelagency.plan.adapter.PlanRecyclerViewAdapter;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailPlanActivity extends AppCompatActivity {
    private static final String TAG = "DetailPlanActivity";

    public static final String KEY_ADMIN = "com.bjtutravel.bjtutravelagency.KEY_ADMIN";

    private boolean mUserIsAdmin = false;
    private InfoPlan mInfoPlan;

    private RecyclerView mRecyclerView;
    private PlanRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.detail_plan_activity);

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            mUserIsAdmin = extras.getBoolean(KEY_ADMIN);
            mInfoPlan = (InfoPlan) extras.get("infoPlan");

            bindDataView();
        }
    }

    // ACTION BAR
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Bind data class to view
    private void bindDataView() {
        if (mInfoPlan != null) {
            bindInfoPlanView();
            bindPlanView();
        }
    }

    // INFO PLAN VIEW
    private void bindInfoPlanView() {
        TextView userText = (TextView) findViewById(R.id.plan_user);
        TextView titleText = (TextView) findViewById(R.id.plan_title);
        TextView dateText = (TextView)findViewById(R.id.plan_date);

        if (mUserIsAdmin) {
            userText.setText(mInfoPlan.getUserName());
            userText.setVisibility(View.VISIBLE);
        } else {
            userText.setVisibility(View.GONE);
        }
        titleText.setText(mInfoPlan.getTitle());
        dateText.setText(mInfoPlan.getDate());
    }

    // PLAN VIEW
    private void bindPlanView() {
        createRecyclerView();

        // Get Firebase db and user
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userId = UtilFirebase.getFirebaseUserId();
        if (userId == null)
            return;

        // Get endpoint reference
        DatabaseReference ref = db.getReference("plans").child(mInfoPlan.getKey());

        // Get requests from db and set them in the view
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.resetList();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            ItemPlan itemPlan = data.getValue(ItemPlan.class);
                            adapter.addItemPlan(itemPlan);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getInfoPlan:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    private void createRecyclerView() {
        // Set the adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanRecyclerViewAdapter(false);
        mRecyclerView.setAdapter(adapter);
    }
}
