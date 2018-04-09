package com.zjk.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by pandengzhe on 2018/4/6.
 */

public class ImageUtil {

    private Context mContext;

    private static ImageUtil instance;

    private ArrayList<String> imageFileNameList;

    private static final Object object = new Object();

    private ImageUtil(Context context) {
        this.mContext = context;
        imageFileNameList = new ArrayList<>();
    }

    public static ImageUtil instance(Context context) {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new ImageUtil(context);
                }
            }
        }
        return instance;
    }

    public ArrayList<String> getImageFileNameList() {
        imageFileNameList.clear();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imageFileNameList.add(new String(data, 0, data.length - 1));
        }
        return imageFileNameList;
    }


}
