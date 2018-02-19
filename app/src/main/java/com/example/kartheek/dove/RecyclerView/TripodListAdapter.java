package com.example.kartheek.dove.RecyclerView;

import android.content.Context;
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
 * Created by kartheek on 17/2/18.
 */

    public class TripodListAdapter extends RecyclerView.Adapter<TripodListAdapter.TripodListViewHolder>{



        private static int mNoOfItems;
        private String[] tripodNames;

        public TripodListAdapter(int nItems){ mNoOfItems = nItems; }

        @Override
        public com.example.kartheek.dove.RecyclerView.TripodListAdapter.TripodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            tripodNames = parent.getResources().getStringArray(R.array.tripod_names);
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item,parent,false);

            return new com.example.kartheek.dove.RecyclerView.TripodListAdapter.TripodListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(com.example.kartheek.dove.RecyclerView.TripodListAdapter.TripodListViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }


        class TripodListViewHolder extends RecyclerView.ViewHolder{

            TextView mTripodName, mPersonName, mTimeStamp;

            TripodListViewHolder(View itemView){
                super(itemView);

                mTripodName = itemView.findViewById(R.id.item_name);
                mPersonName = itemView.findViewById(R.id.person_name);
                mTimeStamp = itemView.findViewById(R.id.time_stamp);
            }

            void bind(final int position){
                mTripodName.setText(tripodNames[position]);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child("Tripods").child("Tripod"+position).child("perName").getValue(String.class);
                        mTimeStamp.setText(dataSnapshot.child("Tripods").child("Tripod"+position).child("time").getValue(String.class));
                        mPersonName.setText(dataSnapshot.child("Users").child(value).child("name").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
}
