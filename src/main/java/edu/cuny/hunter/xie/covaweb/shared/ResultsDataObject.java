package edu.cuny.hunter.xie.covaweb.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultsDataObject implements IsSerializable {
  private String alignmentString;
  private ArrayList<CovaDataRow> covaDataList;
  
  public ResultsDataObject() {
    
  }
  
  public void setAlignmentString(String string) {
    this.alignmentString = string;
  }
  
  public String getAlignmentString() {
    return alignmentString;
  }
  
  public ArrayList<CovaDataRow> getCovaDataList() {
    return covaDataList;
  }
  
  public void setCovaDataObject(ArrayList<CovaDataRow> covaDataList) {
    this.covaDataList = covaDataList;
  }
  
}
