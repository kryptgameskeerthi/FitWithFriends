package com.kryptgames.health.fitwithfriends.models;

import android.os.Parcel;
import android.os.Parcelable;

public class InvitePopupPojo implements Parcelable {

    public int userImage;
    public String userName;

    public InvitePopupPojo(int userImage, String userName) {
        this.userImage = userImage;
        this.userName = userName;
    }

    protected InvitePopupPojo(Parcel in) {
        userImage = in.readInt();
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

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
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
        dest.writeInt(userImage);
        dest.writeString(userName);
    }
}
