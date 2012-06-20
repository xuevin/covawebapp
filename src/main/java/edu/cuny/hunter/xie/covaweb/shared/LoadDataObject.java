package edu.cuny.hunter.xie.covaweb.shared;

import java.io.Serializable;

public class LoadDataObject implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String msaString, queryString, pdbString;
  
  public LoadDataObject(){
    
  }
  
  public LoadDataObject(String queryString) {
    this.setQueryString(queryString);

  }
  
  public LoadDataObject(String queryString, String pdbString, String msaString) {
    setMsaString(msaString);
    setPdbString(pdbString);
    setQueryString(queryString);
  }

  public void setMsaString(String msaString) {
    this.msaString = msaString;
  }

  public String getMsaString() {
    return msaString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public String getQueryString() {
    return queryString;
  }

  public void setPdbString(String pdbString) {
    this.pdbString = pdbString;
  }

  public String getPdbString() {
    return pdbString;
  }
  
}
