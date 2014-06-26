package com.github.pedrovgs.effectiveandroidui.test.doubles;

import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.executor.Interactor;

/**
 * Created by fina on 26/06/14.
 */
public class SynchronousExecutor implements Executor {

    @Override public void run(Interactor interactor) {
        interactor.run();
    }
}
