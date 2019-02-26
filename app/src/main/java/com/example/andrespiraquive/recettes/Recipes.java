package com.example.andrespiraquive.recettes;

import android.graphics.Point;

public class Recipes {

    private String image;
    private String title;
    private String [] ingredients;
    private String [] preparations;
     Point position;

    public Recipes(String image, String title, String[] ingredients, String[] preparations, Point position) {
        this.image = image;
        this.title = title;
        this.ingredients = ingredients;
        this.preparations = preparations;
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getPreparations() {
        return preparations;
    }

    public Point getPosition() {
        return position;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setPreparations(String[] preparations) {
        this.preparations = preparations;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
