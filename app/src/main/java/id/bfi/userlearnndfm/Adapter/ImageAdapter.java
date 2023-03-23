package id.bfi.userlearnndfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.bfi.userlearnndfm.InfoNDFM.DetailInfoActivity;
import id.bfi.userlearnndfm.Item.ImageItem;
import id.bfi.userlearnndfm.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<ImageItem> mUploads;
    private List<ImageItem> mUploadsFilltered;
    private final int limit = 6;

    public ImageAdapter(Context context, List<ImageItem> uploads) {
            this.mContext = context;
            this.mUploads = uploads;
            this.mUploadsFilltered = uploads;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        final ImageItem uploadCurrent = mUploadsFilltered.get(position);
        holder.tvTitle.setText(uploadCurrent.getmTitle());
        holder.tvDesc.setText(uploadCurrent.getmDesc());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.default_image)
                .into(holder.ivImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, DetailInfoActivity.class);
                i.putExtra("link", uploadCurrent.getmImageUrl());
                i.putExtra("title", uploadCurrent.getmTitle());
                i.putExtra("desc", uploadCurrent.getmDesc());
                i.putExtra("term", uploadCurrent.getmTerm());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mUploadsFilltered.size() > limit){
            return limit;
        }else
        {
            return mUploadsFilltered.size();
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvJudulImage);
            tvDesc = itemView.findViewById(R.id.tvDeskripsiImage);
        }
    }
}
