package com.generic.httpclient.interfaces;

import android.graphics.Bitmap;

import com.generic.httpclient.error.ILError;

public interface BitmapRequestListener {

    void onResponse(Bitmap response);

    void onError(ILError anError);

}
