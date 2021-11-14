package com.eidotab.eidotab.Interfaz;


import com.eidotab.eidotab.Model.Plato;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestPlato
{

    @GET("api/platos/{id}")
    Call<Plato> getJSONPlatoid(@Path("id") String id);

    @GET("api/platos")
    Call<ArrayList<Plato>> getJSONPlatos();

    @POST("api/platos")
    Call<Plato> addPlato(@Body Plato plato);

    @PUT("api/platos/{id}")
    Call<Plato> updatePlato(@Path("id") String platoId, @Body Plato plato);

    @DELETE("api/platos/{id}")
    Call<Plato> deletePlato(@Path("id") String platoId);
}
