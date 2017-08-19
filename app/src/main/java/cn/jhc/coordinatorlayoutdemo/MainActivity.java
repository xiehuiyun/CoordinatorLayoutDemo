package cn.jhc.coordinatorlayoutdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import cn.jhc.coordinatorlayoutdemo.level.Level2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("level 1");

        findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    v.setX(event.getRawX()-v.getWidth()/2);
                    v.setY(event.getRawY()-v.getHeight()/2);
                }
                return false;
            }
        });

        findViewById(R.id.to_next_level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Level2Activity.class));
            }
        });
    }

}
