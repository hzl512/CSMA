package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.model.result.Profession;

import java.util.List;

/**
 * 专业列表
 */
public class ProfessionAdapter extends SimpleBaseAdapter<Profession> {

    public ProfessionAdapter(Context context, List<Profession> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_profession;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Profession model=data.get(position);
        TextView textProfessionName=holder.getView(R.id.textProfessionName);
        textProfessionName.setText(model.professionName);
        return convertView;
    }

}
