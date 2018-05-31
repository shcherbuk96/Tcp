package com.shcherbuk.tcp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekBar;
    TextView textView;
    Connection connection;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        //seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(this);

        context=getApplicationContext();

        connection = new Connection();

        start();
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("asd","asd");
                connection.openConnection(context);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("asd2","asd2");
//                        seekBar.setEnabled(true);
//                    }
//                });
            }
        }).start();
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
        textView.setText(String.valueOf(seekBar.getProgress()));
        if (connection != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    connection.sendData(seekBar.getProgress(),context);
                }
            }).start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.closeConnection();
    }

    @Override
    protected void onStop() {
        super.onStop();
        connection.closeConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        connection.closeConnection();
    }
}
