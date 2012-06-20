package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.cuny.hunter.xie.covaweb.client.presenter.DisplayPresenter;

@Singleton
public class DisplayView extends Composite implements
    DisplayPresenter.IDisplayView {
  
  interface MyUiBinder extends UiBinder<Widget,DisplayView> {};

  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField
  Label mainTextLabel;
  @UiField 
  HTML mainTextHTML;
  
  @Inject
  public DisplayView() {
    
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public Label getMainTextLabel() {
    return mainTextLabel;
  }

  @Override
  public HTML getMainTextHTML() {
    return mainTextHTML;
  }
}
