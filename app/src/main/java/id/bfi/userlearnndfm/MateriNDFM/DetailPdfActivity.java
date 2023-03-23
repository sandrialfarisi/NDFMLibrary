package id.bfi.userlearnndfm.MateriNDFM;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import id.bfi.userlearnndfm.R;


public class DetailPdfActivity extends AppCompatActivity {

    public static String EXTRA_LINK = "link";
    private static final String TAG = DetailPdfActivity.class.getSimpleName();
    PDFView pdfView;
    ImageView imageView;
    RelativeLayout rvPdf;
    Integer pageNumber = 0;
    String link;
    private Bitmap watermarkBitmap;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_detail_pdf);


        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        pdfView = findViewById(R.id.pdfView);
//        imageView = findViewById(R.id.watermark);
//        rvPdf = findViewById(R.id.rvPdf);
        link = getIntent().getStringExtra(EXTRA_LINK);
        new retrievePdfStream().execute(link);
//        watermark();
    }


    class retrievePdfStream extends AsyncTask<String, Void, InputStream> implements OnPageChangeListener, OnLoadCompleteListener {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream stream = null;
            try{
                URL url = new URL (strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    stream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return stream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .enableAntialiasing(true)
                    .scrollHandle(new DefaultScrollHandle(DetailPdfActivity.this))
                    .pageFling(false)
                    .load();

        }

        @Override
        public void onPageChanged(int page, int pageCount) {
            pageNumber = page;
            setTitle(String.format("NDF Motorcylce %s / %s",  page + 1, pageCount));
        }

        @Override
        public void loadComplete(int nbPages) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            PdfDocument.Meta meta = pdfView.getDocumentMeta();
            printBookmarksTree(pdfView.getTableOfContents(), "-");
        }

        public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
            for (PdfDocument.Bookmark b : tree) {

                Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

                if (b.hasChildren()) {
                    printBookmarksTree(b.getChildren(), sep + "-");
                }
            }
        }
    }

}
