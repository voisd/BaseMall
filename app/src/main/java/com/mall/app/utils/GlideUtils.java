package com.mall.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mall.app.basemall.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/6/23.
 */

public class GlideUtils {

    public static void load(Context mContext, int rId, ImageView imageView){
        Glide.with(mContext).load(rId).
                placeholder(R.drawable.load).
                error(R.drawable.load)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .dontAnimate()
                .listener(requestListener)
                .into(imageView);
    }

    public static void load(Context mContext, String path, ImageView imageView){
        Glide.with(mContext).load(path).
                placeholder(R.drawable.load).
                error(R.drawable.load)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .dontAnimate()
                .listener(requestListener)
                .into(imageView);
    }

    public static void load(Context mContext, String path, ImageView imageView,int loadRId){
        Glide.with(mContext).load(path).
                placeholder(loadRId).
                error(loadRId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .dontAnimate()
                .listener(requestListener)
                .into(imageView);
    }

    public static void loadRoung(Context mContext, String path, ImageView imageView){
        Glide.with(mContext).load(path).
                placeholder(R.drawable.load).
                error(R.drawable.load)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .dontAnimate()
                .bitmapTransform(new CropCircleTransformation(mContext))
                .crossFade(1000)
                .listener(requestListener)
                .into(imageView);
    }

    public static void loadRoung(Context mContext, String path, ImageView imageView,int loadRId){
        Glide.with(mContext).load(path).
                placeholder(loadRId).
                error(loadRId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .dontAnimate()
                .bitmapTransform(new CropCircleTransformation(mContext))
                .crossFade(1000)
                .listener(requestListener)
                .into(imageView);
    }

    public static RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

}
