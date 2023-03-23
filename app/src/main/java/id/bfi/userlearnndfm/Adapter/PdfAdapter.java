package id.bfi.userlearnndfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.bfi.userlearnndfm.Item.PdfItem;
import id.bfi.userlearnndfm.MateriNDFM.DetailPdfActivity;
import id.bfi.userlearnndfm.R;

@SuppressWarnings("unchecked")
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    private Context mContext;
    private List<PdfItem> mUploads;
    private List<PdfItem> mUploadsFilltered;

    public PdfAdapter(Context context, List<PdfItem> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
        this.mUploadsFilltered = uploads;
    }

    @Override
    public PdfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pdf_item, parent, false);
        return new PdfViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PdfViewHolder holder, final int position) {

        final PdfItem uploadCurrent = mUploadsFilltered.get(position);
        holder.textViewName.setText(uploadCurrent.getmName());
        holder.textDescName.setText(uploadCurrent.getmDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, DetailPdfActivity.class);
                i.putExtra("link", uploadCurrent.getmPdfUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploadsFilltered.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textDescName;

        public PdfViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tvTextItem);
            textDescName = itemView.findViewById(R.id.tvTextDesc);
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
                    List<PdfItem> filteredList = new ArrayList<>();
                    for (PdfItem row : mUploads) {
                        if (row.getmName().toLowerCase().contains(charString.toLowerCase())) {
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
                mUploadsFilltered = (ArrayList<PdfItem>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
    // END OF FILTER FUNCTION
}
