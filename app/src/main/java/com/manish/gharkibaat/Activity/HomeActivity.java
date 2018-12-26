package com.manish.gharkibaat.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.manish.gharkibaat.R;
import com.manish.gharkibaat.Utility.AppSharedPreference;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseFirestore db;
    DocumentReference docRef ;

    ListenerRegistration listenerRegistration;

    final String TAG = "HomeAct";

    @BindView(R.id.available_tv) TextView avaiableCount;
    @BindView(R.id.availability_switch) Switch availabilitySwitch;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportToolbar();
        setToolbarLeftIconClickListener();

        initDrawer();

        setTitle("Welcomee");

        bottomNavigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            gotoLoginAct();
        }

        if(db != null && mAuth != null && currentUser != null) {
            docRef = db.collection("availability").document(mAuth.getCurrentUser().getUid());
        }
        mUser = mAuth.getCurrentUser();

        AppSharedPreference.getInstance(this).setUserId(mAuth.getUid());

//        if(!AppSharedPreference.getInstance(this).isLoggedIn()){
//            finish();
//        }

        availabilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Map<String, Object> userId = new HashMap<>();
                    userId.put("userUID", mAuth.getCurrentUser().getUid());

                    db.collection("availability").document(mAuth.getCurrentUser().getUid()).set(userId).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.collection("availability").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int count = 0;
                                    for(DocumentSnapshot snapshot : task.getResult()){
                                     count++;
                                    }
                                    avaiableCount.setText(count + " Available");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToastMessage("cannot fetch");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToastMessage("add Failed");
                        }
                    });
                }else {
                    db.collection("availability").document(mAuth.getCurrentUser().getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.collection("availability").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int count = 0;
                                    for(DocumentSnapshot snapshot : task.getResult()){
                                        count++;
                                    }
                                    avaiableCount.setText(count + " Available");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToastMessage("cannot fetch2");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToastMessage("cannot fetch2");
                        }
                    });
                }
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.profile:
                    startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
            return false;
        }
    } ;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

//        FirebaseAuth.getInstance().signOut();

        if (currentUser == null) {

            gotoLoginAct();
        }
    }

    int count = 0;

    @Override
    public void onBackPressed() {
        ++count;
        if (count <= 1) {
            showSnackBar("Press Twice to Exit");
        } else {
            super.onBackPressed();
        }
    }

    private void gotoLoginAct() {
        Intent AuthIntent = new Intent(this, LoginActivity.class);
        startActivity(AuthIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Query query = db.collection("availability");
        listenerRegistration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (queryDocumentSnapshots != null ) {

                            avaiableCount.setText(queryDocumentSnapshots.getDocuments().size() + " Available");


                        } else {
                            Log.d(TAG, "Current data: null");
                        }

                    }
                }
        );

    }

    @Override
    protected void onPause() {
        super.onPause();

        listenerRegistration.remove();

    }
}
