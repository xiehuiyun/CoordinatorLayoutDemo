package cn.jhc.coordinatorlayoutdemo.DiffUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.jhc.coordinatorlayoutdemo.R;
import cn.jhc.coordinatorlayoutdemo.adapter.RecyclerAdapter;

public class RecyclerDiffUtilActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> first;
    private List<String> second;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_diff_util);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.diff_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(first);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.diff_change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeData();
            }
        });

    }

    private void changeData() {
        second = new ArrayList<>();
        second = first;
        for (int i = 0; i < 50; i++) {
            second .set(i,"that is " + i);
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffBack(second,first));
        adapter.setData(second);
        result.dispatchUpdatesTo(adapter);
    }

    private void initData() {
        first = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            first.add("this is " + i);
        }
    }
}
