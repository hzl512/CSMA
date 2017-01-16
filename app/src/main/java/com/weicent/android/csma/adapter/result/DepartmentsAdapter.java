package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.model.result.Departments;

import java.util.List;

/**
 *
 */
public class DepartmentsAdapter extends SimpleBaseAdapter<Departments> {


    public DepartmentsAdapter(Context context, List<Departments> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_departments;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Departments model=data.get(position);
        TextView textDepartmentsName=holder.getView(R.id.textDepartmentsName);
        textDepartmentsName.setText(model.departmentsName);
        return convertView;
    }

}