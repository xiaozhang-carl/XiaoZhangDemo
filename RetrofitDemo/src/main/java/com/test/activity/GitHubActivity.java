package com.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.R;
import com.test.adapter.GithubAdapter;
import com.test.githubapi.Contributor;
import com.test.githubapi.GitHub;
import com.test.githubapi.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 10:35
 * ToDo:
 */
public class GitHubActivity extends AppCompatActivity {

    Subscription subscription;


    List<Contributor> contributorList = new ArrayList<>();
    GithubAdapter githubAdapter;
    @Bind(R.id.github_recycleview)
    RecyclerView githubRecycleview;
    private String TAG="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        ButterKnife.bind(this);

        githubAdapter=new GithubAdapter(this,contributorList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        githubRecycleview.setLayoutManager(layoutManager);
        githubRecycleview.setAdapter(githubAdapter);

        //  这样就可以得到数据了，接入rxjava
        Subscriber<List<Contributor>> sub = new Subscriber<List<Contributor>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted() ");
            }


            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.toString());
            }


            @Override
            public void onNext(List<Contributor> someResponse) {
                if (someResponse != null) {
                    Log.i(TAG, someResponse.toString());
                    githubAdapter.addNewList(someResponse);
                }
            }
        };

        GitHub.getGitHubApiInstance()
                .contributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        //.unsubscribeOn(Schedulers.io()).subscribe(sub);
                .subscribe(sub);

        Subscriber<Response> subscriber = new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted() ");
            }


            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.toString());
            }


            @Override
            public void onNext(Response someResponse) {
                if (someResponse != null) {
                    Log.i(TAG, someResponse.toString());
                }
            }
        };

        GitHub.getGitHubApiInstance()
                .someMap(Collections.singletonMap("sort", "desc"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

