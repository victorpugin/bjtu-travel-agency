package com.bjtutravel.bjtutravelagency.plan.create;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bjtutravel.bjtutravelagency.models.Request;
import com.bjtutravel.bjtutravelagency.plan.adapter.PlanRecyclerViewAdapter;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.BaseViewHolder;
import com.bjtutravel.bjtutravelagency.plan.create.dialog.ItemPlanTypeDialogFragment;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.bjtutravel.bjtutravelagency.utils.UtilSnackbar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreatePlanActivity extends AppCompatActivity
        implements ItemPlanTypeDialogFragment.ItemPlanTypeDialogListener {
    private static final String TAG = "CreatePlanActivity";

    private Request mRequest;
    private RecyclerView mRecyclerView;
    private PlanRecyclerViewAdapter adapter;

    private boolean mActionProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.create_plan_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            mRequest = (Request) extras.get("request");

        createListView();
    }

    // LIST VIEW
    private void createListView() {
        // Set the adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanRecyclerViewAdapter(this, true);
        mRecyclerView.setAdapter(adapter);
    }

    // ACTION BAR
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionProcessing) return false;

        if (item.getItemId() == R.id.action_send) {
            mActionProcessing = true;
            savePlan();
        } else if (item.getItemId() == R.id.action_add_plan_item) {
            mActionProcessing = true;
            // Get wanted plan item with a dialog
            new ItemPlanTypeDialogFragment()
                    .show(getSupportFragmentManager(), "ItemPlanTypeDialog");
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    // ADD PLAN ITEM
    private void addPlanItem(int type) {
        // obtain new ItemPlan object
        ItemPlan itemPlan = new ItemPlan();
        itemPlan.setType(type);

        // add new item in view and notify
        adapter.addItemPlan(itemPlan);
    }

    // ITEM PLAN TYPE DIALOG LISTENER
    @Override
    public void onDialogPositiveClick(int typeIndex) {
        addPlanItem(typeIndex + 1);
    }

    @Override
    public void onDialogDismiss() {
        mActionProcessing = false;
    }

    // FIREBASE SAVE
    private void savePlan() {
        // Get Data
        EditText titleEdtText = (EditText)findViewById(R.id.edit_plan_title);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CHINESE);

        String dateString = format.format(date);

        // Create InfoPlan object map
        InfoPlan infoPlan = new InfoPlan();
        infoPlan.setUserId(mRequest.getUserId());
        infoPlan.setUserName(mRequest.getUserName());
        infoPlan.setTitle(titleEdtText.getText().toString());
        infoPlan.setDate(dateString);
        infoPlan.setStaffName(UtilFirebase.getFirebaseUser().getDisplayName());

        // Create new entry in Firebase db and get key
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        // Update children to add new entry
        Map<String, Object> childUpdates = new HashMap<>();

        // update info plan entry
        childUpdates.put("/info-plans/" + mRequest.getKey(), infoPlan.toMap());
        childUpdates.put("/user-info-plans/" + mRequest.getUserId() + "/" + mRequest.getKey(), infoPlan.toMap());
        // add first node of plans with info plan (to have info-plan as header of list view)
        String itemKey = db.child("plans").child(mRequest.getKey()).push().getKey();
        String endpoint = "/plans/" + mRequest.getKey() + "/" + itemKey;
        ItemPlan itemInfoPlan = new ItemPlan();
        itemInfoPlan.setType(ItemPlan.TYPE_INFO_PLAN);
        childUpdates.put(endpoint, itemInfoPlan.toMap());

        // update item plans
        for (ItemPlan itemPlan : adapter.getItemPlanList()) {
            itemKey = db.child("plans").child(mRequest.getKey()).push().getKey();
            endpoint = "/plans/" + mRequest.getKey() + "/" + itemKey;
            childUpdates.put(endpoint, itemPlan.toMap());
        }

        db.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    UtilSnackbar.showSnakbar(
                            findViewById(android.R.id.content),
                            getResources().getString(R.string.title));
                    Log.d(TAG, "SavePlan success");
                    finish();
                } else {
                    UtilSnackbar.showSnakbar(
                            findViewById(android.R.id.content),
                            databaseError.getMessage());
                    Log.e(TAG, "SavePlan failed: " + databaseError.getMessage());
                    mActionProcessing = false;
                }
            }
        });
    }
}
