package com.eidotab.eidotab.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.R;
import com.eidotab.eidotab.SubMainMenuActivity;
import com.eidotab.eidotab.SubMenuActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.VH>
{
    private Context mContext;
    private ArrayList<String> categories;
    private ArrayList<Plato> list_platos;

    public MyAdapter5(Context context, ArrayList<Plato> lista_platos)
    {
        this.list_platos = lista_platos;
        this.mContext = context;
        categories = new ArrayList<>();
    }

    public int AddNew(String categ)
    {
        categories.add(categ);
        int position = categories.indexOf(categ);
        notifyItemInserted(position);
        return position;
    }

    public void Remove(int position)
    {
        categories.remove((position));
        notifyItemRemoved(position);
    }

    @Override
    public MyAdapter5.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
        return new MyAdapter5.VH(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter5.VH holder, int position)
    {
        //position = position % categories.size();
        Collections.sort(categories);
        final String elem = categories.get(position);


        Glide.clear(holder.btn_goto);


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File d1 = new File(sdcard, elem + ".jpg");
            Uri uri = Uri.fromFile(d1);
            Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().dontTransform().into(holder.btn_goto);
        }

        holder.lbl_cate.setText(elem.toUpperCase());

        holder.btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubMenuActivity.class);
                intent.putExtra("data", list_platos);
                intent.putExtra("cate", elem);
                intent.putExtra("swmodo", false);
                mContext.startActivity(intent);
                //((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount()
    {

        //return Integer.MAX_VALUE;
        return categories.size();

    }

    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public class VH extends RecyclerView.ViewHolder
    {
        TextView lbl_cate;
        ImageView btn_goto;
        LinearLayout p_row2;

        public VH(View itemView)
        {
            super(itemView);

            lbl_cate  = (TextView) itemView.findViewById(R.id.lbl_cate);
            btn_goto  = (ImageView) itemView.findViewById(R.id.btn_goto);
            p_row2    = (LinearLayout) itemView.findViewById(R.id.p_row2);

          //  p_row2.setLayoutParams(new LinearLayout.LayoutParams(291, ViewGroup.LayoutParams.MATCH_PARENT)); //285 ancho de la foto



            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels - 152;

            int copantalla = 4;


            p_row2.setLayoutParams(new LinearLayout.LayoutParams((int) (dpWidth / copantalla), ViewGroup.LayoutParams.MATCH_PARENT));






        }
    }
}


