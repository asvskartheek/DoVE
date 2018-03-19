package com.example.kartheek.dove;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartheek.dove.RecyclerView.CameraListAdapter;
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

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //This part is for adding Name
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String uid = mAuth.getCurrentUser().getUid();
                    String name = dataSnapshot.child("Users").child(uid).child("name").getValue(String.class);
                    String text = "Hi! "+ name;
                    TextView mText = findViewById(R.id.tv_camera);
                    mText.setText(text);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        //This part is for scanning QR Code
        FloatingActionButton mTakeCamBtn = findViewById(R.id.take_camera_btn);
        mTakeCamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("SCAN");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        RecyclerView mCameraList = findViewById(R.id.rV_camera);

        mCameraList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCameraList.setLayoutManager(layoutManager);


        //Change the adapter according to item
        CameraListAdapter mAdapter = new CameraListAdapter(2);
        mCameraList.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(MainActivity.this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }else {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

                //Change Camers to Items
                DatabaseReference myRef = database.getReference().child("Cameras").child(result.getContents());

                myRef.child("perName").setValue(mCurrentUser.getUid());
                myRef.child("time").setValue( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                Toast.makeText(MainActivity.this,result.getContents(),Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
