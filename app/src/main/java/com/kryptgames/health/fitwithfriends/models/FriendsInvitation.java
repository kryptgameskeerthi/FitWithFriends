package com.kryptgames.health.fitwithfriends.models;

public class FriendsInvitation {

    public String userImageRef,tokenId;
    public String userName;
    private boolean isSelected=false;
    public int userImage;

    public FriendsInvitation(){

    }

    public int getUserImage() {
        return userImage;
    }

    public String getUserImageRef() {
        return userImageRef;
    }

    public void setUserImageRef(String userImageRef) {
        this.userImageRef = userImageRef;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FriendsInvitation(String userImageRef, String tokenId, String userName) {
        this.userImageRef = userImageRef;
        this.tokenId = tokenId;
        this.userName = userName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}

