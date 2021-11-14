package com.eidotab.eidotab.Interfaz;

import com.eidotab.eidotab.Model.Mensaje;
import com.eidotab.eidotab.Model.Menua;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface IRequesstMensaje {

    @GET("api/menues")
    Call<ArrayList<Menua>> getJSONMenues();

    @POST("api/mensajes")
    Call<Mensaje> addMensaje(@Body Mensaje mensaje);

    @PUT("api/mensajes/{id}")
    Call<Mensaje> updateMensaje(@Path("id") String mensajeId, @Body Mensaje mensaje);

    @DELETE("api/mensajes/{id}")
    Call<Mensaje> deleteMensaje(@Path("id") String mensajeId);

}
