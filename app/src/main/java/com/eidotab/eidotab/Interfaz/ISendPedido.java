package com.eidotab.eidotab.Interfaz;


import com.eidotab.eidotab.Model.DataRoot;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISendPedido
{
    @POST("api/comandas")
    Call<DataRoot> dataenviar (@Body DataRoot pedido);

}
