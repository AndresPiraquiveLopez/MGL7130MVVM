package com.example.andrespiraquive.recettes.Data.Database.Repository;

import android.content.Context;

import com.example.andrespiraquive.recettes.Models.Recipes;

import java.util.List;

public class FakeRepository<T extends Recipes> implements IRepository<T> {
    public FakeRepository(Context context) {
    }

    @Override
    public T saveItem(T record) {
        return null;
    }

    @Override
    public void removeItem(T record) {

    }

    @Override
    public T getItem(String id) {
        return null;
    }

    @Override
    public List<T> getItems() {
        return null;
    }

    @Override
    public int getLength() {
        return 0;
    }
}
