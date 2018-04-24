package com.bjtutravel.bjtutravelagency.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UtilFirebase {
    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        FirebaseAuth auth = getFirebaseAuth();
        return auth.getCurrentUser();
    }
}
