package com.example.kartheek.dove.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartheek.dove.HistoryActivity;
import com.example.kartheek.dove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Developed By BlueberryBoy
 */

public class BaseListAdapter extends RecyclerView.Adapter<BaseListAdapter.BaseListViewHolder>{


    private String[] BaseNames;

    public BaseListAdapter(int nItems){
    }

    @Override
    public BaseListAdapter.BaseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseNames = parent.getResources().getStringArray(R.array.tripod_base_names);
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);

        return new BaseListAdapter.BaseListViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(BaseListAdapter.BaseListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    class BaseListViewHolder extends RecyclerView.ViewHolder{

        TextView mBaseName, mPersonName, mTimeStamp;
        Context context;

        BaseListViewHolder(View itemView,final Context context){
            super(itemView);

            this.context = context;

            mBaseName = itemView.findViewById(R.id.item_name);
            mPersonName = itemView.findViewById(R.id.person_name);
            mTimeStamp = itemView.findViewById(R.id.time_stamp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent=new Intent(context,HistoryActivity.class);
                    intent.putExtra("type","Base");
                    intent.putExtra("number",position);
                    context.startActivity(intent);
                }
            });
        }

        void bind(final int position){
            mBaseName.setText(BaseNames[position]);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.child("Base"+position).child("perName").getValue(String.class);
                    mTimeStamp.setText(dataSnapshot.child("Base"+position).child("time").getValue(String.class));
                    mPersonName.setText(dataSnapshot.child("Users").child(value).child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
