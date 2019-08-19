package com.kryptgames.health.fitwithfriends.activity;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kryptgames.health.fitwithfriends.Profile;
import com.kryptgames.health.fitwithfriends.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfile extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int PICK_IMAGE = 1;

    private String valid_email;

    TextView mDisplayDate;

    EditText edName,edLastName;
    Spinner spinnerGender;

    Button save;
    CircleImageView profilePic, edit;
    DatabaseReference databaseReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    Spinner heightSpinner;
    Spinner weightSpinner;

    Uri imageUri;
    EditText emailAddress;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilizeUI();

        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);



        profilePic = findViewById(R.id.profilePic);
        emailAddress = findViewById(R.id.emailAddress);

        edit = findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }


        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Profile");


        Query myTopPostsQuery = FirebaseDatabase.getInstance().getReference("Profile");


        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    Profile profile = postSnapshot.getValue(Profile.class);
                    Log.e("Get Data", profile.getName());
                    Log.e("Get Data", profile.getGenre());
                    Log.e("Get Data", profile.getDob());
                    Log.e("Get Data", profile.getHeight());
                    Log.e("Get Data", profile.getWeight());
                    Log.e("Get Data", profile.getLastName());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mDisplayDate = (TextView) findViewById(R.id.mDisplayDate);

        edName = findViewById(R.id.edName);
        edLastName = findViewById(R.id.edLastName);

        edName.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });


        edLastName.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });





        spinnerGender = findViewById(R.id.spinnerGender);

        save = findViewById(R.id.save);
        weightSpinner = findViewById(R.id.weightSpinner);

        final List<String> weight = new ArrayList<String>();
        weight.add(0, "Weight");


        for (int i = 70; i < 200; i++) {
            String item = String.valueOf(i + " " + "Kgs");
            weight.add(item);


        }

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weight);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);



        heightSpinner = findViewById(R.id.heightSpinner);

        final List<String> list = new ArrayList<String>();
        list.add(0,"Height");

        for(int i = 100; i < 250; i++) {
            String item = String.valueOf(i + " " +"Cms");
            list.add(item);
            list.indexOf("150 Cms");

        }

        ArrayAdapter<String> meterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        meterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(meterAdapter);


        List<String> Gender = new ArrayList<>();
        Gender.add(0, "Gender");
        Gender.add("Male");
        Gender.add("Female");
        Gender.add("Others");


        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(dataAdapter);



        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Gender")) {

                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO Auto-generated method stub

            }
        });






        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileCreated();


            }
        });





        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DATE);

                DatePickerDialog dialog = new DatePickerDialog(CreateProfile.this, android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" +dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" +year;
                mDisplayDate.setText(date);

            }
        };
    }

    private void initilizeUI() {


        emailAddress = (EditText) findViewById(R.id.emailAddress);

        emailAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                Is_Valid_Email(emailAddress); // pass your EditText Obj here.
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else {
                    valid_email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        });



    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!= null) {
            Toast.makeText(CreateProfile.this, "HOLA" + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CreateProfile.this, "SORRY I DONT KNOW U YET"  , Toast.LENGTH_SHORT).show();

        }

    }


    private void profileCreated() {

        String name = edName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();
        String genre = spinnerGender.getSelectedItem().toString();
        String dob = mDisplayDate.getText().toString();
        String height = heightSpinner.getSelectedItem().toString();
        String weight = weightSpinner.getSelectedItem().toString();
        String email = emailAddress.getText().toString();


        if (!TextUtils.isEmpty(name)) {

            String key = databaseReference.push().getKey();

            Profile profile = new Profile(name, lastName, genre, dob,height, weight, email);

            databaseReference.child(key).setValue(profile);

            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No Field should be Empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){

            imageUri = data.getData();
        } try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            profilePic.setImageBitmap(bitmap);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



}
