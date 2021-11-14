package com.eidotab.eidotab;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.eidotab.eidotab.Interfaz.IRequesstMensaje;
import com.eidotab.eidotab.Interfaz.IRequestPlato;
import com.eidotab.eidotab.Model.Menua;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.Model.Subcaterator;
import com.eidotab.eidotab.Model.Tablet;
import com.eidotab.eidotab.SQlite.DBHelper;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;
import com.owncloud.android.lib.common.network.OnDatatransferProgressListener;
import com.owncloud.android.lib.common.operations.OnRemoteOperationListener;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.files.DownloadRemoteFileOperation;
import com.wang.avi.AVLoadingIndicatorView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoadActivity extends AppCompatActivity implements OnRemoteOperationListener, OnDatatransferProgressListener
{
    AVLoadingIndicatorView loading;

    Boolean conecta = false;
    Boolean esconderui = false;

    TextView txtload;

    DBHelper myDB;

    ArrayList<Plato> lista_Plato;

    String username;
    String password;
    File downloadfolder;

    EditText txt_mesa;
    Button   btn_mesa;

    private OwnCloudClient mClient;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        myDB = DBHelper.GetDBHelper(this);

        lista_Plato = new ArrayList<>();

        txtload = findViewById(R.id.txtload);
        loading = findViewById(R.id.loadanimation);
        txt_mesa = findViewById(R.id.txt_mesa);
        btn_mesa = findViewById(R.id.btn_mesa);

        username = "eidotab";
        password = "moslMOSL";

        Uri serverUri = Uri.parse(getString(R.string.iptab2) + "owncloud");


        downloadfolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);
        mClient.setCredentials(OwnCloudCredentialsFactory.newBasicCredentials(username, password));

    }

    @Override
    protected void onResume()
    {
        super.onResume();

       // startAnim();
        UiChangeListener();


        final Handler handler = new Handler();
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                while (!conecta)
                {
                    if (isNetworkConnected())
                    {

                        conecta = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                txtload.setText("Cargando por favor espere");
                                if(esconderui)
                                {
                                    getWindow().getDecorView().setSystemUiVisibility(
                                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                                }
                            }
                        });


                        loadretrofitmenu();

                        loadRetrofitPlato();

                    }
                    else
                    {

                        esconderui = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                txtload.setText("Desconectado de la Red, Intente de nuevo");
                            }
                        });
                    }
                }
            }
        };
        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }

    private void mostrarMensaje(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void loadRetrofitPlato()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestPlato request = retrofit.create(IRequestPlato.class);

        Call<ArrayList<Plato>> call = request.getJSONPlatos();

        call.enqueue(new Callback<ArrayList<Plato>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Plato>> call, @NonNull Response<ArrayList<Plato>> response) {
                ArrayList<Plato> prefiltro = new ArrayList<>();

                myDB.deleteAllPlato();

               Menua menua = myDB.listMenua();

                String activo = menua.getActivo();


                switch(activo)
                {
                    case "1" :

                        for(Plato plato : response.body())
                        {
                            Boolean m1 = plato.getM1();
                            if(m1)
                            {
                                prefiltro.add(plato);
                            }


                        }

                         break;

                    case "2" :
                        for(Plato plato : response.body())
                        {
                            Boolean m2 = plato.getM2();
                            if(m2)
                            {
                                prefiltro.add(plato);
                            }


                        }

                        break;

                    case "3" :
                        for(Plato plato : response.body())
                        {
                            Boolean m3 = plato.getM3();
                            if(m3)
                            {
                                prefiltro.add(plato);
                            }


                        }

                        break;


                    default:
                        break;
                }

                Collections.sort(prefiltro, new Subcaterator());

                for (Plato plato : prefiltro) {
                    myDB.addPlato(plato);
                }

                lista_Plato = myDB.listPlatos();


                Boolean lanzar = false;

                for (Plato plato : lista_Plato)
                {

                    if (plato.getIdioma().equals("español"))
                    {

                        File file = new File(String.valueOf(downloadfolder), plato.getCategoria_plato() + ".jpg");

                        if(!file.exists())
                        {
                            startDownload(plato.getCategoria_plato() + ".jpg", downloadfolder);
                        }

                        File file2 = new File(String.valueOf(downloadfolder), plato.getSub_categoria_plato() + ".jpg");

                        if(!file2.exists())
                        {
                            if(!plato.getSub_categoria_plato().contains("nada"))
                            {
                                startDownload(plato.getSub_categoria_plato()+ ".jpg", downloadfolder);
                            }

                        }

                        File file3 = new File(String.valueOf(downloadfolder), plato.getFoto_movil());

                        if(!file3.exists())
                        {
                            startDownload(plato.getFoto_movil(), downloadfolder);
                        }

                    }

                    if (lista_Plato.indexOf(plato) == lista_Plato.size() - 1)
                    {
                        lanzar = true;
                    }

                }

                if (lanzar)
                {
                    Boolean ejecuta = true;

                    if(myDB.listTablet().getNro_tablet()== null)
                    {
                        ejecuta = false;
                        showmessage("Ingrese Número de mesa");
                        txt_mesa.setVisibility(View.VISIBLE);
                        btn_mesa.setVisibility(View.VISIBLE);

                        btn_mesa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                //RESTRINGIR EL NUMERO DE MESA A LA CANTIDAD REAL DEL CLIENTE
                                String compa = txt_mesa.getText() + "";
                                if(compa.equals(""))
                                {
                                    showmessage("Ingrese Número de mesa");
                                }
                                else
                                {
                                    Tablet tablet = new Tablet();
                                    tablet.setNro_mesa(txt_mesa.getText() + "");
                                    tablet.setNro_tablet(txt_mesa.getText() + "");
                                    myDB.addMesa(tablet);

                                    Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                    }

                    if (ejecuta)
                    {
                        Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Plato>> call, @NonNull Throwable t) {
                mostrarMensaje("Error: " + t.getMessage());
            }
        });
    }

    private void loadretrofitmenu()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequesstMensaje request = retrofit.create(IRequesstMensaje.class);

        Call<ArrayList<Menua>> call = request.getJSONMenues();

        call.enqueue(new Callback<ArrayList<Menua>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Menua>> call, @NonNull Response<ArrayList<Menua>> response)
            {


                ArrayList<Menua> list =response.body();


                myDB.deleteAllMenua();

                for(Menua entra : list)
                {
                 myDB.addMenua(entra);
                }



            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Menua>> call, @NonNull Throwable t)
            {

                Log.i("LA CAGADA", "onFailure: " + t);
            }
        });

    }

    private void startDownload(String filePath, File targetDirectory)
    {
        DownloadRemoteFileOperation downloadOperation = new DownloadRemoteFileOperation(filePath, targetDirectory.getAbsolutePath() + "/");
        downloadOperation.addDatatransferProgressListener(this);
        downloadOperation.execute( mClient, this, mHandler);
    }

    @Override
    public void onTransferProgress(long l, long l1, long l2, String s)
    {

        mHandler.post( new Runnable() {
            @Override
            public void run() {

                    loading.show();
            }
        });

    }

    @Override
    public void onRemoteOperationFinish(RemoteOperation remoteOperation, RemoteOperationResult remoteOperationResult)
    {
        if (remoteOperation instanceof DownloadRemoteFileOperation)
        {
            if (remoteOperationResult.isSuccess())
            {
                //do your coding here
            }
        }
    }

    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }


}


