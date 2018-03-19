package admin.store.com.httpkhodrawaty.khodrawatystore.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.Image;
import admin.store.com.httpkhodrawaty.khodrawatystore.R;

/**
 * Created by Taha on 3/18/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter <ImageAdapter.MyViewHolder>
{
    private Context mContext;
    private List<Image> imagesModel;


    public ImageAdapter(Context mContext, List<Image> imagesModel )
    {
        this.mContext = mContext;
        this.imagesModel = imagesModel;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.images_layout,parent,false);
        return new ImageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        Picasso.with(mContext).load(imagesModel.get(position).getImage()).into(holder.imageView);
        holder.checkBox.setChecked(imagesModel.get(position).getChecked());

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0 ; i < imagesModel.size() ; i++)
                {
                    imagesModel.get(i).setChecked(false);
                }
                imagesModel.get(position).setChecked(true);
                update();
            }

        });
    }


    private void update()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return imagesModel.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView ;
        CheckBox checkBox ;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }


    public List<Image> getList ()
    {
        return imagesModel;
    }
}
