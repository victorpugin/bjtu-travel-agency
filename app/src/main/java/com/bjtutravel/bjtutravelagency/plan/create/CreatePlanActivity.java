package com.bjtutravel.bjtutravelagency.plan.create;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.plan.create.adapter.PlanRecyclerViewAdapter;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePlanActivity extends AppCompatActivity {

    private PlanRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.create_plan_activity);

        createListView();
    }

    // LIST VIEW
    private void createListView() {
        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
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
        inflater.inflate(R.menu.menu_send_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            savePlan();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // FIREBASE SAVE
    private void savePlan() {
        String userId = UtilFirebase.getFirebaseUserId();
        if (userId == null)
            return;
    }
}
