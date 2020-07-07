package com.generic.httpclient.core;

import java.util.concurrent.Executor;

public interface ExecutorSupplier {

    ILExecutor forNetworkTasks();

    ILExecutor forImmediateNetworkTasks();

    Executor forMainThreadTasks();
}
