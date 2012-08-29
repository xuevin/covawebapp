package edu.cuny.hunter.xie.covaweb.shared;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultsDataObject implements IsSerializable {
  private String alignmentString;
  private ArrayList<CovaDataRow> covaDataList;
  private HashMap<Integer,Integer> alignmentPosToPdbPosMapping;
  
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
  
  public void setAlignmentPosToPdbPosMapping(
      HashMap<Integer,Integer> alignmentPosToPdbPosMapping) {
    this.alignmentPosToPdbPosMapping = alignmentPosToPdbPosMapping;
  }
  
  public HashMap<Integer,Integer> getAlignmentPosToPdbPosMapping() {
    return alignmentPosToPdbPosMapping;
  }
  
}
