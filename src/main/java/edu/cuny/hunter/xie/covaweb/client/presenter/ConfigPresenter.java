package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.util.concurrent.Service.State;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.ConfigView;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;
import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;

@Presenter(view = ConfigView.class)
public class ConfigPresenter extends BasePresenter<ConfigView,COVAWebEventBus> {
  
  public interface IConfigView {
    public MultiUploader getFastaUploader();
    
    public TextArea getFastaTextArea();
    
    public TextArea getMSATextArea();
    
    public TextArea getPDBTextArea();
    
    public Button getFastaSubmitButton();
    
    public Label getResponseLabel();
    
  }
  
  Logger logger = Logger.getLogger(getClass().toString());
  
  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
    
    public void onFinish(IUploader uploader) {
      if (uploader.getStatus() == Status.SUCCESS) {
        UploadedInfo info = uploader.getServerInfo();
        System.out.println("Good");
      } else {
        System.out.println("Huh");
      }
    }
  };
  
  private ClickHandler fastaSubmitHandeler = new ClickHandler() {
    
    @Override
    public void onClick(ClickEvent event) {
      
      if (view.getFastaUploader().getStatus() == Status.UNINITIALIZED) {
        logger.info("Loading Files Via Copy/Paste");
        String fastaText = view.getFastaTextArea().getText();
        String pdbText = view.getPDBTextArea().getText();
        String msaText = view.getMSATextArea().getText();
        
        if(fastaText.length()==0){
          logger.info("Need query sequence! EXITING");
          view.getResponseLabel().setText("Need query sequence!");
          return;
        }else{
          if(pdbText.length()==0 && msaText.length()==0){
            //Perform the automated pipeline
            //TODO
          }else{
            //Perform the manual pipeline
            eventBus.dataLoaded(new DataObject(fastaText,pdbText,msaText));
          }
        }

        
      } else {
        view.getFastaUploader().submit();
      }
    }
    
  };
  
  public void onStart() {
    view.getFastaSubmitButton().addClickHandler(fastaSubmitHandeler);
    view.getFastaUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
    
  }
}
