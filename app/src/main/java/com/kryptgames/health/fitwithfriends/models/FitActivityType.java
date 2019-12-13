package com.kryptgames.health.fitwithfriends.models;

public class FitActivityType {

    private String fitEventType;
    private int unselectedImageId;
    private int selectedImageId;
    private boolean iscurrentSelected;

    public FitActivityType(String fitEventType, int unselectedImageId, int selectedImageId, boolean iscurrentSelected) {
        this.fitEventType = fitEventType;
        this.unselectedImageId = unselectedImageId;
        this.selectedImageId = selectedImageId;
        this.iscurrentSelected = iscurrentSelected;
    }

    public FitActivityType(String fitEventType, int unselectedImageId, int selectedImageId) {
        this.fitEventType = fitEventType;
        this.unselectedImageId = unselectedImageId;
        this.selectedImageId = selectedImageId;

        iscurrentSelected = false;
    }

    public String getFitEventType() {
        return fitEventType;
    }

    public void setFitEventType(String fitEventType) {
        this.fitEventType = fitEventType;
    }

    public int getUnselectedImageId() {
        return unselectedImageId;
    }

    public void setUnselectedImageId(int unselectedImageId) {
        this.unselectedImageId = unselectedImageId;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public boolean isIscurrentSelected() {
        return iscurrentSelected;
    }

    public void setIscurrentSelected(boolean iscurrentSelected) {
        this.iscurrentSelected = iscurrentSelected;
    }
}
