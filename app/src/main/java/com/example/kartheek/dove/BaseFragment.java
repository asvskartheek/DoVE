package com.example.kartheek.dove;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartheek.dove.RecyclerView.BaseListAdapter;
import com.example.kartheek.dove.RecyclerView.CameraListAdapter;
import com.google.zxing.integration.android.IntentIntegrator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //This part is for scanning QR Code
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        FloatingActionButton mTakeCamBtn = view.findViewById(R.id.take_base_btn);
        mTakeCamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator( (Activity) getContext());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("SCAN");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        RecyclerView mCameraList = view.findViewById(R.id.rV_base);

        mCameraList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCameraList.setLayoutManager(layoutManager);


        //Change the adapter according to item
        BaseListAdapter mAdapter = new BaseListAdapter(2);
        mCameraList.setAdapter(mAdapter);

        return view;
    }

}
