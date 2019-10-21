package com.kryptgames.health.fitwithfriends.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.kryptgames.health.fitwithfriends.R;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class PhoneAuthenticationActivity extends AppCompatActivity {

    private EditText mUserNumber;
    private PinEntryEditText mEntered_OTP;
    private Button mbutton;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static String cUserNumber,userNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private LinearLayout pinentryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        mAuth = FirebaseAuth.getInstance();
        mbutton = (Button) findViewById(R.id.fwf_button_otp);
        pinentryLayout=findViewById(R.id.fwf_layout_pinentry);

        mUserNumber = (EditText) findViewById(R.id.fwf_edittext_phonenumber);

        mUserNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if(s.length()>0) {
                    char a;
                    a = s.charAt(0);
                    if (a == '+') {
                        mUserNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                        cUserNumber = mUserNumber.getText().toString();


                    } else {
                        mUserNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                        userNumber = mUserNumber.getText().toString();
                        cUserNumber = "+" + "91" + userNumber;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });


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
                mEntered_OTP = (PinEntryEditText) findViewById(R.id.fwf_pinentryedittext_otp);

                mEntered_OTP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            mbutton.performClick();
                        }
                        return false;
                    }
                });
                pinentryLayout.setVisibility(View.VISIBLE);
                mbutton.setText("Go");
            }
        };
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cUserNumber.length()>10){
                    userNumber=cUserNumber.substring(3,13);
                }
                mUserNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                mUserNumber.setText(cUserNumber);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(cUserNumber, 60, TimeUnit.SECONDS, PhoneAuthenticationActivity.this, mCallbacks);
                mUserNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });
                mUserNumber.setEnabled(false);
                mUserNumber.setBackground(getResources().getDrawable(R.drawable.edittext_steelbackground));
                mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEntered_OTP = (PinEntryEditText) findViewById(R.id.fwf_pinentryedittext_otp);
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
                                        String tokenId= FirebaseInstanceId.getInstance().getToken();
                                        HashMap<String,Object> map=new HashMap<>();
                                        map.clear();
                                        map.put("token",tokenId);
                                        DatabaseReference database=FirebaseDatabase.getInstance().getReference();
                                        DatabaseReference ref=database.child("Profile").child(userNumber);
                                        ref.updateChildren(map).addOnSuccessListener((new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent homeIntent=new Intent(getApplicationContext(),HomeScreenActivity.class);
                                                startActivity(homeIntent);
                                                finish();
                                            }

                                        })).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });


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

