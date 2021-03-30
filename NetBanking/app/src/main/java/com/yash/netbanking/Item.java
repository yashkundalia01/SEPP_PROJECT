package com.yash.netbanking;

import android.widget.ImageView;

public class Item {

    int imageId;
    String label;

    public Item(int imageId, String label) {
        this.imageId = imageId;
        this.label = label;
    }

    public int getImageId() {
        return imageId;
    }

    public String getLabel() {
        return label;
    }
}
