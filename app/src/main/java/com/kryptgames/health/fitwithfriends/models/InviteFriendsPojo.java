package com.kryptgames.health.fitwithfriends.models;

public class InviteFriendsPojo {

    public int userImage;
    public String userName;
    private boolean isSelected=false;

    public InviteFriendsPojo(int userImage, String userName) {
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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}

