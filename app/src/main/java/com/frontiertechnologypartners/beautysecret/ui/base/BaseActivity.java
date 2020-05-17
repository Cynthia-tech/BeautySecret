package com.frontiertechnologypartners.beautysecret.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog loadingBar;
    protected DatabaseReference dbRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);

        dbRef = FirebaseDatabase.getInstance().getReference();
        Paper.init(this);
    }
}
