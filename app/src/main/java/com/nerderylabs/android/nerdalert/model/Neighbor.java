/*
 * Copyright (C) 2016 The Nerdery, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nerderylabs.android.nerdalert.model;

import com.google.gson.Gson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Neighbor {

    private String name;

    private String tagline;

    private String encodedPhoto;

    private transient Bitmap bitmap;

    private transient static final Gson gson = new Gson();

    public Neighbor() {
    }

    public Neighbor(String name, String tagline, String encodedPhoto) {
        this.name = name;
        this.tagline = tagline;
        this.encodedPhoto = encodedPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Bitmap getBitmap() {
        // we might not have a decoded bitmap yet (if this object was received from another device)
        if (bitmap == null) {
            bitmap = base64Decode(encodedPhoto);
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.encodedPhoto = base64Encode(bitmap);
    }

    private static String base64Encode(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap photo = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } else {
            return null;
        }
    }

    private static Bitmap base64Decode(String data) {
        if (data != null) {
            byte[] byteArray = Base64.decode(data, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    // two Neighbor objects are equal if all their Strings are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Neighbor neighbor = (Neighbor) o;

        if (name != null ? !name.equals(neighbor.name) : neighbor.name != null) {
            return false;
        }
        if (tagline != null ? !tagline.equals(neighbor.tagline) : neighbor.tagline != null) {
            return false;
        }
        return !(encodedPhoto != null ? !encodedPhoto
                .equals(neighbor.encodedPhoto) : neighbor.encodedPhoto != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (encodedPhoto != null ? encodedPhoto.hashCode() : 0);
        return result;
    }
}
