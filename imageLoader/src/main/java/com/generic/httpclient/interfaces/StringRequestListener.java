package com.generic.httpclient.interfaces;

import com.generic.httpclient.error.ILError;

public interface StringRequestListener {

    void onResponse(String response);

    void onError(ILError anError);

}
