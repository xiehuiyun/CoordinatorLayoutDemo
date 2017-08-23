package cn.jhc.coordinatorlayoutdemo.level;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import cn.jhc.coordinatorlayoutdemo.R;
import cn.jhc.coordinatorlayoutdemo.vasSonic.VasActivity;

public class Level3Activity extends AppCompatActivity {

    private TextView text;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        StatusBarUtil.setColor(this, Color.parseColor("#f9c8b9"),0);

        text = (TextView) findViewById(R.id.level3_text);
        text.setText("新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。" +
                "新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。" +
                "新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。新概念作文没有题材限制，但参赛者写得较多的是校园生活、家庭生活，以及对经典的阅读和人生的哲理性思考。这几种类型的题材交织着小作者对亲情、爱情、友情的感悟，对生与死的思考，对价值观人生观的辨析。不管内容有多少变化，文章中任何一种感受和思考都打上了作者生活态度的烙印。");
        toolbar = (Toolbar) findViewById(R.id.level3_toolbar);
        toolbar.setTitle("CollapsingToolbarLayout与NestedScrollView");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        findViewById(R.id.level3_move_to_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level3Activity.this,VasActivity.class));
            }
        });
    }
}
