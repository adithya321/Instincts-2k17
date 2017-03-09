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

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pimp.instincts.R;
import com.pimp.instincts.activities.EventDetailActivity;
import com.pimp.instincts.activities.EventDetailBaseActivity;
import com.pimp.instincts.model.Event;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Realm;

public class SearchAdapter extends RealmSearchAdapter<Event, SearchAdapter.ViewHolder> {

    private Context context;

    public SearchAdapter(Context context, Realm realm, String filterColumnName) {
        super(context, realm, filterColumnName);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View EventView = inflater.inflate(R.layout.item_schedule, parent, false);

        return new ViewHolder(EventView);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Event event = realmResults.get(position);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDetailBaseActivity.event = event;
                context.startActivity(new Intent(context, EventDetailActivity.class));
            }
        });

        switch (event.getType()) {
            case "Special":
                setImageView(viewHolder.imageView, R.drawable.ic_award);
                break;
            case "Gaming":
                setImageView(viewHolder.imageView, R.drawable.ic_puppet_show);
                break;
            case "Quiz":
                setImageView(viewHolder.imageView, R.drawable.ic_walk_of_fame);
                break;
            case "Fine Arts":
                setImageView(viewHolder.imageView, R.drawable.ic_puppet);
                break;
            case "Variety":
                setImageView(viewHolder.imageView, R.drawable.ic_mask);
                break;
            case "ELC":
                setImageView(viewHolder.imageView, R.drawable.ic_costume);
                break;
            case "Music":
                setImageView(viewHolder.imageView, R.drawable.ic_microphone);
                break;
            case "Dance":
                setImageView(viewHolder.imageView, R.drawable.ic_spotlights);
                break;
            case "LOP":
                setImageView(viewHolder.imageView, R.drawable.ic_stage);
                break;
            case "Photography":
                setImageView(viewHolder.imageView, R.drawable.ic_shutter);
                break;
            case "Saaral":
                setImageView(viewHolder.imageView, R.drawable.ic_writer);
                break;
            case "Film Club":
                setImageView(viewHolder.imageView, R.drawable.ic_clapperboard);
                break;
            default:
                setImageView(viewHolder.imageView, R.drawable.ic_award);
                break;
        }

        viewHolder.startTime.setText(event.getStartTime().split(" ")[1].substring(0, 5));
        viewHolder.endTime.setText(event.getEndTime().split(" ")[1].substring(0, 5));
        viewHolder.title.setText(event.getName());
        viewHolder.location.setText(event.getLocation());
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    private void setImageView(ImageView imageView, int drawable) {
        imageView.setImageDrawable(context.getResources().getDrawable(drawable));
        imageView.setTag(drawable);
    }

    public class ViewHolder extends RealmSearchViewHolder {
        private LinearLayout linearLayout;

        private ImageView imageView;
        private TextView startTime;
        private TextView endTime;
        private TextView title;
        private TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.event_ll);

            imageView = (ImageView) itemView.findViewById(R.id.icon);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
            endTime = (TextView) itemView.findViewById(R.id.end_time);
            title = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.location);
        }
    }
}
