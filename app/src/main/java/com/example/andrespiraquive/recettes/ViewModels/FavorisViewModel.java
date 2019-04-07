package com.example.andrespiraquive.recettes.ViewModels;

import java.sql.Blob;

public class FavorisViewModel {

    private int id;
    private byte[] imageId;
    private String title;
    private String ingredients;
    private String description;
    private String preparations;
    private double note;
    private String position;
    private String document;

    public FavorisViewModel(int id, byte[] imageId, String title, String ingredients, String description, String preparations, String note, String position) {
        this.id = id;
        this.imageId = imageId;
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparations;
        this.note = Double.parseDouble(note);
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImageId() {
        return imageId;
    }

    public void setImageId(byte[] imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}