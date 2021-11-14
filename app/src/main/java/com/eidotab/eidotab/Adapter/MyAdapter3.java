package com.eidotab.eidotab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.PlatoActivity;
import com.eidotab.eidotab.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.VH>
{


    private Context mContext;
    private ArrayList<Plato> odd;
    private ArrayList<Plato> nood;

    public MyAdapter3(Context context, ArrayList<Plato> odds)
    {
        this.mContext = context;
        this.odd = odds;
        nood = new ArrayList<>();

    }

    public int AddNew(Plato plato)
    {
        nood.add(plato);
        int position = nood.indexOf(plato);
        notifyItemInserted(position);
        return position;
    }

/*    public void Remove(int position)
    {
        nood.remove((position));
        notifyItemRemoved(position);
    }*/

    @Override
    public MyAdapter3.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row3, parent, false);
        return new MyAdapter3.VH(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter3.VH holder, final int position)
    {

        Glide.clear(holder.btn_top);
        Glide.clear(holder.btn_bott);


        Collections.sort(nood);

        ArrayList<Plato> total = new ArrayList<>();

        total.addAll(nood);
        total.addAll(odd);

        final ArrayList<Plato> pasdata = total;

        final int pasapos = total.size() / 2;





        final Plato platou = nood.get(position);

        fillup(platou, holder.btn_top);
        holder.lbl_cate.setText(platou.getNombre_plato().toUpperCase());

        if (odd.size() == nood.size())
        {
            final Plato platod = odd.get(position);
            filldown(platod, holder.btn_bott);
            holder.lbl_cate2.setText(platod.getNombre_plato().toUpperCase());

            holder.btn_bott.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PlatoActivity.class);
                    intent.putExtra("data", pasdata);
                    intent.putExtra("position", position + pasapos);
                    mContext.startActivity(intent);
                }
            });

        }

        if (odd.size() < nood.size())
        {
            if (position < odd.size())
            {
                final Plato platod = odd.get(position);
                filldown(platod, holder.btn_bott);
                holder.lbl_cate2.setText(platod.getNombre_plato().toUpperCase());

                holder.btn_bott.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PlatoActivity.class);
                        intent.putExtra("data", pasdata);
                        intent.putExtra("position", position + pasapos + 1);
                        mContext.startActivity(intent);
                    }
                });

            }
        }

        if (nood.size() > odd.size())
        {
            if (position == nood.size() - 1)
            {
                holder.lyo_infe.setVisibility(View.INVISIBLE);
            }
        }

        holder.btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlatoActivity.class);
                intent.putExtra("data", pasdata);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    private void filldown(Plato plato, CircleImageView btn_bott)
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File d1 = new File(sdcard, plato.getFoto_movil());
            Uri uri = Uri.fromFile(d1);
            Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().dontTransform().into(btn_bott);
        }
    }

    private void fillup(Plato plato, CircleImageView btn_top)
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File d1 = new File(sdcard, plato.getFoto_movil());
            Uri uri = Uri.fromFile(d1);
            Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().dontTransform().into(btn_top);
        }
    }


    @Override
    public int getItemCount()
    {

        //return Integer.MAX_VALUE;
        return nood.size();

    }

/*    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }*/

    public class VH extends RecyclerView.ViewHolder
    {

        LinearLayout p_row3;
        CircleImageView btn_top;
        CircleImageView btn_bott;
        LinearLayout lyo_infe;
        public TextView lbl_cate;
        public TextView lbl_cate2;


/*        int getScreenWidth()
        {

            return Resources.getSystem().getDisplayMetrics().widthPixels;

        }*/

        public VH(View itemView)
        {

            super(itemView);

            p_row3   = itemView.findViewById(R.id.p_row3);
            btn_top  = itemView.findViewById(R.id.btn_top);
            btn_bott = itemView.findViewById(R.id.btn_bott);
            lyo_infe = itemView.findViewById(R.id.lyo_infe);
            lbl_cate = itemView.findViewById(R.id.lbl_cate);
            lbl_cate2 = itemView.findViewById(R.id.lbl_cate2);

            /*int copantalla = 5;
            int dipantalla = getScreenWidth();*/

           // p_row3.setLayoutParams(new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels - 152;

            int copantalla = 5;


            p_row3.setLayoutParams(new LinearLayout.LayoutParams((int) (dpWidth / copantalla), ViewGroup.LayoutParams.MATCH_PARENT));


        }
    }
}
