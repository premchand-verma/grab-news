package com.generic.httpclient.common;

import java.util.concurrent.Executor;

public interface RequestBuilder {

    RequestBuilder setPriority(Priority priority);

    RequestBuilder setTag(Object tag);

    RequestBuilder doNotCacheResponse();

    RequestBuilder getResponseOnlyIfCached();

    RequestBuilder getResponseOnlyFromNetwork();

    RequestBuilder setExecutor(Executor executor);

    RequestBuilder setUserAgent(String userAgent);

}
