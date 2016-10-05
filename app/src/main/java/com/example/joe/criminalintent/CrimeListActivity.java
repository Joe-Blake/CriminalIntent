package com.example.joe.criminalintent;


import android.content.Intent;
import android.support.v4.app.Fragment;



/**
 * Created by joe on 16/9/20.
 * 托管CrimeListFragment
 */

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    /**
     * 有条件的CrimeFragment启动
     * @param crime
     */
    @Override
    public void onCrimeSelected(Crime crime) {
        //如果不包含详情页,打开独立详情页
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {//包含详情页,添加fragment到detail_fragment_container中
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id
                    .detail_fragment_container, newDetail).commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
