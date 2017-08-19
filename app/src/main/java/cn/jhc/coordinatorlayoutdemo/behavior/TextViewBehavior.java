package cn.jhc.coordinatorlayoutdemo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/18.
 */

public class TextViewBehavior extends CoordinatorLayout.Behavior<TextView> {

    public TextViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX()+200);
        child.setY(dependency.getY() +200);
        child.setText(child.getX() +" , " + child.getY());
        return true;
    }
}
