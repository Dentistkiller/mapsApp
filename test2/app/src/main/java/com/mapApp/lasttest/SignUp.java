package com.mapApp.lasttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.mapApp.lasttest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail,etPassword;
    Button Save;
    String email,password,transport,system;
    private RadioGroup radioGroup;
    Switch Msystem;
    UserD userDetails=new UserD();
    private RadioButton walking, driving, bicycle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uploadUserD = database.getReference("userD");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        etEmail=findViewById(R.id.ettGmail1);
        radioGroup=findViewById(R.id.myRadioGroup);
        walking=findViewById(R.id.rbnWalking);
        driving=findViewById(R.id.rbnDriving);
        bicycle=findViewById(R.id.rbnBicycle);
        etPassword=findViewById(R.id.ettPassword1);
        Msystem=findViewById(R.id.sthMSystem);
        Save=findViewById(R.id.btnSave);

        Msystem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Msystem.isChecked()==false){
                    Msystem.setText("metric");
                }else{
                    Msystem.setText("imperial");
                }
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1 || etPassword.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Error a field was left blank", Toast.LENGTH_SHORT).show();
                }else{
                    if (selectedId == walking.getId()) {
                        userDetails.Transport = "walking";
                    } else if (selectedId == driving.getId()) {
                        userDetails.Transport = "driving";
                    } else {
                        userDetails.Transport = "cycling";
                    }
                    userDetails.System = Msystem.getText().toString();
                    email = etEmail.getText().toString();
                    email = email.replace(" ", "");
                    password = etPassword.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        userDetails.Username = email;
                                        String key = uploadUserD.child("userD").push().getKey();
                                        userDetails.Key = key;
                                        uploadUserD.child(key).setValue(userDetails);

                                         Intent intent = new Intent(SignUp.this, Hub.class);
                                        intent.putExtra("username", email);
                                         startActivity(intent);
                                    }
                                    else
                                        {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUp.this, "username or password is invalid", Toast.LENGTH_SHORT).show();
                                    }}
                            });
                }
            }
        });
    }
}
