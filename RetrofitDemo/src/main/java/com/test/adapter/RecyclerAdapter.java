package com.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;


public abstract class RecyclerAdapter<T> extends
        RecyclerView.Adapter<MyViewHolder> {

    public OnItemClickListener itemlickCListener;
    public List<T> beans;
    public LayoutInflater inflater;
    public int layoutId;
    public Context context;

    public RecyclerAdapter(Context context, List<T> beans) {
        this.inflater = LayoutInflater.from(context);
        this.beans = beans;
        this.layoutId = setLayoutId();
        this.context = context;
    }


    public abstract int setLayoutId();

    /**
     * @param holder
     * @param bean
     */
    public abstract void setItemViews(MyViewHolder holder, T bean, int position);


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(layoutId, viewGroup, false);
        return new MyViewHolder(context, view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setmPosition(position);

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemlickCListener != null) {

                    itemlickCListener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });
        holder.getItemView().setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub

                if (itemlickCListener != null) {
                    return itemlickCListener.onItemLongClick(v, holder.getLayoutPosition());
                }

                return false;
            }
        });
        setItemViews(holder, beans.get(position), holder.getLayoutPosition());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public T getBean(int position) {
        return beans.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view, int position);
    }

    public void setItemlickCListener(OnItemClickListener itemlickCListener) {
        this.itemlickCListener = itemlickCListener;
    }

    /**
     * 清空数据并刷新适配器
     */
    public void clearList() {
        beans.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 清除旧数据，添加新的数据集合，并刷新数据
     *
     * @param list
     */
    public void clearOldAndAddNew(List<T> list) {
        clearList();
        addNewList(list);
    }

    /**
     * 添加新的数据集合，并刷新数据
     *
     * @param list
     */
    public void addNewList(List<T> list) {
        if (null != list && list.size() > 0) {
            beans.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View itemView;
    public Context context;


    public MyViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.mViews = new SparseArray<View>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public MyViewHolder setText(int viewId, String text) {
        if (text != null && text.trim().length() != 0) {
            TextView tv = getView(viewId);
            tv.setText(text);
        }
        return this;
    }

    /**
     * 给item的textview设置文本,并加粗字体
     *
     * @param viewId
     * @param text
     * @return
     */
    public MyViewHolder setBoldText(int viewId, String text) {
        if (text != null) {
            TextView tv = getView(viewId);
            tv.setText(text);
            tv.getPaint().setFakeBoldText(true);//加粗
        }
        return this;
    }

    public MyViewHolder setImageView(int viewId, String imageUrl) {
        if (imageUrl != null && imageUrl.trim().length() != 0) {
            ImageView imageView = getView(viewId);
            if (imageView != null) {
            }
            Glide.with(context).load(imageUrl).skipMemoryCache(true).centerCrop().into(imageView);
        }
        return this;
    }

    public View getItemView() {
        return itemView;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public int getmPosition() {
        return mPosition;
    }

}
