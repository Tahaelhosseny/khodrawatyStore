package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.EditCatInfo;
import admin.store.com.httpkhodrawaty.khodrawatystore.EditSalesMan;
import admin.store.com.httpkhodrawaty.khodrawatystore.ItemsActivity;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.SalesManModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;
import admin.store.com.httpkhodrawaty.khodrawatystore.SalesMan;

/**
 * Created by Taha on 3/23/2018.
 */

public class SalesManAdapter  extends RecyclerView.Adapter<SalesManAdapter.MyViewHolder>
{

    private Activity mContext;
    private List<SalesManModel> salesManModels;


    public SalesManAdapter(Activity mContext, List<SalesManModel> salesManModels)
    {
        this.mContext = mContext;
        this.salesManModels = salesManModels;

    }

    @Override
    public SalesManAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.sales_layout,parent,false);
        return new SalesManAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesManAdapter.MyViewHolder holder, final int position)
    {
        holder.name.setText(salesManModels.get(position).getName());
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext , EditSalesMan.class);
                intent.putExtra("name",salesManModels.get(position).getName());
                intent.putExtra("city",salesManModels.get(position).getCity());
                intent.putExtra("city_id",salesManModels.get(position).getCity_id());
                intent.putExtra("email",salesManModels.get(position).getEmail());
                intent.putExtra("password",salesManModels.get(position).getPassword());
                intent.putExtra("phone",salesManModels.get(position).getPhone());
                intent.putExtra("id",salesManModels.get(position).getId());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return salesManModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        //ImageView imageView ;
        TextView name ;
        Button edit ;
        //Button items;
       // RadioGroup radioGroup ;
       // RadioButton active;
       // RadioButton notActive;

        //ImageView imageView22;


        public MyViewHolder(View itemView)
        {
            super(itemView);
           // imageView = (ImageView) itemView.findViewById(R.id.cat_image);
            name = (TextView) itemView.findViewById(R.id.name);
            edit = (Button) itemView.findViewById(R.id.details);
            //radioGroup = (RadioGroup) itemView.findViewById(R.id.avilabilty);
            //active = (RadioButton) itemView.findViewById(R.id.active);
            //notActive = (RadioButton) itemView.findViewById(R.id.not_active);
            //imageView22 = (ImageView) itemView.findViewById(R.id.imageView22);
            //items = (Button) itemView.findViewById(R.id.items);

        }
    }
}
