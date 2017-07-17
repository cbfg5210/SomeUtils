package com.ue.someutils.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private boolean hasSavedInstanceState;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        hasSavedInstanceState = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        hasSavedInstanceState = true;
    }

    public boolean isHasSavedInstanceState() {
        return hasSavedInstanceState;
    }
}
