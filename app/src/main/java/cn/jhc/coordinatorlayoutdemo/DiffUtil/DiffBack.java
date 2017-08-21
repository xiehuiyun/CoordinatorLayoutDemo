package cn.jhc.coordinatorlayoutdemo.DiffUtil;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DiffBack extends DiffUtil.Callback {

    private List<String> news;
    private List<String> old;

    public DiffBack(List<String> news, List<String> old) {
        this.news = news;
        this.old = old;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return news.size();
    }

    //类型是否相同，即RecyclerView.Adapter里的viewType
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getClass().equals(news.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).equals(news.get(newItemPosition));
    }
}
