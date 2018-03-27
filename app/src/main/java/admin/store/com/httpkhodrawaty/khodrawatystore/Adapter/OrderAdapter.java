package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.InvoiceDetails;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.OrderModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/25/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter <OrderAdapter.MyViewHolder>
{

    private Activity mContext;
    private List<OrderModel> orderModels;


    public OrderAdapter(Activity mContext, List<OrderModel> orderModels)
    {
        this.mContext = mContext;
        this.orderModels = orderModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.order_layout,parent,false);
        return new OrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        holder.invoice.setText(orderModels.get(position).getId() + "#");
        holder.name.setText(orderModels.get(position).getName());
        holder.phone.setText(orderModels.get(position).getPhone());
        holder.price.setText(orderModels.get(position).getPrice()+" ريال ");
        holder.date.setText(orderModels.get(position).getTime());
        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext , InvoiceDetails.class);
                intent.putExtra("id" , orderModels.get(position).getId());
                intent.putExtra("name",orderModels.get(position).getName());
                intent.putExtra("phone" , orderModels.get(position).getPhone());
                intent.putExtra("time" , orderModels.get(position).getTime());
                intent.putExtra("price" , orderModels.get(position).getPrice());
                intent.putExtra("product" , orderModels.get(position).getProduct());
                intent.putExtra("status" , orderModels.get(position).getStatus());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView invoice ;
        TextView name ;
        TextView phone ;
        TextView date;
        TextView price ;

        CardView cardView ;

        Button button ;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            invoice = (TextView) itemView.findViewById(R.id.invoice);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            date = (TextView) itemView.findViewById(R.id.date);
            price = (TextView) itemView.findViewById(R.id.price);
            cardView = (CardView) itemView.findViewById(R.id.details);
            button = (Button) itemView.findViewById(R.id.det);

        }
    }

}
