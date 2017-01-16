package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.model.result.Commodity1;

import java.util.List;

/**
 * 
 */
public class CommodityAdapter extends SimpleBaseAdapter<Commodity1>{
   	
	//图片下载器
	private AbImageLoader mAbImageLoader = null;
	public CommodityAdapter(Context context, List<Commodity1> data) {
        super(context, data);
		//图片下载器
		mAbImageLoader = AbImageLoader.getInstance(context);
		mAbImageLoader.setDesiredWidth(200);
		mAbImageLoader.setDesiredHeight(200);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_commodity;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Commodity1 model=data.get(position);
		ProgressBar progressBar=holder.getView(R.id.progressBar);
		ImageView imgView=holder.getView(R.id.imgView);
	    TextView textCommodityName=holder.getView(R.id.textCommodityName);
	    TextView textCommodityAddress=holder.getView(R.id.textCommodityAddress);
	    TextView textCommodityPrice=holder.getView(R.id.textCommodityPrice);
	    TextView textUsersID=holder.getView(R.id.textUsersID);
	    textCommodityName.setText(model.commodityName);
	    textCommodityAddress.setText(model.commodityAddress);
	    textCommodityPrice.setText(model.commodityPrice);
		textUsersID.setText(model.usersProfessionNameGrade);
		//图片的下载
		mAbImageLoader.display(imgView, progressBar, App.BASE_URL + model.commodityImageUrl);
        return convertView;
    }
   
}