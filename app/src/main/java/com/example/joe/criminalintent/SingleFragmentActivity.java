package com.example.joe.criminalintent;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * 通用超类,封装新建activity过程
 * Created by joe on 16/9/20.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    //返回activity托管的fragment实例
    protected abstract Fragment createFragment();

    /**
     * 获取所需fragment的资源Id
     * @return
     */
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //在fragManager中查找fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            //创建新事务、添加绑定fragment、提交
            fragment = createFragment();//其子类必须实现该方法,返回由activity托管的fragment实例
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
