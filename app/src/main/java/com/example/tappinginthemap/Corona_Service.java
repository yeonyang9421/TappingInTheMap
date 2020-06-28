package com.example.tappinginthemap;


import com.example.tappinginthemap.model.CoronaInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Corona_Service {

    @GET("/demo/searchCoronaPatient")
    Call<CoronaInfo> keylatLon(@Query("latitude") double latitude, @Query("longitude") double longitude,
                               @Query("diameter") int diameter , @Query("searchDay") int searchDay);


}
