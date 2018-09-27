package com.example.beletsky_ma.cinemood;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.beletsky_ma.cinemood.POJO.User;
import com.example.beletsky_ma.cinemood.POJO.UsersList;
import com.example.beletsky_ma.cinemood.View.UsersAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements MVPInterface.IView{

    private String BASE_URL = "https://api.github.com/";
    private MVPInterface.IPresenter presenter;
    private UsersAdapter adapter;
    private RecyclerView recycler;
    public UsersList user_list;
    public EditText edit;
    Button search_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        recycler = findViewById(R.id.recycler);

        adapter = new UsersAdapter(getApplicationContext());
        adapter.mainAvatar = findViewById(R.id.imageAvatar);
        adapter.container = (FrameLayout)findViewById(R.id.container);

        edit  = (EditText) findViewById(R.id.search_name);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.search_button_clicked(view);

            }
        });
    }

    public void manage_keyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void search_user()
    {
        String search_name = edit.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        GitHubAPI gitFace = retrofit.create(GitHubAPI.class);

        Call<UsersList> users = gitFace.users(search_name);

        users.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                if(response.isSuccessful())
                {
                    response_success(response);
                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                Log.e("response_error",t.getMessage());
            }
        });
    }

    public void response_success(Response<UsersList> response)
    {

        user_list = response.body();
        adapter.list = user_list.usersList;
        Log.e("response body","list - "+user_list.usersList.size());
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("list",user_list);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user_list = savedInstanceState.getParcelable("list");
        if(user_list != null) {
            adapter = new UsersAdapter(getApplicationContext());
            adapter.list = user_list.usersList;
            adapter.mainAvatar = findViewById(R.id.imageAvatar);
            adapter.container = (FrameLayout) findViewById(R.id.container);
            recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recycler.setAdapter(adapter);
        }
    }

    //конвертер для получения ответа в ретрофите в виде строки (без сериализации)
    /*
    class ToStringConverterFactory extends Converter.Factory {
        private final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                                Retrofit retrofit) {
            if (String.class.equals(type)) {
                return new Converter<ResponseBody, String>() {
                    @Override
                    public String convert(ResponseBody value) throws IOException
                    {
                        return value.string();
                    }
                };
            }
            return null;
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                              Annotation[] methodAnnotations, Retrofit retrofit) {

            if (String.class.equals(type)) {
                return new Converter<String, RequestBody>() {
                    @Override
                    public RequestBody convert(String value) throws IOException {
                        return RequestBody.create(MEDIA_TYPE, value);
                    }
                };
            }
            return null;
        }
    }
    */
}
