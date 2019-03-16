package com.example.andrespiraquive.recettes.Models;

import android.graphics.Point;

public class Recipes {

    private String imageId;
    private String title;
    private String ingredients;
    private String description;
    private String preparations;
    private double note;
    private String position;
    private String document;
    //private byte[] images;


    private String test;

    public Recipes(String imageId, String title, String ingredients, String description, String preparations, double note, String position, String document) {
        this.imageId = imageId;
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparations;
        this.note = note;
        this.position = position;
        this.document = document;
    }

    public Recipes(String imageId, String title, String ingredients, String description, String preparations, double note, String position) {
        this.imageId = imageId;
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparations;
        this.note = note;
        this.position = position;
    }

    public Recipes(String title, float note, String ingredients, String description, String preparations) {
        this.title = title;
        this.note = note;
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparations;
    }

    public Recipes(String title, String ingredients, String description, String preparations, String position) {
        this.title = title;
        this.position = position;
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparations;
    }

    public Recipes(String imageID, String title, String ingredients, String description, String preparation, String note, String position) {
        this.title = title;
        this.note = Double.parseDouble (note);
        this.ingredients = ingredients;
        this.description = description;
        this.preparations = preparation;
    }

    public String getImage() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPreparations() {
        return preparations;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public double getNote() {
        return note;
    }

    public String getDocument() {
        return document;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public void setDescription(String description) {
        description = description;
    }

    public void setImage(String imageId) {
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
