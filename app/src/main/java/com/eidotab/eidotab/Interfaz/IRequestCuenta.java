package com.eidotab.eidotab.Interfaz;

import com.eidotab.eidotab.Model.DataRoot;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestCuenta
{
    @GET("api/factura/{id}")
    Call<DataRoot> getJSONFacturasid(@Path("id") String id);

    @GET("api/facturas")
    Call<ArrayList<DataRoot>> getJSONFacturas();

    @POST("api/facturas")
    Call<DataRoot> addFactura(@Body DataRoot dataRoot);

    @PUT("api/facturas/{id}")
    Call<DataRoot> updateFactura(@Path("id") String facturaId, @Body DataRoot dataRoot);

    @DELETE("api/facturas/{id}")
    Call<DataRoot> deleteFactura(@Path("id") String facturaId);
}
