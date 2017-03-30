package com.qunter.searchcall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.entity.SchoolInfo;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */

public class SchoolInfoListAdapter extends RecyclerView.Adapter<SchoolInfoListAdapter.ViewHolder> {
    private List<SchoolInfo> schoolInfoList;
    private LruCache<String, Bitmap> mImageCache;
    private Context context;
    private RecyclerView recyclerView;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView schoolInfoTitle;
        ImageView schoolInfoImg;
        public ViewHolder(View view){
            super(view);
            schoolInfoTitle = (TextView) view.findViewById(R.id.item_title);
            schoolInfoImg = (ImageView) view.findViewById(R.id.item_image);
        }
    }
    public SchoolInfoListAdapter(Context context,List<SchoolInfo> list,RecyclerView recyclerView){
        this.context = context;
        schoolInfoList = list;
        this.recyclerView = recyclerView;
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_info,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SchoolInfo schoolInfo = schoolInfoList.get(position);
        holder.schoolInfoTitle.setText(schoolInfo.getTitle());
        holder.schoolInfoImg.setTag(schoolInfo.getImgUrl());
        if (mImageCache.get(schoolInfo.getImgUrl()) != null) {
            holder.schoolInfoImg.setImageBitmap(mImageCache.get(schoolInfo.getImgUrl()));
        } else {
            ImageTask it = new ImageTask();
            it.execute(schoolInfo.getImgUrl());
        }
    }

    @Override
    public int getItemCount() {
        return schoolInfoList.size();
    }


    class ImageTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap;
            if (params[0].equals("")){
                bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.account_avatar);
                return bitmap;
            }
            imageUrl = params[0];
            bitmap = downloadImage();
            if (mImageCache.get(imageUrl) == null){
                mImageCache.put(imageUrl, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) recyclerView.findViewWithTag(imageUrl);
            if (iv != null && result != null) {
                iv.setImageBitmap(result);
            }
        }

        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }
}
