package com.example.beletsky_ma.cinemood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersList implements Parcelable{

    @SerializedName("items")
    public List<User> usersList;

    public static final Creator<UsersList> CREATOR = new Creator<UsersList>() {
        @Override
        public UsersList createFromParcel(Parcel in) {
            return new UsersList(in);
        }

        @Override
        public UsersList[] newArray(int size) {
            return new UsersList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeArray(new Object[]{usersList});
    }

    private UsersList(Parcel in)
    {
        Object[] data = new Object[1];
        data = in.readArray(Object.class.getClassLoader());
        usersList = (List)data[0];
    }
}
