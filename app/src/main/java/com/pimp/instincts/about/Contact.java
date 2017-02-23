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

package com.pimp.instincts.about;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * @author drakeet
 */
public class Contact {
    @DrawableRes
    public final int avatarResId;
    @NonNull
    public final String name;
    @NonNull
    public final String number;

    public Contact(@DrawableRes int avatarResId, @NonNull String name, @NonNull String number) {
        this.avatarResId = avatarResId;
        this.name = name;
        this.number = number;
    }
}