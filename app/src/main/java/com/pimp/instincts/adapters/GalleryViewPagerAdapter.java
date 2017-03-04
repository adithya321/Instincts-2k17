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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.squareup.picasso.Picasso;

public class GalleryViewPagerAdapter extends PagerAdapter {
    protected LayoutInflater inflater;
    protected Context context;

    public GalleryViewPagerAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 23;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_view_pager_gallery, container, false);

        view.setTag(R.id.index, position);
        ImageView image = (ImageView) view.findViewById(R.id.gallery_image);
        Picasso.with(context)
                .load("http://ssninstincts.org.in/img/gallery/big/" + (position + 1) + ".jpg")
                .into(image);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public View getCurrentView(ViewPager pager) {
        for (int i = 0; i < pager.getChildCount(); i++) {
            if ((int) pager.getChildAt(i).getTag(R.id.index) == pager.getCurrentItem()) {
                return pager.getChildAt(i);
            }
        }

        return null;
    }
}



