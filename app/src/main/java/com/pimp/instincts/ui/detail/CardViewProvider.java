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

package com.pimp.instincts.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pimp.instincts.R;

import me.drakeet.multitype.ItemViewProvider;

import static android.view.View.GONE;

public class CardViewProvider extends ItemViewProvider<Card, CardViewProvider.ViewHolder> {

    private final View.OnClickListener onActionClickListener;

    public CardViewProvider(View.OnClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.detail_page_item_card, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Card card) {
        holder.content.setText(card.content);
        if (card.action == null) holder.action.setVisibility(GONE);
        else holder.action.setText(card.action);
        if (card.title == null) holder.title.setVisibility(GONE);
        else holder.title.setText(card.title);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView action;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            action = (TextView) itemView.findViewById(R.id.action);
            action.setOnClickListener(onActionClickListener);
        }
    }
}