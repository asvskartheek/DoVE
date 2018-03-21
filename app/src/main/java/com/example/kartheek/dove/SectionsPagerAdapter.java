package com.example.kartheek.dove;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by blueberryboy on 20/3/18.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                CameraFragment cameraFragment = new CameraFragment();
                return cameraFragment;
            case 1:
                MemoryFragment memoryFragment = new MemoryFragment();
                return memoryFragment;
            case 2:
                BaseFragment baseFragment = new BaseFragment();
                return baseFragment;
            case 3:
                TripodFragment tripodFragment = new TripodFragment();
                return tripodFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
