package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.cuny.hunter.xie.covaweb.client.presenter.GridPresenter;

@Singleton
public class GridView extends Composite implements GridPresenter.IGridView {
  
  interface MyUiBinder extends UiBinder<Widget,GridView> {};
  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField
  HTML textPart;
  @UiField
  DockLayoutPanel panel;
  @UiField
  HTMLPanel pager;
  
  public GridView() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public HTML getLabel() {
    return textPart;
  }
  
  @Override
  public DockLayoutPanel getPanel() {
    return panel;
  }

  @Override
  public HTMLPanel getPagerPanel() {
    return pager;
  }
  
}
