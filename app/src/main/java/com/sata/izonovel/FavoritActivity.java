package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.sata.izonovel.Model.FavoriteNovelRequest;
import com.sata.izonovel.Model.FavoriteNovelResponse;
import com.sata.izonovel.Model.ListNovelRequestModel;
import com.sata.izonovel.Model.ListNovelResponseModel;
import com.sata.izonovel.Model.LoginRequestModel;
import com.sata.izonovel.Retrofit.APIService;
import com.sata.izonovel.adpter.DaftarNovelAdapter;
import com.sata.izonovel.adpter.FavoritNovelAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritActivity extends AppCompatActivity {
    EditText fav;
    private RecyclerView recyclerView;
    private FavoritNovelAdapter favNovelAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_novel);

        setTitle(R.string.title_daftar_novel);

        recyclerView = findViewById(R.id.reycle_daftar_novel);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        onLoadData();
    }

    private void onLoadData(){
        FavoriteNovelRequest favoriteNovelRequest=new FavoriteNovelRequest();
        favoriteNovelRequest.setCollection("novel");
        favoriteNovelRequest.setDatabase("izonovel");
        favoriteNovelRequest.setDataSource("Cluster0");

        FavoriteNovelRequest.Filter favFilter = new FavoriteNovelRequest.Filter();
        favFilter.setisFavorit(fav.getText().toString());

        favoriteNovelRequest.setFilter(favFilter);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        APIService.endpoint().FavNovel(favoriteNovelRequest).enqueue(new Callback<FavoriteNovelResponse>() {
            @Override
            public void onResponse(Call<FavoriteNovelResponse> call, Response<FavoriteNovelResponse> response) {
                List<FavoriteNovelResponse.Document> document = response.body().getDocuments();

                favNovelAdapter = new FavoritNovelAdapter(FavoritActivity.this, document);
                recyclerView.setAdapter(favNovelAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FavoriteNovelResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}