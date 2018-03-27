package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.EditCatInfo;
import admin.store.com.httpkhodrawaty.khodrawatystore.EditItem;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/18/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.MyViewHolder>
{


    private Activity mContext;
    private List<ItemModel> itemmModels;





    public ItemAdapter(Activity mContext, List<ItemModel> itemmModels)
    {
        this.mContext = mContext;
        this.itemmModels = itemmModels;

    }

    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.MyViewHolder holder,final int position)
    {
        holder.title.setText(itemmModels.get(position).getName());
        holder.price.setText(itemmModels.get(position).getPrice() +" ريال ");
        Picasso.with(mContext).load(itemmModels.get(position).getcLink()).placeholder(R.drawable.icons_14).into(holder.imageView);
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ItemModel itemModel = itemmModels.get(position);
                Intent intent = new Intent(mContext.getApplicationContext() , EditItem.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", itemModel.getId());
                intent.putExtra("name", itemModel.getName());
                intent.putExtra("image", itemModel.getImage());
                intent.putExtra("avilable", itemModel.getAvilable());
                intent.putExtra("price", itemModel.getPrice());
                intent.putExtra("cLink", itemModel.getcLink());
                intent.putExtra("details", itemModel.getDetails());
                intent.putExtra("serverName", itemModel.getServerName());
                intent.putExtra("weight", itemModel.getWeight());
                intent.putExtra("cat" ,itemModel.getCat());
                mContext.getApplicationContext().startActivity(intent);
                mContext.finish();
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
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
            edit = (Button) itemView.findViewById(R.id.item_edit);
            imageView22 = (ImageView) itemView.findViewById(R.id.imageView22);
            items = (Button) itemView.findViewById(R.id.item_edit);
            price = (TextView) itemView.findViewById(R.id.item_price);

        }
    }
}
