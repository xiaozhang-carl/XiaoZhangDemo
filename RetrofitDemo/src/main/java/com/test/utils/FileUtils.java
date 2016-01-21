package com.test.utils;


import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @作者： 张宏强
 * @时间：2015-7-1下午1:36:25
 * @作用：文件工具类
 * @修改人：
 * @修改日期：
 *
 */
public class FileUtils {
    private static String APP_CACHE="defu";
    private static String SYS_CACHE="defu";
    private static String PHOTO_CACHE="img";


    /**
     * 获取应用程序的根文件夹
     *
     * @return
     */
    public static File getAppCache() {

        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取SD卡/qianfeng
            path = Environment.getExternalStorageDirectory() + File.separator + APP_CACHE;
        } else {
            path = Environment.getDataDirectory() + File.separator + SYS_CACHE;
        }
        File appCache = new File(path);
        if (!appCache.exists()) {
            appCache.mkdirs();
        }
        return appCache;
    }

    /**
     * 获取拍照图片文件夹
     *
     * @return
     */

    public static File getImageCache() {

        String imagePath = getAppCache() + File.separator + PHOTO_CACHE;
        File imageCache = new File(imagePath);
        if (!imageCache.exists()) {
            imageCache.mkdirs();
        }

        return imageCache;
    }



    public static File getImageFile() {
        //获取图片保存的文件名称
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png";
        //获取文件
        String imageFile = getImageCache() + File.separator + fileName;
        File file = new File(imageFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }



    // 保存图片
    public static void saveBitmap(String path, Bitmap mBitmap) {
        File f = new File(path);
        try {
   /*
    * if(!f.exists()) { f.mkdir(); }
    */
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
