package com.example.joe.criminalintent;


import android.support.v4.app.Fragment;


/**
 * Created by joe on 16/9/20.
 * 托管CrimeListFragment
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
