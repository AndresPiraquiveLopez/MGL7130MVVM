package com.example.andrespiraquive.recettes.Data.Database.Repository;

import java.util.List;

public interface IRepository<T> {
    T saveItem(T record);

    void removeItem(T record);

    T getItem(String id);

    List<T> getItems();

    int getLength();
}
