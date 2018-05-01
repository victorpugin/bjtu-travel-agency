package com.bjtutravel.bjtutravelagency.utils.service;

import android.util.Log;

import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstance extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseInstance";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String currentToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser firebaseUser = UtilFirebase.getFirebaseUser();

        if (firebaseUser != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(firebaseUser.getUid());

            if (currentToken != null)
                ref.child("fcmTokens").child(currentToken).setValue(true);
            else
                Log.i(TAG, "onTokenRefresh: token was null.");
            Log.d(TAG, "New firebase token : " + currentToken);
        }
    }
}
