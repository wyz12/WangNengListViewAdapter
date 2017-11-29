package zxwl.com.wannenglistviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public abstract class ListViewAdapter<T> extends BaseAdapter {


    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected List<T> mData;
    protected int mItemLayoutId;

    public ListViewAdapter(Context mContext, List<T> mData, int mItemLayoutId) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup parent) {
        //实例化一个ViewHolder
        ViewHolder holder = getViewHolder(i, convertView, parent);
        //使用对外公开的convert方法，通过ViewHolder把View找到，通过Item设置值
        convert(holder, getItem(i));
        return holder.getmConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent){
        return ViewHolder.getHolder(mContext, mItemLayoutId, convertView, parent);
    }
    public abstract void convert(ViewHolder holder, T item);


}




