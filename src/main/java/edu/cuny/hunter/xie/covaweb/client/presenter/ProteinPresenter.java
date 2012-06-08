package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.logging.Logger;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.ProteinView;

@Presenter(view = ProteinView.class)
public class ProteinPresenter extends BasePresenter<ProteinView,COVAWebEventBus>{
  
  public interface IProteinView{
    HTMLPanel getHtmlPanel();
  }
  
  Logger logger = Logger.getLogger(getClass().toString());
  private WebGLRenderingContext glContext;
  private Canvas webGLCanvas;
  
  public void onStart() {
    
    webGLCanvas = Canvas.createIfSupported();
    webGLCanvas.setCoordinateSpaceHeight(500);
    webGLCanvas.setCoordinateSpaceWidth(500);
    glContext = (WebGLRenderingContext) webGLCanvas
        .getContext("experimental-webgl");
    if (glContext == null) {
      Window.alert("Sorry, Your Browser doesn't support WebGL!");
    }
    view.getHtmlPanel().add(webGLCanvas);
  }
}
