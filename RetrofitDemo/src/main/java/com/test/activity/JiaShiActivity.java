package com.test.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.test.glide.GlideCircleTransform;
import com.test.R;
import com.test.jiashiapi.Cat;
import com.test.jiashiapi.Jiashi;
import com.test.jiashiapi.LoginBean;
import com.test.jiashiapi.UpdateImgBean;
import com.test.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.OnShowRationale;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JiaShiActivity extends BaseActivity{
    //用户id
    String session_id;
    // 用户头像
    @Bind(R.id.jiashi_img)
    ImageView mImageView;

    private Bitmap photo;
    private String TAG = "123";

    /*zhe*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiashi);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.jiashi_img, R.id.jiashi_login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            // 换头像
            case R.id.jiashi_img:
                if (session_id != null) {
                    createCameraIntent();
                    insertDummyContactWrapper(android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.WRITE_CONTACTS);
                }
                break;
            //  登录
            case R.id.jiashi_login_btn:
                if (session_id == null) {
                    login();
                }
                break;
        }
    }

    private void showUserIcon(String str) {
    /**/
        Glide.with(this)
                //   url
                .load("http://www.jiashi51.com/" + str)
                        //   占位图
                .placeholder(R.mipmap.ic_launcher)
                        //        //   错误图
                .error(R.mipmap.ic_launcher)
                        //圆形图片
                .transform(new GlideCircleTransform(this))
                        //   显示scletype，圆角图片的话，如果加了这就话就会出错
                        //.centerCrop()
                        //   图片容器
                .into(mImageView);
    }

    private void login() {
        Jiashi.getGitHubApiInstance()
                .login2("15986302511", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean != null && loginBean.getInfo() != null && loginBean.getInfo().getSession_id() != null) {
                            session_id = loginBean.getInfo().getSession_id();
                            showUserIcon(loginBean.getInfo().getHead());
                            Log.i(TAG, "session_id:" + session_id);
                        }
                    }
                });

        Jiashi.getGitHubApiInstance()
                .cat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cat>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted()");
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }


                    @Override
                    public void onNext(Cat cat) {
                        Log.i(TAG, cat.toString());
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case 2:
                if (new File(UPLOAD_IMAGE_URL).exists()) {
                    File temp = new File(UPLOAD_IMAGE_URL);
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 * 非空判断大家一定要验证，如果不验证的话,在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
                 *
                 */
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        // 先保存下图片等会显示用
                        photo = extras.getParcelable("data");
                        // 保存图片,一定要写这句话，否则文件UPLOAD_IMAGE_URL为空文件
                        FileUtils.saveBitmap(UPLOAD_IMAGE_URL, photo);
                        // 上传图片
                        postImg();
                        Log.i(TAG, UPLOAD_IMAGE_URL);
                    }
                }
                break;
        }
    }


    private void postImg() {
        //http://blog.csdn.net/u010945031/article/details/49475897
        try {
            Map<String, RequestBody> map = new HashMap<>();

            //保存上传图片文件夹路径
            File imgFile = new File(UPLOAD_IMAGE_URL);
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
//                                mImageView.setImageBitmap(photo);
                                String imageUri = "file://" + UPLOAD_IMAGE_URL; // from SD card
                                Glide.with(JiaShiActivity.this).load(imageUri).transform(new GlideCircleTransform(JiaShiActivity.this)).into(mImageView);
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
        startActivityForResult(intent, 3);
    }


    public static String UPLOAD_IMAGE_URL = null;
    private static final String IMAGE_UNSPECIFIED = "image/*";


    public void createCameraIntent() {
        //获取保存图片的路径
        UPLOAD_IMAGE_URL = FileUtils.getImageFile().getAbsolutePath();

        //    拍照或者选取图片,只能在这里写，写在别处会出错
        AlertDialog.Builder builder = new AlertDialog.Builder(this) {
            @Override
            public AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
                return super.setOnDismissListener(onDismissListener);
            }
        };
        builder.setIcon(android.R.drawable.ic_menu_camera);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("获取图片");
        builder.setCancelable(false);
        builder.setPositiveButton("拍照",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 拍照
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 下面这句指定调用相机拍照后的照片存储的路径
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(UPLOAD_IMAGE_URL)));
                        startActivityForResult(intent2, 2);

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
                        startActivityForResult(intent, 1);
                    }
                });
        builder.show();
    }


}
