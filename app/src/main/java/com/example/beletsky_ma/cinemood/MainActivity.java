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
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.beletsky_ma.cinemood.POJO.User;
import com.example.beletsky_ma.cinemood.POJO.UsersList;
import com.example.beletsky_ma.cinemood.View.UsersAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MVPInterface.IView{

    private MVPInterface.IPresenter presenter;
    private UsersAdapter adapter;
    public UsersList user_list;

    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.search_name) EditText edit;
    @BindView(R.id.search_button) Button search_button;
    @BindView(R.id.imageAvatar) ImageView imageAvatar;
    @BindView(R.id.container) FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        adapter = new UsersAdapter(getApplicationContext());
        adapter.mainAvatar = imageAvatar;
        adapter.container = container;

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.search_button_clicked(view,edit.getText().toString());
            }
        });
    }

    public void manage_keyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void populate_adapter(List<User> response) {
        adapter.list = response;
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
            adapter.mainAvatar = imageAvatar;
            adapter.container = container;
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
