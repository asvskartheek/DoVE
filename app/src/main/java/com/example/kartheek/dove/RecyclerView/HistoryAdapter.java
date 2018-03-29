package com.example.kartheek.dove.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartheek.dove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
        return mNoOfItems;
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
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            Intent intent = ((Activity) context).getIntent();
            final String type = intent.getStringExtra("type");
            final int number = intent.getIntExtra("number",0);
            mPersonName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String user = dataSnapshot.child("Camera"+position).child("perName").getValue(String.class);
                            assert user != null;


                            int position_sudo = position+1;
                            String child = type.concat(Integer.toString(number));
                            String value = dataSnapshot.child(child).child("his"+position_sudo).getValue(String.class);
                            System.out.println(value);
                            assert value != null;
                            if(!value.equals("u_1")&&!value.equals("u_10")){
                                String phone_number = dataSnapshot.child("Users").child(user).child("phone").getValue(String.class);
                                Uri uri = Uri.parse("tel:"+phone_number);
                                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                                if (intent.resolveActivity(context.getPackageManager())!=null){
                                    context.startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(context,"This is a dummy user bruh!!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int position_sudo = position+1;
                    String child = type.concat(Integer.toString(number));
                    String value = dataSnapshot.child(child).child("his"+position_sudo).getValue(String.class);
                    System.out.println(value);
                    assert value != null;
                    if(!value.equals("u_1")&&!value.equals("u_10")){
                        mPersonName.setText(dataSnapshot.child("Users").child(value).child("name").getValue(String.class));
                    }
                    else {
                        mPersonName.setText(value);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
