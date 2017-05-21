package com.sahilguptalive.amazingflashcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 21-05-2017.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager=getSupportFragmentManager();
    }
}
