package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import edu.cuny.hunter.xie.covaweb.client.presenter.RootPresenter;

public class RootView extends Composite implements RootPresenter.IRootView {
  
  interface MyUiBinder extends UiBinder<Widget,RootView> {};
  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField(provided = true)
  ConfigView configView;
  
  @UiField(provided = true)
  DisplayView displayView;
  
  @UiField(provided = true)
  GridView gridView;
  
  @UiField(provided = true)
  ProteinView proteinView;
  
  @Inject
  public RootView(ConfigView configView, DisplayView displayView,
      GridView gridView, ProteinView proteinView) {
    this.configView = configView;
    this.displayView = displayView;
    this.gridView = gridView;
    this.proteinView = proteinView;
    initWidget(uiBinder.createAndBindUi(this));
  }
}
