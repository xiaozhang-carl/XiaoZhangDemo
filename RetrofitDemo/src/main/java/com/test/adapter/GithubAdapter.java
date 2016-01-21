package com.test.adapter;

import android.content.Context;

import com.test.R;
import com.test.githubapi.Contributor;

import java.util.List;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 11:15
 * ToDo:
 */
public class GithubAdapter extends RecyclerAdapter<Contributor>{
    public GithubAdapter(Context context, List<Contributor> beans) {
        super(context, beans);
    }

    @Override
    public int setLayoutId() {
      return   R.layout.item_github;
    }

    @Override
    public void setItemViews(MyViewHolder holder, Contributor bean, int position) {
        holder.setBoldText(R.id.item_github_name,bean.getLogin());
        holder.setText(R.id.item_github_attr,bean.getHtml_url());
        holder.setImageView(R.id.item_github_img,bean.getAvatar_url());
    }
}
