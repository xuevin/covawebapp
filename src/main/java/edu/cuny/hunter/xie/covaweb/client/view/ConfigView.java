package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import edu.cuny.hunter.xie.covaweb.client.presenter.ConfigPresenter;
import gwtupload.client.MultiUploader;

@Singleton
public class ConfigView extends Composite implements
    ConfigPresenter.IConfigView {
  interface MyUiBinder extends UiBinder<Widget,ConfigView> {};
  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField
  MultiUploader fastaUploader;
  @UiField
  TextArea fastaTextArea;
  @UiField
  TextArea msaTextArea;
  @UiField
  TextArea pdbTextArea;
  @UiField
  Button fastaSubmitButton;
  @UiField
  Label responseLabel;
  
  public ConfigView() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public MultiUploader getFastaUploader() {
    return fastaUploader;
  }
  
  @Override
  public TextArea getFastaTextArea() {
    return fastaTextArea;
  }
  
  @Override
  public Button getFastaSubmitButton() {
    return fastaSubmitButton;
  }
  
  @Override
  public TextArea getMSATextArea() {
    return msaTextArea;
  }
  
  @Override
  public TextArea getPDBTextArea() {
    return pdbTextArea;
  }
  
  @Override
  public Label getResponseLabel() {
    return responseLabel;
  }
}
