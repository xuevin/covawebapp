package edu.cuny.hunter.xie.covaweb.shared;

import java.io.Serializable;

public class ResultsDataObject implements Serializable {
  private static final long serialVersionUID = 1L;
  private String alignmentString;
  private String stringifiedLinkedPositionDatabase;
  
  public ResultsDataObject() {
    
  }
  
  public void setAlignmentString(String string) {
    this.alignmentString = string;
  }
  
  public String getAlignmentString() {
    return alignmentString;
  }
  
  public String getStringifiedLinkedPositionDatabase() {
    return stringifiedLinkedPositionDatabase;
  }
  
  public void setStringifiedLinkedPositionDatabase(String linkedPositionDatabase) {
    this.stringifiedLinkedPositionDatabase = linkedPositionDatabase;
  }
  
}
