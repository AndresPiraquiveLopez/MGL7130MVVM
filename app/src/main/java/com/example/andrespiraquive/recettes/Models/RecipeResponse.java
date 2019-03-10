package com.example.andrespiraquive.recettes.Models;

public class RecipeResponse {
    private int PictureId;

    private String Title;

    private Float Note;

    private String Description;

    public RecipeResponse(){
    }

    public RecipeResponse(int pictureId, String title, Float Note, String description) {
        this.PictureId = pictureId;
        this.Title = title;
        this.Note = Note;
        Description = description;
    }
    public RecipeResponse(int pictureId, String title) {
        this.PictureId = pictureId;
        this.Title = title;
    }

    public int getPictureId() {
        return PictureId;
    }

    public float getNote() {
        return Note;
    }

    public String getDescription() {
        return Description;
    }

    public String getTitle() {
        return Title;
    }

    public void setPictureId(int pictureId) {
        PictureId = pictureId;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setNote(float note) {
        Note = note;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
