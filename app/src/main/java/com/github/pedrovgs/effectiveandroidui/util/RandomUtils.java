package com.github.pedrovgs.effectiveandroidui.util;

import javax.inject.Inject;
import java.util.Random;

/**
 * Some utility methods related with the Random class.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public class RandomUtils {

  private static final Random random = new Random();

    @Inject public RandomUtils(){}

  /**
   * Return true of false using a random value generated and the percentage passed as parameter.
   *
   * @param percentage to evaluate.
   * @return true fifty percent of the times it's executed if the percentage parameter is 50.
   * @deprecated Use expectedPercent(int percentage)
   */
  @Deprecated
  public static boolean percent(int percentage) {
    return (random.nextInt(100) < percentage);
  }

  public boolean expectedPercent(int percentage) {
    return (random.nextInt(100) < percentage);
  }
}
