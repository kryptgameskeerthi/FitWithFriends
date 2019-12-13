package com.kryptgames.health.fitwithfriends.models;

import android.os.Parcel;
import android.os.Parcelable;

public class InvitePopupPojo implements Parcelable {

    public String userImageRef;
    public String userName;

    public InvitePopupPojo(String userImageRef, String userName) {
        this.userImageRef = userImageRef;
        this.userName = userName;
    }

    protected InvitePopupPojo(Parcel in) {
        userImageRef = in.readString();
        userName = in.readString();
    }

    public static final Creator<InvitePopupPojo> CREATOR = new Creator<InvitePopupPojo>() {
        @Override
        public InvitePopupPojo createFromParcel(Parcel in) {
            return new InvitePopupPojo(in);
        }

        @Override
        public InvitePopupPojo[] newArray(int size) {
            return new InvitePopupPojo[size];
        }
    };

    public String getUserImage() {
        return userImageRef;
    }

    public void setUserImage(String userImage) {
        this.userImageRef = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userImageRef);
        dest.writeString(userName);
    }
}
