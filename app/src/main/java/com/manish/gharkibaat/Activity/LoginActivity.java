package com.manish.gharkibaat.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manish.gharkibaat.R;
import com.manish.gharkibaat.Utility.AppSharedPreference;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.sendcode)Button mSendBtn;
    @BindView(R.id.ll1)LinearLayout mPhoneLayout;
    @BindView(R.id.ll2)LinearLayout mCodeLayout;

    @BindView(R.id.phoneno)EditText mPhoneText;
    @BindView(R.id.verifycode)EditText mCodeText;

    @BindView(R.id.phoneno_progress)ProgressBar mPhoneProgress;
    @BindView(R.id.verifycode_progress)ProgressBar mCodeProgress;


    private int SendByn_Type = 0;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private String mVerificationId;
//    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;

    public String TAG = "LoginAct";

    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ButterKnife.bind(this);

        db = FirebaseFirestore.getInstance();

//        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
//
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                    showToastMessage("INvalid Req");
//
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                    showToastMessage("SMS Quota exceeded");
//                }
//                else {
//                    Log.d("Authh",e.getMessage());
//                }
//            }
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//
//                mVerificationId = verificationId;
////                mResendToken = token;
//
//                SendByn_Type = 1;
//
//                mPhoneProgress.setVisibility(View.INVISIBLE);
//                mCodeLayout.setVisibility(View.VISIBLE);
//
//                mSendBtn.setText("Verify Code");
//                mSendBtn.setEnabled(true);
//            }
//        };

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @OnClick(R.id.sendcode)
    void sendCode(){
        if (SendByn_Type == 0) {

            mPhoneProgress.setVisibility(View.VISIBLE);
            mPhoneText.setEnabled(false);
            mSendBtn.setEnabled(false);

            String PhoneNo = "+91" + mPhoneText.getText().toString();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    PhoneNo,
                    60,
                    TimeUnit.SECONDS,
                    LoginActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                            Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                            signInWithPhoneAuthCredential(phoneAuthCredential);
                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                // Invalid request
                                showToastMessage("INvalid Req");

                            } else if (e instanceof FirebaseTooManyRequestsException) {
                                // The SMS quota for the project has been exceeded
                                showToastMessage("SMS Quota exceeded");
                            }
                            else {
                                Log.d("Authh",e.getMessage());
                                showSnackBar(e.getMessage());
                            }
                            showSnackBar(e.getMessage()+"**");
                        }

                        @Override
                        public void onCodeSent(String verificationId,
                                               PhoneAuthProvider.ForceResendingToken token) {

                            mVerificationId = verificationId;
//                mResendToken = token;

                            SendByn_Type = 1;

                            mPhoneProgress.setVisibility(View.INVISIBLE);
                            mCodeLayout.setVisibility(View.VISIBLE);

                            mSendBtn.setText("Verify Code");
                            mSendBtn.setEnabled(true);
                        }
                    }

            );
        }else {

            mSendBtn.setEnabled(false);
            mCodeLayout.setVisibility(View.VISIBLE);

            String VerificationCode = mCodeText.getText().toString();

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,VerificationCode);
            signInWithPhoneAuthCredential(credential);

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();

//                            db.collection("")

                            AppSharedPreference.getInstance(LoginActivity.this).setLoggedIn(true);

                            Intent mainIntent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(mainIntent);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                showToastMessage("Invalid Code");
                            }
                        }
                    }
                });
    }


}
