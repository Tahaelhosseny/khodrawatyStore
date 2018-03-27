package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.InvoiceProduct;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/26/2018.
 */

public class InvoiceProductAdapter extends RecyclerView.Adapter <InvoiceProductAdapter.MyViewHolder>
{

    private Activity mContext;
    private List<InvoiceProduct> invoiceProducts;


    public InvoiceProductAdapter(Activity mContext, List<InvoiceProduct> invoiceProducts) {
        this.mContext = mContext;
        this.invoiceProducts = invoiceProducts;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.invoice_item,parent,false);
        return new InvoiceProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        holder.price.setText(invoiceProducts.get(position).getPrice());
        holder.qun.setText(invoiceProducts.get(position).getQuantity());
        holder.pro.setText(invoiceProducts.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return invoiceProducts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
       TextView price ;
       TextView qun ;
       TextView pro ;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            qun = (TextView) itemView.findViewById(R.id.qun);
            pro = (TextView) itemView.findViewById(R.id.pro);

        }
    }
}
