package id.bfi.userlearnndfm.Item;

import com.google.firebase.database.Exclude;

public class ImageItem {
    private String mTitle;
    private String mDesc;
    private String mTerm;
    private String mImageUrl;

    public ImageItem(){

    }

    public ImageItem(String title, String desc, String term, String imageUrl) {
        if (title.trim().equals("")) {
            title = "-";
        }else if (desc.trim().equals("")){
            desc = "-";
        }else if (term.trim().equals("")){
            term = "-";
        }

        mTitle = title;
        mDesc = desc;
        mTerm = term;
        mImageUrl = imageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmTerm() {
        return mTerm;
    }

    public void setmTerm(String mTerm) {
        this.mTerm = mTerm;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

}
