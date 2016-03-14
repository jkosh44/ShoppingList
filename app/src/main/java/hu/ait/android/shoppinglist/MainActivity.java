package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;
    private Timer timer;

    private class MyCounterTask extends TimerTask {
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    counter++;
                    if (counter == 3) {
                        Intent startSec = new Intent();
                        startSec.setClass(MainActivity.this, ListActivity.class);

                        startActivity(startSec);

                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new MyCounterTask(), 0, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

}
