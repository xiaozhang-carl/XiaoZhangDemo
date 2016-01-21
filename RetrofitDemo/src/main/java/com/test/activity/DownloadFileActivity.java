package com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.R;
import com.test.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 09:13
 * ToDo:http://blog.csdn.net/bboyfeiyu/article/details/50512459
 */
public class DownloadFileActivity extends Activity {

    @Bind(R.id.download_btn)
    Button downloadBtn;
    //进度条
    @Bind(R.id.download_progress)
    ProgressBar downloadProgress;

    @Bind(R.id.download_tv)
    TextView downloadTv;
    //下载用的订阅者
    private PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        ButterKnife.bind(this);

        downloadProgress.setMax(100);
        downloadProgress.setProgress(0);
    }

    /*下载文件的*/
    private boolean downloadFile(String source, String destination) {
        boolean result = false;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            int fileLength = connection.getContentLength();
            input = connection.getInputStream();
            //文件位置
            output = new FileOutputStream(FileUtils.getImageCache() + "/" + destination);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                if (fileLength > 0) {
                    int percentage = (int) (total * 100 / fileLength);
                    mDownloadProgress.onNext(percentage);
                }
                output.write(data, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            mDownloadProgress.onError(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                mDownloadProgress.onError(e);
            }
            if (connection != null) {
                connection.disconnect();
                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }


    private Observable<Boolean> obserbableDownload(final String source, final String destination) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = downloadFile(source, destination);
                    if (result) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Throwable("Download failed."));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @OnClick(R.id.download_btn)
    public void onClick() {

        downloadBtn.setClickable(false);
        mDownloadProgress.distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() {
                        Log.i("123", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("123", e.toString());
                    }

                    @Override
                    public void onNext(Integer progress) {
                        Log.i("123", "progress" + progress);
                        downloadTv.setText(progress+"%");
                        downloadProgress.setProgress(progress);
                    }
                });

        final String destination = "sdcardsoftboy.avi";
        obserbableDownload("http://archive.blender.org/fileadmin/movies/softboy.avi", destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadBtn.setClickable(true);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(FileUtils.getImageCache(),
                                destination);
                        intent.setDataAndType(Uri.fromFile(file), "video/avi");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

    }

}
