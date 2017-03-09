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

package com.pimp.instincts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.pimp.instincts.model.Event;
import com.pimp.instincts.ui.detail.Card;
import com.pimp.instincts.ui.detail.CardViewProvider;
import com.pimp.instincts.ui.detail.Category;
import com.pimp.instincts.ui.detail.CategoryViewProvider;
import com.pimp.instincts.ui.detail.Contact;
import com.pimp.instincts.ui.detail.ContactViewProvider;
import com.pimp.instincts.ui.detail.Line;
import com.pimp.instincts.ui.detail.LineViewProvider;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public abstract class EventDetailBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public static Event event;
    protected Toolbar toolbar1, toolbar2;
    protected CollapsingToolbarLayout collapsingToolbar;

    protected Items items;
    protected MultiTypeAdapter adapter;

    protected abstract void onCreateHeader(ImageView icon);

    protected abstract void onItemsCreated(@NonNull Items items);

    @Nullable
    protected CharSequence onCreateTitle() {
        return null;
    }

    protected void onActionClick(View action) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        ImageView icon = (ImageView) findViewById(R.id.icon);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        onCreateHeader(icon);

        final CharSequence title = onCreateTitle();
        if (title != null) {
            collapsingToolbar.setTitle(title);
        }

        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        onSetupRecyclerView(recyclerView);
    }

    private void onSetupRecyclerView(RecyclerView recyclerView) {
        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewProvider());
        adapter.register(Card.class, new CardViewProvider(this));
        adapter.register(Line.class, new LineViewProvider());
        adapter.register(Contact.class, new ContactViewProvider(this));
        items = new Items();
        onItemsCreated(items);
        adapter.setItems(items);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Set the header view background to a given resource and replace the default value
     * ?attr/colorPrimary.
     * The resource should refer to a Drawable object or 0 to remove the background.
     *
     * @param resId The identifier of the resource.
     */
    public void setHeaderBackgroundResource(@DrawableRes int resId) {
        if (collapsingToolbar == null) {
            collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        }
        collapsingToolbar.setContentScrimResource(resId);
        collapsingToolbar.setBackgroundResource(resId);
    }

    public void setHeaderContentColor(@ColorInt int color) {
        collapsingToolbar.setCollapsedTitleTextColor(color);
    }

    /**
     * Set the icon to use for the toolbar's navigation button.
     *
     * @param resId Resource ID of a drawable to set
     */
    public void setNavigationIcon(@DrawableRes int resId) {
        toolbar1.setNavigationIcon(resId);
        toolbar2.setNavigationIcon(resId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.action_map:
                startActivity(new Intent(this, MapsActivity.class)
                        .putExtra("location", event.getLocation()));
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void setTitle(CharSequence title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action:
            case R.id.contact_layout:
                onActionClick(v);
                break;
        }
    }
}
