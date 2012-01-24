package edu.cuny.hunter.xie.covaweb.server;

import java.util.HashMap;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.utils.AlignmentUtils;

public class MappedSeq {
  Logger logger = LoggerFactory.getLogger(MappedSeq.class);

  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA;
  
  private HashMap<Integer,Integer> alignedQueryMap;
  private HashMap<Integer,Integer> pdbMap;

  private ProteinSequence queryProteinSequence;
  private ProteinSequence pdbProteinSequence;
  
  
  public MappedSeq(ProteinSequence queryProteinSequence, Structure pdb,
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment) {
    
    SequencePair<ProteinSequence,AminoAcidCompound> queryToPDBPair = AlignmentUtils.alignPDBSequenceToQuery(queryProteinSequence, pdb);

    //assign global variables
    this.hmmMSA = alignment;
    this.queryProteinSequence=queryToPDBPair.getQuery().getOriginalSequence();
    this.pdbProteinSequence=queryToPDBPair.getTarget().getOriginalSequence();

    
    //make new mappings
    this.alignedQueryMap = new HashMap<Integer,Integer>();
    this.pdbMap = new HashMap<Integer,Integer>();
    
    AlignedSequence<ProteinSequence,AminoAcidCompound> querySeq = queryToPDBPair.getQuery();
    ProteinSequence alignedQuerySeq = hmmMSA.getAlignedSequence(hmmMSA.getSize()); // Sequence at last index is the aligned sequence
    
    logger.debug("AlignedQuery:\t\t" + alignedQuerySeq);
    logger.debug("OriginalQuery:\t" + queryToPDBPair.getQuery());
    logger.debug("PDB:\t\t\t" + queryToPDBPair.getTarget());
    
    
    
    
    
    
    //Iterate through the protein sequence to match up the corresponding positions
    for(int i = 1;i<=queryToPDBPair.getTarget().getOriginalSequence().getLength();i++){
      //Key is the index in the queryProteinSequence, Value is the index in the PDB 
      pdbMap.put(queryToPDBPair.getIndexInQueryForTargetAt(i), i);
    }
    
    
    // Iterate through the query sequence and get the corresponding position in
    // the hmmMSA.
    try {
      int positionInHMM = 1;
      for (int i = 1; i <= querySeq.getLength(); i++) {
        if (alignedQuerySeq.getCompoundAt(positionInHMM).equals(
            querySeq.getCompoundAt(i))) {
          alignedQueryMap.put(i, positionInHMM);
          
          
        } else {
          while (!alignedQuerySeq.getCompoundAt(positionInHMM).equals(
              querySeq.getCompoundAt(i))) {
            positionInHMM++;
          }
          alignedQueryMap.put(i, positionInHMM);
        }
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The original query sequence was not found in the alignment!",e);
    }
    
  }
  
  public int getIndexInPDBForQueryAt(int queryIndex) {
    if(pdbMap.get(queryIndex)==null){
      return -1;
    }else{
      return pdbMap.get(queryIndex);
    }
  }
  
  public int getIndexInHmmMSAForQueryAt(int queryIndex) {
    if(alignedQueryMap.get(queryIndex)==null){
      return -1;
    }else{
      return alignedQueryMap.get(queryIndex);
    }
  }
  
  public ProteinSequence getQuerySequence() {
    return queryProteinSequence;
  }
  
  public ProteinSequence getPDBSequence() {
    return pdbProteinSequence;
  }
  
  public MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getHmmMSA() {
    return hmmMSA;
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QueryIndex\tAlignmentIndex\tPDBIndex\tCompound\n");
    for(int i=1;i<=queryProteinSequence.getLength();i++){
      builder.append(i+"\t\t"+alignedQueryMap.get(i)+"\t\t"+pdbMap.get(i)+"\t\t"+queryProteinSequence.getCompoundAt(i)+"\n");
    }
    return builder.toString(); 
  }
  
}
