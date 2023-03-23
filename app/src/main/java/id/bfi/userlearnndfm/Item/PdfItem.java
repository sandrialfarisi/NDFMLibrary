package id.bfi.userlearnndfm.Item;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class PdfItem {
    private String mName;
    private String mDesc;
    private String mPdfUrl;

    public PdfItem(){

    }

    public PdfItem(String name, String desc, String pdfUrl) {
        if (name.trim().equals("")){
            name = "-";
        }else if (desc.trim().equals("")){
            desc = "-";
        }

        mName = name;
        mDesc = desc;
        mPdfUrl = pdfUrl;
    }

    public PdfItem(String trim, Task<Uri> downloadUrl) {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPdfUrl() {
        return mPdfUrl;
    }

    public void setmPdfUrl(String mPdfUrl) {
        this.mPdfUrl = mPdfUrl;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

}
