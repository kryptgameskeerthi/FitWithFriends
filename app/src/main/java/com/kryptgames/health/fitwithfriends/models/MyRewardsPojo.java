package com.kryptgames.health.fitwithfriends.models;

public class MyRewardsPojo {
    public int rewardImage;
    public String title,useByDate;

    public int getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(int rewardImage) {
        this.rewardImage = rewardImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUseByDate() {
        return useByDate;
    }

    public void setUseByDate(String useByDate) {
        this.useByDate = useByDate;
    }

    public MyRewardsPojo(int rewardImage, String title, String useByDate) {
        this.rewardImage = rewardImage;
        this.title = title;
        this.useByDate = useByDate;
    }
}
