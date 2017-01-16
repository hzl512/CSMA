package com.weicent.android.csma.adapter.result;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.SimpleBaseAdapter;
import com.weicent.android.csma.data.model.result.*;

import java.util.List;

/**
 * 
 */
public class UsersAdapter extends SimpleBaseAdapter<Users>{
   	
   	private Context context;

	public UsersAdapter(Context context, List<Users> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_users;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Users model=data.get(position);
	     	
	    TextView textUsersName=holder.getView(R.id.textUsersName);
			
	    TextView textUsersAddTime=holder.getView(R.id.textUsersAddTime);
			
	    TextView textUsersNickName=holder.getView(R.id.textUsersNickName);
			
	    TextView textUsersPhone=holder.getView(R.id.textUsersPhone);
			
	    TextView textUsersQQ=holder.getView(R.id.textUsersQQ);
			
	    TextView textDepartmentsID=holder.getView(R.id.textDepartmentsID);
			
	    TextView textProfessionID=holder.getView(R.id.textProfessionID);
			
	    TextView textUsersGrade=holder.getView(R.id.textUsersGrade);
					
	    textUsersName.setText(model.usersName);
			
	    textUsersAddTime.setText(model.usersAddTime);
			
	    textUsersNickName.setText(model.usersNickName);
			
	    textUsersPhone.setText(model.usersPhone);
			
	    textUsersQQ.setText(model.usersQQ);
			
	    textDepartmentsID.setText(String.valueOf(model.departmentsID));
			
	    textProfessionID.setText(String.valueOf(model.professionID));
			
	    textUsersGrade.setText(String.valueOf(model.usersGrade));
		
        return convertView;
    }
   
}