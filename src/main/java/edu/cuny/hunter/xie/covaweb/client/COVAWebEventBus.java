package edu.cuny.hunter.xie.covaweb.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

import edu.cuny.hunter.xie.covaweb.client.presenter.COVAPresenter;
import edu.cuny.hunter.xie.covaweb.client.view.COVAView;

@Events(startView = COVAView.class)
public interface COVAWebEventBus extends EventBus {
  
  @Start
  @Event(handlers = {COVAPresenter.class})
  public void start();
}
