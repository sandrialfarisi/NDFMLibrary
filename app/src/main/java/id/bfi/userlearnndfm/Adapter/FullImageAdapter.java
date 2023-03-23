package id.bfi.userlearnndfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.bfi.userlearnndfm.InfoNDFM.DetailInfoActivity;
import id.bfi.userlearnndfm.Item.ImageItem;
import id.bfi.userlearnndfm.R;

@SuppressWarnings("unchecked")
public class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<ImageItem> mUploads;
    private List<ImageItem> mUploadsFilltered;

    public FullImageAdapter(Context context, List<ImageItem> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
        this.mUploadsFilltered = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.imagefull_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        final ImageItem uploadCurrent = mUploadsFilltered.get(position);
        holder.textViewName.setText(uploadCurrent.getmTitle());
        holder.textDescName.setText(uploadCurrent.getmDesc());
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
        return mUploadsFilltered.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textDescName;
        public ImageView ivImage;

        public ImageViewHolder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            textViewName = itemView.findViewById(R.id.tvJudulImage);
            textDescName = itemView.findViewById(R.id.tvDeskripsiImage);
        }
    }
        // FILTER FUNCTION FOR FILTER THE LIST OF RECYCLERVIEW
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mUploadsFilltered = mUploads;
                } else {
                    List<ImageItem> filteredList = new ArrayList<>();
                    for (ImageItem row : mUploads) {
                        if (row.getmTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mUploadsFilltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mUploadsFilltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mUploadsFilltered = (ArrayList<ImageItem>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
// END OF FILTER FUNCTION
}
