package com.bjtutravel.bjtutravelagency.request.create;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.Request;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.bjtutravel.bjtutravelagency.utils.UtilSnackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        inflater.inflate(R.menu.menu_create_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            saveRequest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // FIREBASE SAVE
    private void saveRequest() {
        FirebaseUser firebaseUser = UtilFirebase.getFirebaseUser();
        if (firebaseUser == null)
            return;

        String userId = firebaseUser.getUid();

        // Get Data
        EditText titleEdtText = (EditText)findViewById(R.id.edit_request_title);
        EditText messageEdtText = (EditText)findViewById(R.id.edit_request_message);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CHINESE);

        String dateString = format.format(date);

        // Create Request object map
        Request request = new Request();
        request.setTitle(titleEdtText.getText().toString());
        request.setMessage(messageEdtText.getText().toString());
        request.setDate(dateString);
        Map<String, Object> requestValues = request.toMap();

        // Create new entry in Firebase db and get key
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String key = db.child("requests").push().getKey();

        // Update children from request list to add new entry
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/requests/" + key, requestValues);
        childUpdates.put("/user-requests/" + userId + "/" + key, requestValues);

        db.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    UtilSnackbar.showSnakbar(
                            findViewById(android.R.id.content),
                            getResources().getString(R.string.title));
                    finish();
                } else {
                    UtilSnackbar.showSnakbar(
                            findViewById(android.R.id.content),
                            databaseError.getMessage());
                }
            }
        });
    }
}
