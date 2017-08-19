package cn.jhc.coordinatorlayoutdemo.level;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jhc.coordinatorlayoutdemo.R;
import cn.jhc.coordinatorlayoutdemo.adapter.RecyclerAdapter;


public class Level2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        data = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            data.add("this is " + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.level2_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }
}
