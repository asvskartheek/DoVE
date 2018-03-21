package com.example.kartheek.dove;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //This part is for adding Name
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String uid = mAuth.getCurrentUser().getUid();
                    String name = dataSnapshot.child("Users").child(uid).child("name").getValue(String.class);
                    String text = "Hi! " + name;
                    TextView mText = findViewById(R.id.tv_camera);
                    mText.setText(text);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        //Tabs
        mViewPager = findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(MainActivity.this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }else {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

                //Change Camers to Items
                final DatabaseReference myRef = database.getReference().child(result.getContents());

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false;
                        if (!flag){
                            for (int i = 19;i>1;--i){
                                String value = "his"+i;
                                int prev_int = i-1;
                                //TODO: Run this only once
                                String prev_hehe = dataSnapshot.child(prev).getValue(String.class);
                                String hehe = dataSnapshot.child(value).getValue(String.class);
                                myRef.child(value).setValue(prev_hehe);
                            }
                            String prev_hehe = dataSnapshot.child("perName").getValue(String.class);
                            myRef.child("his1").setValue(prev_hehe);
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                myRef.child("perName").setValue(mCurrentUser.getUid());
                myRef.child("time").setValue( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                Toast.makeText(MainActivity.this,result.getContents(),Toast.LENGTH_LONG).show();
            }
        }

    }
}

