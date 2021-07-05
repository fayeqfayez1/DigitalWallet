package com.app.network.firebase.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseReferances {

    public static final String USERS_REF = "users";
    public static final String CREDIT_CARD_REF = "CreditCard ";

    private static FirebaseReferances instance;

    public static FirebaseReferances getInstance() {
        if (instance == null)
            instance = new FirebaseReferances();
        return instance;
    }

    public static FirebaseAuth getAuthReference() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getAuthCurrentUserReference() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DatabaseReference getDatabaseReference() {
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference();
        scoresRef.keepSynced(true);
        return scoresRef;
    }

    public static DatabaseReference getUsersListReference() {
        return getDatabaseReference()
                .child(USERS_REF);
    }

    public static DatabaseReference getCreditCardListReference() {
        return getDatabaseReference().child(CREDIT_CARD_REF);
    }

    public static DatabaseReference getCreditCardReference(String userId) {
        return getCreditCardListReference().child(userId).push();
    }

    public static DatabaseReference getUserReference(String userId) {
        return getUsersListReference().child(userId);
    }

    public static String getUserId() {
        return (getAuthReference().getCurrentUser() != null) ? getAuthReference().getCurrentUser().getUid() : "";
    }
}
