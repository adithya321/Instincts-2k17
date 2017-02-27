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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.pimp.instincts.model.Event;

import java.util.List;

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
        Event event = eventList.get(position);

        switch (event.getType()) {
            case "Quiz":
                setImageView(viewHolder.imageView, R.drawable.quiz_full);
                break;
            case "Fine Arts":
                setImageView(viewHolder.imageView, R.drawable.finearts_full);
                break;
            case "Variety":
                setImageView(viewHolder.imageView, R.drawable.variety_full);
                break;
            case "ELC":
                setImageView(viewHolder.imageView, R.drawable.elc_full);
                break;
            case "Music":
                setImageView(viewHolder.imageView, R.drawable.music_full);
                break;
            case "Dance":
                setImageView(viewHolder.imageView, R.drawable.dance_full);
                break;
            case "LOP":
                setImageView(viewHolder.imageView, R.drawable.lop_full);
                break;
            case "Photography":
                setImageView(viewHolder.imageView, R.drawable.spc_full);
                break;
            case "Saaral":
                setImageView(viewHolder.imageView, R.drawable.saaral_full);
                break;
            case "Film Club":
                setImageView(viewHolder.imageView, R.drawable.filmclub_full);
                break;
        }

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawable = context.getResources().getIdentifier(context.getResources()
                        .getResourceEntryName((int) viewHolder.imageView.getTag()).split("_")[0]
                        + "_pop", "drawable", context.getPackageName());
                viewHolder.imageView.setImageResource(drawable);
            }
        });
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
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.event_image);
        }
    }
}
