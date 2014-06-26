package com.github.pedrovgs.effectiveandroidui.util;

import javax.inject.Inject;
import java.util.Random;

/**
 * Created by fina on 26/06/14.
 */
public class RandomUtilsRefactored {

    private static final Random random = new Random();

    @Inject public RandomUtilsRefactored(){}

    /**
     * Return true of false using a random value generated and the percentage passed as parameter.
     *
     * @param percentage to evaluate.
     * @return true fifty percent of the times it's executed if the percentage parameter is 50.
     */
    public boolean percent(int percentage) {
        return (random.nextInt(100) < percentage);
    }
}
