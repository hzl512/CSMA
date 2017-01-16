package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class Users1 implements Parcelable {

   	public Users1() {}

    //
    public Integer id;
	//
    public String usersName;
	//
    public String usersPwd;
	//
    public String usersAddTime;
	//
    public String usersNickName;
	//
    public String usersPhone;
	//
    public String usersQQ;
	//
    public String departmentsID;
	//
    public String professionID;
	//
    public String usersGrade;

    @Override
    public String toString() {
        return "Users1{" +
                "id=" + id +
                ", usersName='" + usersName + '\'' +
                ", usersPwd='" + usersPwd + '\'' +
                ", usersAddTime='" + usersAddTime + '\'' +
                ", usersNickName='" + usersNickName + '\'' +
                ", usersPhone='" + usersPhone + '\'' +
                ", usersQQ='" + usersQQ + '\'' +
                ", departmentsID='" + departmentsID + '\'' +
                ", professionID='" + professionID + '\'' +
                ", usersGrade='" + usersGrade + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.usersName);
        dest.writeString(this.usersPwd);
        dest.writeString(this.usersAddTime);
        dest.writeString(this.usersNickName);
        dest.writeString(this.usersPhone);
        dest.writeString(this.usersQQ);
        dest.writeString(this.departmentsID);
        dest.writeString(this.professionID);
        dest.writeString(this.usersGrade);
    }

    protected Users1(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.usersName = in.readString();
        this.usersPwd = in.readString();
        this.usersAddTime = in.readString();
        this.usersNickName = in.readString();
        this.usersPhone = in.readString();
        this.usersQQ = in.readString();
        this.departmentsID = in.readString();
        this.professionID = in.readString();
        this.usersGrade = in.readString();
    }

    public static final Creator<Users1> CREATOR = new Creator<Users1>() {
        @Override
        public Users1 createFromParcel(Parcel source) {
            return new Users1(source);
        }

        @Override
        public Users1[] newArray(int size) {
            return new Users1[size];
        }
    };
}