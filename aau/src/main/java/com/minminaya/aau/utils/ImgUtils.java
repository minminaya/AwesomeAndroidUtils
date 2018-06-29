package com.minminaya.aau.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>图片的工具类，包括压缩，通过路径加载bitmap等</p>
 *
 * @author minminaya
 * @email minminaya@gmail.com
 * @time Created by 2018/6/29 13:50
 */
public class ImgUtils {

    private static final String TAG = "ImgUtils";

    /**
     * <p>获取图片路径的方法，适用于4.4及以上系统</p>
     *
     * @param context 上下文
     * @param data    onActivityResult回调的Intent对象，此处包含了图片的某种信息
     * @return 返回图片的path路径
     */
    @TargetApi(19)
    public static String handleImagePathForKitKat(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String documentTypeId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentTypeId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentTypeId));
                imagePath = getImagePath(context, contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果不是document类型的Uri,则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        }
        Logger.d(TAG, "path:" + imagePath);
        return imagePath;
    }


    /**
     * <p>通过Uri和selection来获取真实的图片路径</p>
     *
     * @param context
     * @param selection selection
     * @param uri       uri
     * @return 图片的真实路径
     */
    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * <p>通过比例压缩图片</p>
     * <p></p>
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return 返回压缩后的Bitmap对象
     */
    public static Bitmap compressImage(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;
        float ww = pixelW;

        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize);
        return bitmap;
    }


//    /**
//     *
//     * @deprecated 内部NativeBitmap对象缺失
//     * <p>Bitmap对象转换成NativeBitmap对象</p>
//     *
//     * @param bitmap 源Bitmap对象
//     * @return 目标Bitmap对象
//     *
//     */
//    public static NativeBitmap bitmap2NativiBitmap(Bitmap bitmap) {
//        //bitmap转换成新NativeBitmap
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        NativeBitmap nativeBitmap = NativeBitmap.createBitmap(baos.toByteArray(), 0);
//        try {
//            baos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return nativeBitmap;
//    }
}
