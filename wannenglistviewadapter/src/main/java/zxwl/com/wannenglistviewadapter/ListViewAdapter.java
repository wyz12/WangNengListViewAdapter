package zxwl.com.wannenglistviewadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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

class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    //构造函数
    private ViewHolder(Context context, int resLayoutId, ViewGroup parent) {
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    //获取一个ViewHolder
    public static ViewHolder getHolder(Context context, int resLayoutId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, resLayoutId, parent);
        }
        return (ViewHolder) convertView.getTag();
    }

    //通过控件的id获取对应的控件，如果没有则加入mViews;记住 <T extends View> T 这种用法
    public <T extends View> T getItemView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //获得一个convertView
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView赋值
     */
    public ViewHolder setTextView(int viewId, String text) {
        TextView view = getItemView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView赋值——glide
     */
    public ViewHolder setImageBitmap(int viewId, String bitmap,Context context) {
        ImageView view = getItemView(viewId);
        Glide.with(context).load(bitmap).apply(new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop())
                .into(view);


        return this;
    }

}

