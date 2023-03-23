package id.bfi.userlearnndfm.InfoNDFM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import id.bfi.userlearnndfm.R;

public class DetailInfoActivity extends AppCompatActivity {
    public static String EXTRA_LINK = "link";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_DESC = "desc";
    public static String EXTRA_TERM = "term";

    TextView tvTitle, tvDesc, tvTerm;
    ImageView ivImage;
    String link, title, desc, term;
    PhotoView photoView;
    RelativeLayout relativeLayout1;
    NestedScrollView detailinfoactvity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        ivImage = findViewById(R.id.ivFullImage);
        tvTitle = findViewById(R.id.tvFullJudul);
        tvDesc = findViewById(R.id.tvFullDeskripsi);
        tvTerm = findViewById(R.id.tvFullTerm);
        photoView = findViewById(R.id.photo_view);
        relativeLayout1 = findViewById(R.id.relativelayout1);
        detailinfoactvity = findViewById(R.id.detailinfoactivity);


        link = getIntent().getStringExtra(EXTRA_LINK);
        title = getIntent().getStringExtra(EXTRA_TITLE);
        desc = getIntent().getStringExtra(EXTRA_DESC);
        term = getIntent().getStringExtra(EXTRA_TERM);

        Picasso.get()
                .load(link)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.default_image)
                .into(ivImage);

        Picasso.get()
                .load(link)
                .placeholder(R.drawable.default_image)
                .into(photoView);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout1.setVisibility(View.VISIBLE);
                detailinfoactvity.setVisibility(View.INVISIBLE);
            }
        });

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvTerm.setText(term);
    }


    @Override
    public void onBackPressed() {
       if (relativeLayout1.getVisibility() == View.VISIBLE){
           detailinfoactvity.setVisibility(View.VISIBLE);
           relativeLayout1.setVisibility(View.INVISIBLE);
       }else {
        super.onBackPressed();
       }
    }
}
