package com.weicent.android.csma.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 泛型数据适配器
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> data;
    protected boolean busy = false;

    public SimpleBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int getItemResource();

    /**
     * 滑动状态
     *
     * @param busy
     */
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    //public abstract View getItemView(int position, View convertView);
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    //通过布局ID并使用布局中的ID识别控件
    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

    //添加Item
    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    //插入Item
    public void insert(List<T> elem) {
        data.addAll(0, elem);
        notifyDataSetChanged();
    }

    //移除来自T 也就是model的Item
    public void remove(T elem) {
        if (elem != null) {
            data.remove(elem);
        }
        notifyDataSetChanged();
    }

    //移除来自index 索引的Item
    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    //替换所有Item
    public void update(List<T> elem) {
        if (elem != null) {
            data.clear();
            data.addAll(elem);
        }
        notifyDataSetChanged();
    }

    //清除全部的Item
    public void clearAll() {
        data.clear();
        notifyDataSetChanged();
    }
}

