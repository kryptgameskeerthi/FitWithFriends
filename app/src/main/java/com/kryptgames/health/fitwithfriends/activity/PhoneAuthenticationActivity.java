package com.kryptgames.health.fitwithfriends.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kryptgames.health.fitwithfriends.R;

import java.util.concurrent.TimeUnit;


public class PhoneAuthenticationActivity extends AppCompatActivity {

    private EditText mUserNumber;
    private EditText mEntered_OTP;
    private Button mbutton;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static String userNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        mAuth = FirebaseAuth.getInstance();
        mbutton = (Button) findViewById(R.id.fwf_button_otp);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),"Service error,please try again after short time",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                Toast.makeText(PhoneAuthenticationActivity.this, "Otp sent successfully", Toast.LENGTH_SHORT).show();
                mEntered_OTP = (EditText) findViewById(R.id.fwf_edittext_otp);
                mEntered_OTP.setVisibility(View.VISIBLE);
                mbutton.setText("Go");

            }
        };
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserNumber = (EditText) findViewById(R.id.fwf_edittext_phonenumber);
                userNumber = mUserNumber.getText().toString();
                String number = "+" + "91" + userNumber;
                PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, PhoneAuthenticationActivity.this, mCallbacks);

                mUserNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });
                mUserNumber.setText("+91" + userNumber);
                mUserNumber.setEnabled(false);
                mUserNumber.setBackground(getResources().getDrawable(R.drawable.edittext_steelbackground));

                mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEntered_OTP = (EditText) findViewById(R.id.fwf_edittext_otp);
                        String entered_OTP = mEntered_OTP.getText().toString();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, entered_OTP);
                        signInWithPhoneAuthCredential(credential);
                    }
                });

            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                            DatabaseReference ref=reference.child("Profile").child(userNumber);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Intent homeIntent=new Intent(getApplicationContext(),HomeScreenActivity.class);
                                        startActivity(homeIntent);
                                        finish();
                                    }

                                    else {
                                        Intent profileIntent = new Intent(PhoneAuthenticationActivity.this, CreateProfileActivity.class);
                                        profileIntent.putExtra("number",userNumber);
                                        startActivity(profileIntent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "You have entered incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static String getNumber(){
        String a=userNumber;
        return a;
    }

}

