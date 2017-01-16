package com.weicent.android.csma.data.model.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Departments implements Parcelable {

    public Departments() {
    }
    //
    public Integer id;
    //
    public String departmentsName;
    //
    public String departmentsRemark;

    @Override
    public String toString() {
        return "Departments{" +
                "id=" + id +
                ", departmentsName='" + departmentsName + '\'' +
                ", departmentsRemark='" + departmentsRemark + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.departmentsName);
        dest.writeString(this.departmentsRemark);
    }

    protected Departments(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.departmentsName = in.readString();
        this.departmentsRemark = in.readString();
    }

    public static final Parcelable.Creator<Departments> CREATOR = new Parcelable.Creator<Departments>() {
        @Override
        public Departments createFromParcel(Parcel source) {
            return new Departments(source);
        }

        @Override
        public Departments[] newArray(int size) {
            return new Departments[size];
        }
    };
}