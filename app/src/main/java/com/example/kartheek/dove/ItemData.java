package com.example.kartheek.dove;


import android.annotation.SuppressLint;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class ItemData {
    public String his1,his2,his3,his4,his5,his6,his7,his8,his9,his10,his11,his12,his13,his14,his15,his16,his17,his18,his19;
    public String perName;
    public String time;

    public ItemData(){
        //Default constructor for calls to DataSnapshot.getValue(ItemData.class)
    }

    @SuppressLint("SimpleDateFormat")
    public ItemData(ItemData old, String new_user){
        this.perName = new_user;
        this.his1 = old.perName;
        this.his2 = old.his1;
        this.his3 = old.his2;
        this.his4 = old.his3;
        this.his5 = old.his4;
        this.his6 = old.his5;
        this.his7 = old.his6;
        this.his8 = old.his7;
        this.his9 = old.his8;
        this.his10 = old.his9;
        this.his11 = old.his10;
        this.his12 = old.his11;
        this.his13 = old.his12;
        this.his14=old.his13;
        this.his15=old.his14;
        this.his16=old.his15;
        this.his17=old.his16;
        this.his18=old.his17;
        this.his19=old.his18;
        this.time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
