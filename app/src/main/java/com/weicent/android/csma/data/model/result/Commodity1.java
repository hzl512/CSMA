package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;
/**
 *
 */
public class Commodity1 implements Parcelable {

    public Commodity1() {}

    //
    public Integer id;
    //
    public Integer usersID;
    //用户信息
    public String usersDetail;
    //用户专业信息
    public String usersProfessionNameGrade;
    //图片地址
    public String commodityImageUrl;
    //商品名称
    public String commodityName;
    //商品详情
    public String commodityDetail;
    //交易地点
    public String commodityAddress;
    //价格
    public String commodityPrice;
    //讲价
    public Integer commodityBargain;
    //联系电话
    public Integer commodityPhone;
    //联系QQ
    public Integer commodityQQ;
    //添加时间
    public String commodityAddTime;
    //浏览量
    public Integer commodityViews;

    @Override
    public String toString() {
        return "Commodity1{" +
                "id=" + id +
                ", usersID=" + usersID +
                ", usersDetail='" + usersDetail + '\'' +
                ", commodityImageUrl='" + commodityImageUrl + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityDetail='" + commodityDetail + '\'' +
                ", usersProfessionNameGrade='" + usersProfessionNameGrade + '\'' +
                ", commodityAddress='" + commodityAddress + '\'' +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", commodityBargain=" + commodityBargain +
                ", commodityPhone=" + commodityPhone +
                ", commodityQQ=" + commodityQQ +
                ", commodityAddTime='" + commodityAddTime + '\'' +
                ", commodityViews=" + commodityViews +
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
        dest.writeString(this.usersDetail);
        dest.writeString(this.usersProfessionNameGrade);
        dest.writeString(this.commodityImageUrl);
        dest.writeString(this.commodityName);
        dest.writeString(this.commodityDetail);
        dest.writeString(this.commodityAddress);
        dest.writeString(this.commodityPrice);
        dest.writeValue(this.commodityBargain);
        dest.writeValue(this.commodityPhone);
        dest.writeValue(this.commodityQQ);
        dest.writeString(this.commodityAddTime);
        dest.writeValue(this.commodityViews);
    }

    protected Commodity1(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.usersID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.usersDetail = in.readString();
        this.usersProfessionNameGrade = in.readString();
        this.commodityImageUrl = in.readString();
        this.commodityName = in.readString();
        this.commodityDetail = in.readString();
        this.commodityAddress = in.readString();
        this.commodityPrice = in.readString();
        this.commodityBargain = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commodityPhone = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commodityQQ = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commodityAddTime = in.readString();
        this.commodityViews = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Commodity1> CREATOR = new Parcelable.Creator<Commodity1>() {
        @Override
        public Commodity1 createFromParcel(Parcel source) {
            return new Commodity1(source);
        }

        @Override
        public Commodity1[] newArray(int size) {
            return new Commodity1[size];
        }
    };
}