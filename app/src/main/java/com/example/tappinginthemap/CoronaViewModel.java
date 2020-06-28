package com.example.tappinginthemap;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.tappinginthemap.model.CoronaInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoronaViewModel extends AndroidViewModel {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.10.141:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Corona_Service service = retrofit.create(Corona_Service.class);
    public MutableLiveData<CoronaInfo> coronaInfos=new MutableLiveData<>();

    public CoronaViewModel(@NonNull Application application) {
        super(application);
    }
    public void fetchCorona(double lat, double lon, int diameter, int searchDay) {
        service.keylatLon(lat, lon, diameter, searchDay).enqueue(new Callback<CoronaInfo>() {
            @Override
            public void onResponse(Call<CoronaInfo> call, Response<CoronaInfo> response) {
                if (response.body() != null) {
//                    lists.setValue(response.body().getList());
//                    cities.setValue(response.body().getCity());
                    coronaInfos.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CoronaInfo> call, Throwable t) {
            }
        });
    }

    }






