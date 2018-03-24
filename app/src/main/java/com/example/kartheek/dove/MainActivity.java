package com.example.kartheek.dove;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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


public class MainActivity extends AppCompatActivity {

    final private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final private FirebaseUser mCurrentUser = mAuth.getCurrentUser();
    final private String uid = mCurrentUser != null ? mCurrentUser.getUid() : null;
    final private DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tabs
        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);

        ViewPager mViewPager = findViewById(R.id.main_tabPager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(MainActivity.this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }else {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ItemData old_data = dataSnapshot.child(result.getContents()).getValue(ItemData.class);
                        String resultName = result.getContents();
                        String[] part = resultName.split("(?<=\\D)(?=\\d)");
                        int index = Integer.parseInt(part[1]);
                        String[] req_arr = getResources().getStringArray(R.array.camera_names);
                        switch (part[0]){
                            case "Camera":
                                req_arr = getResources().getStringArray(R.array.camera_names);
                                break;
                            case "Base":
                            case "Tripod":
                                req_arr = getResources().getStringArray(R.array.tripod_base_names);
                               break;
                            case "Memory":
                                req_arr = getResources().getStringArray(R.array.memory_names);
                                break;
                        }
                        String item_name = req_arr[index];
                        if(!(old_data != null && old_data.perName.equals(uid))) {
                            updateData(old_data, result.getContents());
                            Toast.makeText(MainActivity.this,item_name,Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"You already have " + item_name + " rey CHUTIYE!!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void updateData(ItemData old_data,String item){
        ItemData new_data = new ItemData(old_data, uid);
        mDatabase.child(item).setValue(new_data);
    }


}

