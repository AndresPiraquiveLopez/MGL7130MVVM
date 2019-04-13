package com.example.andrespiraquive.recettes.Data.Database.Repository;

import android.content.Context;

import com.example.andrespiraquive.recettes.Models.Recipes;

public class RepositoryFactory {
    private static IRepository<Recipes> _recipesRepository = null;

    public static IRepository<Recipes> getRecipesRepository(Context context) {
        if (_recipesRepository == null) {
            _recipesRepository = new Repository<Recipes> (context);
        }
        return _recipesRepository;
    }
}
