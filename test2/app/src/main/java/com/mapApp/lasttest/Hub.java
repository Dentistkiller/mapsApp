package com.mapApp.lasttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.mapApp.lasttest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Hub extends AppCompatActivity {
    firstFragment frag1;
    secondFragment frag2;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        Bundle extras = getIntent().getExtras();
        username=extras.getString("username");
        //frags
        frag1= new firstFragment();
        frag2= new secondFragment();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.map);
        Bundle bundles = new Bundle();
        bundles.putString("username", username);
        //frag Manage
        frag2.setArguments(bundles);
        frag1.setArguments(bundles);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flfragment, frag1);
        transaction.addToBackStack(null);
        transaction.commit();
        //frag 1
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//change frag
                switch ((menuItem.getItemId())){
                    case  R.id.map:
                        final FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    //frag1
                        transaction2.replace(R.id.flfragment, frag1);
                        transaction2.addToBackStack(null);
                        transaction2.commit();
                        break;
                    case  R.id.setting:
                        final FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        //frag2
                        transaction1.replace(R.id.flfragment, frag2);
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        break;
                }
                return false;
            }
        });
    }
}
