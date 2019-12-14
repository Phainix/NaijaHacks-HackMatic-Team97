package com.hackmatic.edmatic.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackmatic.edmatic.DataStore;

import java.util.HashMap;
import java.util.Map;

public class AppActivity {
    private static final String TAG = AppActivity.class.getSimpleName();

    public static void save(String desc) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> activity = new HashMap<>();
        activity.put("activity", desc);
        activity.put("user_id", auth.getCurrentUser().getUid());
        activity.put("created_on", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        db.collection(DataStore.ACTIVITIES)
                .add(activity);
    }

}
