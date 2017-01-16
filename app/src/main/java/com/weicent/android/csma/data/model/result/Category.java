package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class Category implements Parcelable {
   	
   	public Category() {}
	     
    //
    public Integer id;
	//
    public String categoryName;
	//
    public String categoryRemark;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryRemark='" + categoryRemark + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryRemark);
    }

    protected Category(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryName = in.readString();
        this.categoryRemark = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}