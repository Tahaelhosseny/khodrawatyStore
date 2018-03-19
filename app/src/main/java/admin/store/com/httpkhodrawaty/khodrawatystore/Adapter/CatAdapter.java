package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.EditCatInfo;
import admin.store.com.httpkhodrawaty.khodrawatystore.ItemsActivity;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.Image;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/17/2018.
 */

public class CatAdapter extends RecyclerView.Adapter <CatAdapter.MyViewHolder>
{

    private Context mContext;
    private List<CategoryModel> categoryModels;


    public CatAdapter(Context mContext, List<CategoryModel> categoryModels)
    {
        this.mContext = mContext;
        this.categoryModels = categoryModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cat_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        holder.title.setText(categoryModels.get(position).getName());
        Picasso.with(mContext).load(categoryModels.get(position).getImage()).placeholder(R.drawable.icons_14).into(holder.imageView);
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mContext.startActivity(new Intent(mContext.getApplicationContext() , EditCatInfo.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id",categoryModels.get(position).getId()).putExtra("name",categoryModels.get(position).getName()).putExtra("image",categoryModels.get(position).getImage()).putExtra("availabilty",categoryModels.get(position).isAvilable()));
            }
        });

        if(categoryModels.get(position).isAvilable().equals("1"))
        {
            holder.imageView22.setBackgroundResource(R.mipmap.act);
        }
        else
        {
            holder.imageView22.setBackgroundResource(R.mipmap.not_act);
        }

        holder.items.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                mContext.startActivity(new Intent(mContext.getApplicationContext(),ItemsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("name",categoryModels.get(position).getName()).putExtra("cat_id",categoryModels.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return categoryModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView ;
        TextView title ;
        Button edit ;
        Button items;
        RadioGroup radioGroup ;
        RadioButton active;
        RadioButton notActive;

        ImageView imageView22;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cat_image);
            title = (TextView) itemView.findViewById(R.id.title);
            edit = (Button) itemView.findViewById(R.id.edit);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.avilabilty);
            active = (RadioButton) itemView.findViewById(R.id.active);
            notActive = (RadioButton) itemView.findViewById(R.id.not_active);
            imageView22 = (ImageView) itemView.findViewById(R.id.imageView22);
            items = (Button) itemView.findViewById(R.id.items);

        }
    }
}
