package com.androidex.lockcontrolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DoorLockServiceBinder doorLockServiceBinder;
    private Button always,delay,close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doorLockServiceBinder = new DoorLockServiceBinder(this);
        always = findViewById(R.id.open_n);
        always.setOnClickListener(this);
        delay = findViewById(R.id.open_d);
        delay.setOnClickListener(this);
        close = findViewById(R.id.close_);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_n:
                doorLockServiceBinder.openDoor(0xF0, 0);
                break;
            case R.id.open_d:
                doorLockServiceBinder.openDoor(0xF0, 0x40);
                break;
            case R.id.close_:
                doorLockServiceBinder.closeDoor(0);
                break;
        }
    }
}
