package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.model.result.Category;

import java.util.List;
/**
 * 
 */
public class CategoryAdapter extends SimpleBaseAdapter<Category>{
   	
   	private Context context;

	public CategoryAdapter(Context context, List<Category> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_category;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Category model=data.get(position);
        ImageView imageView=holder.getView(R.id.imgView);
	    TextView textCategoryName=holder.getView(R.id.textCategoryName);
	    TextView textCategoryRemark=holder.getView(R.id.textCategoryRemark);
        imageView.setBackgroundDrawable(context.getResources().getDrawable(Global.getInstance().getCategoryView(model.id)));
	    textCategoryName.setText(model.categoryName);
	    textCategoryRemark.setText(model.categoryRemark);
        return convertView;
    }
   
}