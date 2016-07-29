package cn.wangyang.www.mygirlfriend;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import tyrantgit.widget.HeartLayout;

public class MainActivity extends AppCompatActivity implements Handler.Callback{

    private Random mRandom = new Random();
    private Handler handler = new Handler(this);

    private HeartLayout mHeartLayout;
    private HeartLayout mHeartLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        mHeartLayout2 = (HeartLayout) findViewById(R.id.heart_layout2);
        handler.sendEmptyMessageDelayed(1, 500);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    @Override
    public boolean handleMessage(Message message) {
        mHeartLayout.addHeart(randomColor());
        mHeartLayout2.addHeart(randomColor());
        handler.sendEmptyMessageDelayed(1, 200);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
