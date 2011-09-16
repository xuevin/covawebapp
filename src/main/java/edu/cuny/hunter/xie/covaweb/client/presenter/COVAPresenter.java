package edu.cuny.hunter.xie.covaweb.client.presenter;

import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.COVAView;
import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;

@Presenter(view = COVAView.class)
public class COVAPresenter extends
    BasePresenter<COVAPresenter.ICOVAView,COVAWebEventBus> {
  
  public interface ICOVAView {
    
    MultiUploader getMultiUploader();
    
    ListBox getListBox();
    
  }
  
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
  
  public void onStart() {
    view.getListBox().addItem("FOOBAR");
    view.getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
    view.getMultiUploader().addOnChangeUploadHandler(new OnChangeUploaderHandler() {
      
      public void onChange(IUploader uploader) {
        System.out.println("Why won't it submit?!");
        view.getMultiUploader().submit();
      
      }
    });
    
  }
  
}
