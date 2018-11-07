package com.example.beletsky_ma.cinemood;

import android.view.View;

import com.example.beletsky_ma.cinemood.Model.UserModel;
import com.example.beletsky_ma.cinemood.POJO.UsersList;

public class MainPresenter implements MVPInterface.IPresenter {

    private MVPInterface.IView view;
    private MVPInterface.IModel model;

    public MainPresenter(MVPInterface.IView mView) {
        this.view = mView;
        this.model = new UserModel(this);
    }

    public void search_button_clicked(View v, String s)
    {
        view.manage_keyboard(v);

        model.search_user(s);
    }

    public void setList(UsersList userList)
    {
        view.populate_adapter(userList.usersList);
    }
}
