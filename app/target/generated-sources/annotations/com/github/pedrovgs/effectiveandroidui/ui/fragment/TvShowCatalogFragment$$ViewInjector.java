// Generated code from Butter Knife. Do not modify!
package com.github.pedrovgs.effectiveandroidui.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TvShowCatalogFragment$$ViewInjector {
  public static void inject(Finder finder, final com.github.pedrovgs.effectiveandroidui.ui.fragment.TvShowCatalogFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230721, "field 'pb_loading'");
    target.pb_loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131230723, "field 'gv_tv_shows'");
    target.gv_tv_shows = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2131230722, "field 'v_empty_case'");
    target.v_empty_case = view;
  }

  public static void reset(com.github.pedrovgs.effectiveandroidui.ui.fragment.TvShowCatalogFragment target) {
    target.pb_loading = null;
    target.gv_tv_shows = null;
    target.v_empty_case = null;
  }
}
