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
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/18/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.MyViewHolder>
{


    private Context mContext;
    private List<ItemModel> itemmModels;

    public ItemAdapter(Context mContext, List<ItemModel> itemmModels)
    {
        this.mContext = mContext;
        this.itemmModels = itemmModels;
        Toast.makeText(mContext.getApplicationContext() , itemmModels.size() + "" , Toast.LENGTH_SHORT).show();

    }


    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.MyViewHolder holder,final int position)
    {
        holder.title.setText(itemmModels.get(position).getName());
        Picasso.with(mContext).load(itemmModels.get(position).getImage()).placeholder(R.drawable.icons_14).into(holder.imageView);
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mContext.startActivity(new Intent(mContext.getApplicationContext() , EditCatInfo.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id",itemmModels.get(position).getId()).putExtra("name",itemmModels.get(position).getName()).putExtra("image",itemmModels.get(position).getImage()).putExtra("availabilty",itemmModels.get(position).getAvilable()));
            }
        });

        if(itemmModels.get(position).getAvilable().equals("1"))
        {
            holder.imageView22.setBackgroundResource(R.mipmap.act);
        }
        else
        {
            holder.imageView22.setBackgroundResource(R.mipmap.not_act);
        }

    }

    @Override
    public int getItemCount() {
        return itemmModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView ;
        TextView title ;
        Button edit ;
        Button items;
        ImageView imageView22;


        TextView price ;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cat_image);
            title = (TextView) itemView.findViewById(R.id.title);
            edit = (Button) itemView.findViewById(R.id.edit);
            imageView22 = (ImageView) itemView.findViewById(R.id.imageView22);
            items = (Button) itemView.findViewById(R.id.items);
            price = (TextView) itemView.findViewById(R.id.price);

        }
    }
}
