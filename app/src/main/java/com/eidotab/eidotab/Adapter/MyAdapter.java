/*
package com.eidotab.eidotab.Adapter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eidotab.eidotab.Model.Cuentadata;
import com.eidotab.eidotab.R;
import java.util.ArrayList;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>
{
    private Context mContext;
    public ArrayList<Cuentadata> cdatar;
    public ArrayList<Integer> cdataro;
    private Boolean expan;
    private TextView lb_toti;
    public Button agregar;


    public MyAdapter(Context context, TextView lbl_toti, Button agg )
    {

        this.mContext = context;
        cdataro = new ArrayList<>();
        cdatar = new ArrayList<>();
        expan = false;
        this.lb_toti = lbl_toti;
        this.agregar = agg;

    }

    public int AddNew(Integer ncuenta)
    {
        cdataro.add(ncuenta);
        int position = cdataro.indexOf(ncuenta);
        notifyItemInserted(position);
        return position;


    }

    public void Addplat(Cuentadata cuentadata)
    {
        cdatar.add(cuentadata);
    }

    @Override
    public MyAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.r, parent, false);
        return new MyAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.VH holder, int position)
    {
        final Integer ncuenta = cdataro.get(position);

        holder.ncuenta.setText("CUENTA " + (ncuenta));

        holder. setAdapter();


        holder.setData();








    }

    @Override
    public int getItemCount()
    {

        return cdataro.size();

    }

    public class VH extends RecyclerView.ViewHolder
    {


        ImageView arrow;
        private MyAdapterhijo adp2;
        FrameLayout elpadre;
        TextView ncuenta;
        LinearLayout elhijo;

        private RecyclerView myrecyclerhijo;


        public VH(View itemView)
        {
            super(itemView);

            arrow = itemView.findViewById(R.id.list_item_genre_arrow);
            elpadre = itemView.findViewById(R.id.elpadre);
            ncuenta = itemView.findViewById(R.id.list_item_cuenta_number);
            myrecyclerhijo = itemView.findViewById(R.id.myrecyclerhijo);
            elhijo = itemView.findViewById(R.id.elhijo);





        }

        public void setData() {





            elpadre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!expan)
                    {
                        elhijo.animate()
                                .alpha(1.0f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        elhijo.setVisibility(View.VISIBLE);
                                    }
                                });
                        expan = true;
                        animateExpand();
                    }
                    else
                    {
                        elhijo.animate()
                                .alpha(0.0f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        elhijo.setVisibility(View.GONE);
                                    }
                                });
                        expan = false;
                        animateCollapse();
                    }
                }



            });



            if(cdatar.size() == 1)
            {
                elpadre.performClick();
                for (int i = 0; i <cdatar.size() ; i++) {

                    adp2.AddNew(cdatar.get(i));
                    cdatar.remove(i);

                }
            }
            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int i = 0; i <cdatar.size() ; i++) {


                        adp2.AddNew(cdatar.get(i));
                        cdatar.remove(i);


                    }
                    for(Cuentadata data : cdatar)
                    {
                        adp2.AddNew(data);
                    }
                }
            });

        }
        void setAdapter()
        {
            adp2 = new MyAdapterhijo(mContext, lb_toti);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            myrecyclerhijo.setLayoutManager(linearLayoutManager);
            myrecyclerhijo.setAdapter(adp2);
        }


        private void animateExpand() {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.startAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.startAnimation(rotate);
        }


    }
}


*/
