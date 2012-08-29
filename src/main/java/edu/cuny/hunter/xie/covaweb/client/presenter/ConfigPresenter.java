package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.service.PipelineServiceAsync;
import edu.cuny.hunter.xie.covaweb.client.utils.LinkedPositionDatabase;
import edu.cuny.hunter.xie.covaweb.client.view.ConfigView;
import edu.cuny.hunter.xie.covaweb.shared.LoadDataObject;
import edu.cuny.hunter.xie.covaweb.shared.ResultsDataObject;
import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;

@Presenter(view = ConfigView.class)
public class ConfigPresenter extends BasePresenter<ConfigView,COVAWebEventBus> {
  
  public interface IConfigView {
    public MultiUploader getFastaUploader();
    
    public MultiUploader getPDBUploader();
    
    public MultiUploader getMSAUploader();
    
    public TextArea getFastaTextArea();
    
    public TextArea getMSATextArea();
    
    public TextArea getPDBTextArea();
    
    public Button getSubmitButton();
    
    public Label getResponseLabel();
    
  }
  
  Logger logger = Logger.getLogger(getClass().toString());
  
  @Inject
  private PipelineServiceAsync preprocessService;
  
  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
    
    public void onFinish(IUploader fastaUploader) {
      if (fastaUploader.getStatus() == Status.SUCCESS) {
        UploadedInfo info = fastaUploader.getServerInfo();
        JSONObject jsonObj = JSONParser.parseStrict(info.message).isObject();
        
        String type = jsonObj.get("type").isString().stringValue();
        String value = jsonObj.get("value").isString().stringValue();
        
        if (type.equals("fasta")) {
          view.getFastaTextArea().setText(value);
        } else if (type.equals("pdb")) {
          view.getPDBTextArea().setText(value);
        } else if (type.equals("msa")) {
          view.getMSATextArea().setText(value);
        }
        
        logger.info("Upload Successful");
      } else {
        logger.info("Upload Failed");
      }
    }
  };
  
  private ClickHandler submitButtonHandeler = new ClickHandler() {
    
    @Override
    public void onClick(ClickEvent event) {
      // The object that is sent to the server
      LoadDataObject dataObject = null;
      
      if (view.getFastaUploader().getStatus() == Status.UNINITIALIZED) {
        logger.info("Loading Files Via Copy/Paste");
        String fastaText = view.getFastaTextArea().getText();
        String pdbText = view.getPDBTextArea().getText();
        String msaText = view.getMSATextArea().getText();
        
        if (fastaText.length() == 0) {
          logger.info("Need query sequence! EXITING");
          view.getResponseLabel().setText("Need query sequence!");
          return;
        } else {
          if (pdbText.length() == 0 && msaText.length() == 0) {
            dataObject = new LoadDataObject();
          } else {
            dataObject = new LoadDataObject(fastaText, pdbText, msaText);
          }
        }
        
        if (pdbText != null) {
          eventBus.proteinLoad(pdbText);
        }
        
        AsyncCallback<ResultsDataObject> callback = new AsyncCallback<ResultsDataObject>() {
          @Override
          public void onSuccess(ResultsDataObject result) {
            eventBus.alignmentResultsReady(result.getAlignmentString());
            eventBus.covaResultsReady(new LinkedPositionDatabase(result.getCovaDataList(),result.getAlignmentPosToPdbPosMapping()));
          }
          
          @Override
          public void onFailure(Throwable caught) {
            logger.info("RPC failed: " + caught.getMessage());
            eventBus
                .alignmentResultsReady("RPC failed: " + caught.getLocalizedMessage());
          }
        };
        preprocessService.runPipeline(dataObject, callback);
        
      } else {
        view.getFastaUploader().submit();
      }
      
    }
    
  };
  
  public void onStart() {
    view.getSubmitButton().addClickHandler(submitButtonHandeler);
    
    view.getFastaUploader().setServletPath(view.getFastaUploader().getServletPath() + "?fileType=fasta");
    view.getFastaUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
    
    view.getMSAUploader().setServletPath(view.getMSAUploader().getServletPath() + "?fileType=msa");
    view.getMSAUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
    
    view.getPDBUploader().setServletPath(view.getPDBUploader().getServletPath() + "?fileType=pdb");
    view.getPDBUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
    
  }
}
