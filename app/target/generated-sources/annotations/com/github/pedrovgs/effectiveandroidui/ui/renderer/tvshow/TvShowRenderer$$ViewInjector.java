// Generated code from Butter Knife. Do not modify!
package com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TvShowRenderer$$ViewInjector {
  public static void inject(Finder finder, final com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow.TvShowRenderer target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230725, "field 'thumbnailImageView' and method 'onThumbnailClicked'");
    target.thumbnailImageView = (android.widget.ImageView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onThumbnailClicked();
        }
      });
    view = finder.findRequiredView(source, 2131230726, "field 'titleTextView'");
    target.titleTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230727, "field 'seasonsCounterTextView'");
    target.seasonsCounterTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131230724, "method 'onBackgroundClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onBackgroundClicked();
        }
      });
  }

  public static void reset(com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow.TvShowRenderer target) {
    target.thumbnailImageView = null;
    target.titleTextView = null;
    target.seasonsCounterTextView = null;
  }
}
