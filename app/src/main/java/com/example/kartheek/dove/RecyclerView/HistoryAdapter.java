package com.example.kartheek.dove.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartheek.dove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by blueberryboy on 21/3/18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static int mNoOfItems;

    public HistoryAdapter(int nItems){
        mNoOfItems = nItems;
    }

    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item,parent,false);

        return new HistoryAdapter.HistoryViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView mPersonName;
        Context context;

        HistoryViewHolder(View itemView,Context context){
            super(itemView);

            this.context = context;
            mPersonName = itemView.findViewById(R.id.history_person_name);
        }

        void bind(final int position){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            Intent intent = ((Activity) context).getIntent();
            final String type = intent.getStringExtra("type");
            final int number = intent.getIntExtra("number",0);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int position_sudo = position+1;
                    mPersonName.setText(dataSnapshot.child(type+number).child("his"+position_sudo).getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
