package com.example.beletsky_ma.cinemood.Model;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.beletsky_ma.cinemood.GitHubAPI;
import com.example.beletsky_ma.cinemood.MVPInterface;
import com.example.beletsky_ma.cinemood.MainPresenter;
import com.example.beletsky_ma.cinemood.POJO.UsersList;
import com.example.beletsky_ma.cinemood.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModel implements  MVPInterface.IModel{


    private MVPInterface.IPresenter iPresenter;
    private GitHubAPI gitHubAPI;

    public UserModel()
    {

    }
    public UserModel(MVPInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        gitHubAPI = RetrofitBuilder.getInstance();
//        gitHubAPI = RetrofitBuilder.getRestClient();
    }

    @Override
    public void search_user(String str)
    {
//        String search_name = edit.getText().toString();

        gitHubAPI.users(str)
                .enqueue(new Callback<UsersList>() {
                    @Override
                    public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                        if(response.isSuccessful())
                        {
                            iPresenter.setList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsersList> call, Throwable t) {
                        Log.e("response_error",t.getMessage());
                    }
                });
    }

//    public void response_success(Response<UsersList> response)
//    {
//        user_list = response.body();
//        adapter.list = user_list.usersList;
//        Log.e("response body","list - "+user_list.usersList.size());
//        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recycler.setAdapter(adapter);
//    }

}
