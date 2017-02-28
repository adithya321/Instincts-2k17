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
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.internal.zzt.TAG;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private List<Event> eventList;
    private Context context;

    public EventsAdapter(Context context, List<Event> Events) {
        eventList = Events;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View EventView = inflater.inflate(R.layout.item_event, parent, false);

        return new ViewHolder(EventView);
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.ViewHolder viewHolder, int position) {
        final Event event = eventList.get(position);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDetailBaseActivity.event = event;
                context.startActivity(new Intent(context, EventDetailActivity.class));
            }
        });

        switch (event.getType()) {
            case "Quiz":
                setImageView(viewHolder.imageView, R.drawable.quiz);
                break;
            case "Fine Arts":
                setImageView(viewHolder.imageView, R.drawable.finearts);
                break;
            case "Variety":
                setImageView(viewHolder.imageView, R.drawable.variety);
                break;
            case "ELC":
                setImageView(viewHolder.imageView, R.drawable.elc);
                break;
            case "Music":
                setImageView(viewHolder.imageView, R.drawable.music);
                break;
            case "Dance":
                setImageView(viewHolder.imageView, R.drawable.dance);
                break;
            case "LOP":
                setImageView(viewHolder.imageView, R.drawable.lop);
                break;
            case "Photography":
                setImageView(viewHolder.imageView, R.drawable.photography);
                break;
            case "Saaral":
                setImageView(viewHolder.imageView, R.drawable.saaral);
                break;
            case "Film Club":
                setImageView(viewHolder.imageView, R.drawable.filmclub);
                break;
            default:
                setImageView(viewHolder.imageView, R.drawable.other);
                break;
        }

        viewHolder.startTime.setText(event.getStartTime().split(" ")[1].substring(0, 5));
        viewHolder.title.setText(event.getName());

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        try {
            Date startTime = sdf1.parse(event.getStartTime());
            Date endTime = sdf1.parse(event.getEndTime());

            viewHolder.description.setText(sdf2.format(startTime)
                    + " - " + sdf2.format(endTime) + " / " + event.getLocation());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private void setImageView(ImageView imageView, int drawable) {
        imageView.setImageDrawable(context.getResources().getDrawable(drawable));
        imageView.setTag(drawable);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;

        private ImageView imageView;
        private TextView startTime;
        private TextView title;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.event_ll);

            imageView = (ImageView) itemView.findViewById(R.id.icon);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
