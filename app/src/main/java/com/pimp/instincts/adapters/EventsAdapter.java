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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pimp.instincts.R;
import com.pimp.instincts.activities.EventDetailActivity;
import com.pimp.instincts.activities.EventDetailBaseActivity;
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
        final Event event = eventList.get(position);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDetailBaseActivity.event = event;
                context.startActivity(new Intent(context, EventDetailActivity.class)
                        .putExtra("theme", R.style.EventsTheme));
            }
        });

        viewHolder.startTime.setText(event.getStartTime().split(" ")[1].substring(0, 5));
        viewHolder.endTime.setText(event.getEndTime().split(" ")[1].substring(0, 5));
        viewHolder.title.setText(event.getName());
        viewHolder.location.setText(event.getLocation());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView linearLayout;

        private TextView startTime;
        private TextView endTime;
        private TextView title;
        private TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (CardView) itemView.findViewById(R.id.event_ll);

            startTime = (TextView) itemView.findViewById(R.id.start_time);
            endTime = (TextView) itemView.findViewById(R.id.end_time);
            title = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.location);
        }
    }
}
