package cn.jhc.coordinatorlayoutdemo.level;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jhc.coordinatorlayoutdemo.DiffUtil.RecyclerDiffUtilActivity;
import cn.jhc.coordinatorlayoutdemo.R;
import cn.jhc.coordinatorlayoutdemo.adapter.RecyclerAdapter;


public class Level2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<String> data;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitle("AppBarLayout与CoordinatorLayout结合");
        setSupportActionBar(toolbar);
        StatusBarUtil.setColor(this,Color.parseColor("#f9c8b9"),0);
        initData();

        recyclerView = (RecyclerView) findViewById(R.id.level2_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);

       findViewById(R.id.move_to_level3).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               startActivity(new Intent(Level2Activity.this,Level3Activity.class));
               startActivity(new Intent(Level2Activity.this,RecyclerDiffUtilActivity.class));
           }
       });
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            data.add("this is " + i);
        }
    }
}
