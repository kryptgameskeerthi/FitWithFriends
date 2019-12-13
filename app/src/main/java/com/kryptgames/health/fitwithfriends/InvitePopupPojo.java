package com.kryptgames.health.fitwithfriends;

public class InvitePopupPojo {

    public int userImage;
    public String userName;

    public InvitePopupPojo(int userImage, String userName) {
        this.userImage = userImage;
        this.userName = userName;
    }

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
}
