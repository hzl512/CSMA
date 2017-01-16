package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class Buys implements Parcelable {
   	
   	public Buys() {}
	     
    //
    public Integer id;
	//
    public Integer usersID;
	//
    public String buysName;
	//
    public String buysPrice;
	//
    public String buysAddress;
	//
    public String buysDetail;
	//
    public String buysPhone;
	//
    public String buysAddTime;
	//
    public Integer buysStatus;
	//
    public Integer buysQQ;

    @Override
    public String toString() {
        return "Buys{" +
                "id=" + id +
                ", usersID=" + usersID +
                ", buysName='" + buysName + '\'' +
                ", buysPrice='" + buysPrice + '\'' +
                ", buysAddress='" + buysAddress + '\'' +
                ", buysDetail='" + buysDetail + '\'' +
                ", buysPhone='" + buysPhone + '\'' +
                ", buysAddTime='" + buysAddTime + '\'' +
                ", buysStatus=" + buysStatus +
                ", buysQQ=" + buysQQ +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.usersID);
        dest.writeString(this.buysName);
        dest.writeString(this.buysPrice);
        dest.writeString(this.buysAddress);
        dest.writeString(this.buysDetail);
        dest.writeString(this.buysPhone);
        dest.writeString(this.buysAddTime);
        dest.writeValue(this.buysStatus);
        dest.writeValue(this.buysQQ);
    }

    protected Buys(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.usersID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.buysName = in.readString();
        this.buysPrice = in.readString();
        this.buysAddress = in.readString();
        this.buysDetail = in.readString();
        this.buysPhone = in.readString();
        this.buysAddTime = in.readString();
        this.buysStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.buysQQ = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Buys> CREATOR = new Parcelable.Creator<Buys>() {
        @Override
        public Buys createFromParcel(Parcel source) {
            return new Buys(source);
        }

        @Override
        public Buys[] newArray(int size) {
            return new Buys[size];
        }
    };
}