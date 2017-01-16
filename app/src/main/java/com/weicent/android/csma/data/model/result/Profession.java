package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Profession implements Parcelable {

    public Profession() {
    }

    //
    public Integer id;
    //
    public Integer departmentsID;
    //
    public String professionName;
    //
    public String professionRemark;

    @Override
    public String toString() {
        return "Profession{" +
                "id=" + id +
                ", departmentsID=" + departmentsID +
                ", professionName='" + professionName + '\'' +
                ", professionRemark='" + professionRemark + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.departmentsID);
        dest.writeString(this.professionName);
        dest.writeString(this.professionRemark);
    }

    protected Profession(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.departmentsID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.professionName = in.readString();
        this.professionRemark = in.readString();
    }

    public static final Parcelable.Creator<Profession> CREATOR = new Parcelable.Creator<Profession>() {
        @Override
        public Profession createFromParcel(Parcel source) {
            return new Profession(source);
        }

        @Override
        public Profession[] newArray(int size) {
            return new Profession[size];
        }
    };
}