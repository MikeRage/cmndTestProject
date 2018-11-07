package com.example.beletsky_ma.cinemood.modules;

import com.example.beletsky_ma.cinemood.Model.UserModel;
import com.example.beletsky_ma.cinemood.View.UsersAdapter;

import dagger.Provides;

public class UsersAdapterModule {

    @Provides
    UserModel provideUsersAdapter()
    {
        return new UserModel();
    }
}
