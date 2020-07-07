package com.generic.httpclient.utils;

import okhttp3.Response;

public final class CloseRequestUtils {

    private CloseRequestUtils() {
    }

    public static void close(Response response) {
        if (response != null && response.body() != null &&
                response.body().source() != null) {
            try {
                response.body().source().close();
            } catch (Exception ignore) {

            }
        }
    }
}
