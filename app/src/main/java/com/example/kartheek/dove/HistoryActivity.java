package com.example.kartheek.dove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kartheek.dove.RecyclerView.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView mHistoryList = findViewById(R.id.rV_history);
        mHistoryList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryActivity.this);
        mHistoryList.setLayoutManager(layoutManager);

        HistoryAdapter mAdapter = new HistoryAdapter(20);
        mHistoryList.setAdapter(mAdapter);
    }
}
