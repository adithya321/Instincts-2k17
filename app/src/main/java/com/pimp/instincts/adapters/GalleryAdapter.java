/*
 * Instincts 2k17
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.pimp.instincts.activities.GalleryViewPagerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {

    private static final String TRANSITION_NAME = "transition";
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
        return new PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gallery,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.PhotoViewHolder viewHolder, final int position) {
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
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public View getViewAtIndex(RecyclerView recycler, int index) {
        if (index >= 0) {
            for (int i = 0; i < recycler.getChildCount(); i++) {
                View child = recycler.getChildAt(i);

                int pos = recycler.getChildAdapterPosition(child);
                if (pos == index) {
                    return child;
                }
            }
        }
        return null;
    }

    private void showPagerActivity(View view, int position) {
        Intent intent = new Intent(context, GalleryViewPagerActivity.class);
        intent.putExtra(GalleryViewPagerActivity.EXTRA_POSITION, position);

        Activity activity = (Activity) context;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                view, TRANSITION_NAME);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.gallery_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPagerActivity(view, getLayoutPosition());
                }
            });
        }
    }
}
