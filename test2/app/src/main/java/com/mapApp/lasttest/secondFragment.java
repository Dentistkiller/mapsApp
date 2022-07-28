package com.mapApp.lasttest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapApp.lasttest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class secondFragment extends Fragment {

    public secondFragment() {
    }

    //vars
    Button Save;
    private RadioGroup radioGroup;
    Switch Msystem;
    private RadioButton walking, driving, bicycle;

    String username;
    UserD details = new UserD();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uploadUserD = database.getReference("userD");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username= getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_second, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup=view.findViewById(R.id.myRadioGroup1);
        walking=view.findViewById(R.id.rbnWalking1);
        driving=view.findViewById(R.id.rbnDriving1);
        bicycle=view.findViewById(R.id.rbnBicycle1);
        Msystem=view.findViewById(R.id.sthMSystem1);
        Save=view.findViewById(R.id.btnSave1);
        uploadUserD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    UserD u=d.getValue(UserD.class);
                    if (u.getUsername().equals(username)){
                        details=u;
                        if (details.System.equals("metric")){
                            Msystem.setText("metric");
                            Msystem.setChecked(false);
                        }else{
                            Msystem.setChecked(true);
                            Msystem.setText("imperial");
                        }

                        if (details.getTransport() .equals("walking")) {
                            walking.setChecked(true);
                        } else if (details.getTransport().equals("driving")) {
                            driving.setChecked(true);
                        } else {
                            bicycle.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

                if (selectedId == walking.getId()) {
                    details.Transport = "walking";
                } else if (selectedId == driving.getId()) {
                    details.Transport = "driving";
                } else {
                    details.Transport = "cycling";
                }
                details.System = Msystem.getText().toString();
                uploadUserD.child(details.getKey()).setValue(details);
                Toast.makeText(getActivity(), "success , information has been changed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
