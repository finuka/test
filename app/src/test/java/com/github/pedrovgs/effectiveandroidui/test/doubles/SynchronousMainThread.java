package com.github.pedrovgs.effectiveandroidui.test.doubles;

import com.github.pedrovgs.effectiveandroidui.executor.MainThread;

/**
 * Created by fina on 26/06/14.
 */
public class SynchronousMainThread implements MainThread {

    @Override public void post(Runnable runnable) {
        runnable.run();
    }
}
