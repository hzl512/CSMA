package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.model.result.Buys1;

import java.util.List;

/**
 * 我的求购
 */
public class BuysAdapter extends SimpleBaseAdapter<Buys1>{

   	private Context context;
	private ICallOnClickListener icallOnClickListener;
	private int type=0;

	public BuysAdapter(Context context, List<Buys1> data) {
        super(context, data);
        this.context=context;
    }

	public void setType(int t){
		type=t;
	}

    @Override
    public int getItemResource() {
        return R.layout.list_item_buys;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
		Buys1 model=data.get(position);
		final String phone;
		phone=model.buysPhone;
		View viewLine=holder.getView(R.id.viewLine);

		TextView textUsersDetail=holder.getView(R.id.textUsersDetail);
		TextView textBuysName=holder.getView(R.id.textBuysName);
		TextView textBuysAddTime=holder.getView(R.id.textBuysAddTime);
		TextView textBuysDetail=holder.getView(R.id.textBuysDetail);
		TextView textBuysPrice=holder.getView(R.id.textBuysPrice);
	    TextView textBuysAddress=holder.getView(R.id.textBuysAddress);
		LinearLayout btnContacts=holder.getView(R.id.btnContacts);

		textUsersDetail.setText(String.valueOf(model.usersDetail));
	    textBuysName.setText("[求购]"+model.buysName);
		textBuysAddTime.setText(model.buysAddTime);
		textBuysDetail.setText(model.buysDetail);
		textBuysPrice.setText(model.buysPrice);
	    textBuysAddress.setText(model.buysAddress);
		btnContacts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (icallOnClickListener==null){
					return;
				}else {
					icallOnClickListener.onClick(phone);
				}
			}
		});
		btnContacts.setVisibility(View.VISIBLE);
		viewLine.setVisibility(View.VISIBLE);
		if (type == 1) {
			btnContacts.setVisibility(View.GONE);
			viewLine.setVisibility(View.GONE);
		}
		return convertView;
    }

	public interface  ICallOnClickListener{
		void onClick(String phone);
	}

	public void setCallOnClickListener(ICallOnClickListener i){
		icallOnClickListener=i;
	}
   
}