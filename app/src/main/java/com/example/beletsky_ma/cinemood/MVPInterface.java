package com.example.beletsky_ma.cinemood;

import android.view.View;

import com.example.beletsky_ma.cinemood.POJO.User;
import com.example.beletsky_ma.cinemood.POJO.UsersList;

import java.util.List;

public interface MVPInterface {

    interface IView{

        void manage_keyboard(View view);
        void populate_adapter(List<User> response);
    }

    interface IPresenter{

        void search_button_clicked(View v, String s);
        void setList(UsersList userList);
    }

    interface IModel{

        void search_user(String str);
    }

}

