package com.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.test.jiashiapi.Jiashi;
import com.test.jiashiapi.UpdateImgBean;
import com.test.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-20 Time: 14:54
 * ToDo:
 */
public class UpDateImageUtils {
    private Activity activity;
    private ImageView userIcon;
    String session_id;
    private Bitmap photo;
    private String TAG = "123";

    public UpDateImageUtils(Activity activity, ImageView userIcon,String session_id) {
        this.activity = activity;
        this.userIcon = userIcon;
        this.session_id=session_id;
    }


    private void postImg() {
        //http://blog.csdn.net/u010945031/article/details/49475897
        try {
            Map<String, RequestBody> map = new HashMap<>();

            //保存上传图片文件夹路径
            File imgFile = new File(upload_image_url);
            // 非空判断
            if (imgFile != null && session_id != null) {
                //得到用户的id
                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), session_id);
                map.put("session_id", userid);
                // 传递的图片文件
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imgFile);
                map.put("image\"; filename=\"" + imgFile.getName() + "", fileBody);

                //  retrofit2.0开始上传图片
                Jiashi.getGitHubApiInstance()
                        .changeUserIcon(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UpdateImgBean>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted()");
                            }


                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError");
                            }


                            @Override
                            public void onNext(UpdateImgBean cat) {
                                Log.i(TAG, "上传头像返回：" + cat.getStatus());
                                // 显示图片
                                userIcon.setImageBitmap(photo);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
  /*
   * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
   * yourself_sdk_path/docs/reference/android/content/Intent.html
   * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
   * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
   */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, 3);
    }


    public static String upload_image_url = null;
    private static final String IMAGE_UNSPECIFIED = "image/*";

    public void createCameraIntent() {
        //获取保存图片的路径
        upload_image_url = FileUtils.getImageFile().getAbsolutePath();

        //    拍照或者选取图片,只能在这里写，写在别处会出错
        AlertDialog.Builder builder = new AlertDialog.Builder(activity) {
            @Override
            public AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
                return super.setOnDismissListener(onDismissListener);
            }
        };
        builder.setIcon(android.R.drawable.ic_menu_camera);
        builder.setTitle(activity.getString(R.string.app_name));
        builder.setMessage("获取图片");
        builder.setCancelable(false);
        builder.setPositiveButton("拍照",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 拍照
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 下面这句指定调用相机拍照后的照片存储的路径
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(upload_image_url)));
                        activity.startActivityForResult(intent2, 2);
                    }
                });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("相册",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_UNSPECIFIED);
                        activity.startActivityForResult(intent, 1);
                    }
                });
        builder.show();
    }
}
