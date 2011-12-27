package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import covariance.algorithms.ConservationSum;
import covariance.algorithms.ELSCCovariance;
import covariance.algorithms.JavaSCA;
import covariance.algorithms.MICovariance;
import covariance.algorithms.OmesCovariance;

public class ExecuteCOVATest {
  
  private File file;
  
  @Before
  public void setup() {
    file = new File(getClass().getClassLoader().getResource(
        "pnase.txt").getFile());
  }
  
  /**
   * Test to make sure that each line is equivalent in the function and the
   * output.
   * 
   * @throws Exception
   */
  @Test
  public void confirmThatOutputFromJavaSCAIsEquivalentToOriginal()
      throws Exception {
    
    File temp = File.createTempFile("javasca.", ".tmp");
    JavaSCA.main(new String[] {file.toString(), temp.toString()});
    
    List<String> outputFromMain = Files.readLines(temp, Charsets.UTF_8);
    
    List<String> outputFromCustomWrapper = ExecuteCOVA
        .getOutputFromJavaSCA(file);
    
    for (int i = 0; i < outputFromMain.size(); i++) {
      assertEquals(outputFromMain.get(i), outputFromCustomWrapper.get(i));
    }
    
  }
  
  /**
   * Test to make sure that each line has an equivalent value. This function
   * tends to add a '-' infront of zero so each iteration is not identical
   * 
   * @throws Exception
   */
  @Test
  public void confirmThatOutputFromELSCCovarianceIsEquivalentToOriginal()
      throws Exception {
    
    File temp = File.createTempFile("elsc.", ".tmp");
    ELSCCovariance.main(new String[] {file.toString(), temp.toString()});
    
    List<String> outputFromMain = Files.readLines(temp, Charsets.UTF_8);
    
    List<String> outputFromCustomWrapper = ExecuteCOVA
        .getOutputFromELSCCovariance(file);
    
    for (int i = 0; i < outputFromMain.size(); i++) {
      if (i == 0) {
        assertEquals(outputFromMain.get(i), outputFromCustomWrapper.get(i));
      } else {
        assertEquals(Double.parseDouble(outputFromMain.get(i).split("\t")[2]),
            Double.parseDouble(outputFromCustomWrapper.get(i).split("\t")[2]),
            0);
      }
    }
    
  }
  
  @Test
  public void confirmThatOutputFromMICovarianceIsEquivalentToOriginal()
      throws Exception {
    
    File temp = File.createTempFile("mi.", ".tmp");
    MICovariance.main(new String[] {file.toString(), temp.toString()});
    
    List<String> outputFromMain = Files.readLines(temp, Charsets.UTF_8);
    
    List<String> outputFromCustomWrapper = ExecuteCOVA
        .getOutputFromMICovariance(file);
    
    for (int i = 0; i < outputFromMain.size(); i++) {
      assertEquals(outputFromMain.get(i), outputFromCustomWrapper.get(i));
    }
  }
  
  @Test
  public void confirmThatOutputFromOmesCovarianceIsEquivalentToOriginal()
      throws Exception {
    
    File temp = File.createTempFile("omes.", ".tmp");
    OmesCovariance.main(new String[] {file.toString(), temp.toString()});
    
    List<String> outputFromMain = Files.readLines(temp, Charsets.UTF_8);
    
    List<String> outputFromCustomWrapper = ExecuteCOVA
        .getOutputFromOmesCovariance(file);
    
    for (int i = 0; i < outputFromMain.size(); i++) {
      assertEquals(outputFromMain.get(i), outputFromCustomWrapper.get(i));
    }
  }
  
  @Test
  public void confirmThatOutputFromConservationSumIsEquivalentToOriginal()
      throws Exception {
    
    File temp = File.createTempFile("csum.", ".tmp");
    ConservationSum.main(new String[] {file.toString(), temp.toString()});
    
    List<String> outputFromMain = Files.readLines(temp, Charsets.UTF_8);
    
    List<String> outputFromCustomWrapper = ExecuteCOVA
        .getOutputFromConservationSum(file);
    
    for (int i = 0; i < outputFromMain.size(); i++) {
      assertEquals(outputFromMain.get(i), outputFromCustomWrapper.get(i));
    }
  }
  @Test
  public void confirmThatAllCanBeCombinedInASingleFunction() throws Exception{
    List<String> list = ExecuteCOVA.getOutputFromAll(file);
    
    for(int i = 0;i<20;i++){
      System.out.println(list.get(i));
    }
    
  }
}
