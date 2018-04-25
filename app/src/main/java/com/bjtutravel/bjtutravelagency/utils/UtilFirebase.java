package com.bjtutravel.bjtutravelagency.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UtilFirebase {
    private static final String TAG = "UtilFirebase";

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        FirebaseAuth auth = getFirebaseAuth();
        return auth.getCurrentUser();
    }

    public static String getFirebaseUserId() {
        FirebaseUser firebaseUser = UtilFirebase.getFirebaseUser();
        if (firebaseUser == null)
            return null;

        return firebaseUser.getUid();
    }
}
