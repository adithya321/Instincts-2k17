/*
 * Instincts
 * Copyright (C) 2017  Adithya J
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.pimp.instincts.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {

    private ArrayList<String> imagesList;
    private Context context;
    private int screenWidth;

    public GalleryAdapter(Context context, ArrayList<String> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public GalleryAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.item_gallery, parent, false);
        return new PhotoViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.PhotoViewHolder viewHolder, int position) {
        String url = imagesList.get(position);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagesList.get(position), opts);
        opts.inJustDecodeBounds = false;
        int height;
        if (position == 1 || position == (imagesList.size() - 1)) {
            height = 150;
        } else {
            height = 300;
        }
        Picasso.with(context)
                .load(url)
                .resize(screenWidth / 2, height)
                .centerCrop()
                .into((viewHolder.imageView));

        /*Glide.with(context)
                .load(url)
                .asBitmap()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        if (bitmap != null) {
                            viewHolder.imageView.setHeightRatio(bitmap.getHeight() / bitmap.getWidth());
                            viewHolder.imageView.setImageBitmap(bitmap);
                        }
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.gallery_image);
        }
    }
}
