package edu.cuny.hunter.xie.covaweb.server.utils;

import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperUtils {
  static Logger logger = LoggerFactory.getLogger(MapperUtils.class);
  //FIXME Incomplete
  public static void alignTwoMSAToQuery(
      SequencePair<ProteinSequence,AminoAcidCompound> queryTo_PDB_Pair,
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA) {
   
      ProteinSequence allignedQuerySeq = hmmMSA.getAlignedSequence(hmmMSA.getSize());
      AlignedSequence<ProteinSequence,AminoAcidCompound> querySeq = queryTo_PDB_Pair.getQuery();
      logger.debug("AlignedQuery:\t"+allignedQuerySeq);
      logger.debug("OriginalQuery:\t"+queryTo_PDB_Pair.getQuery());
      logger.debug("PDB:\t\t\t"+queryTo_PDB_Pair.getTarget());
      
      
      // Iterate through the query sequence and get the corresponding position in the hmmMSA.
      try{
        int positionInHMM = 1;
        System.out.println("QryPos\tAlndPos\tCompound");
        for(int i =1;i<=querySeq.getLength();i++){
          if(allignedQuerySeq.getCompoundAt(positionInHMM).equals(querySeq.getCompoundAt(i))){
            System.out.println(i+"\t"+positionInHMM + "\t" + querySeq.getCompoundAt(i));
          }else{
            while(!allignedQuerySeq.getCompoundAt(positionInHMM).equals(querySeq.getCompoundAt(i)))
            {
              positionInHMM++;
            }
            System.out.println(i+"\t"+positionInHMM + "\t" + querySeq.getCompoundAt(i));
          }
        }
      }catch(IndexOutOfBoundsException e){
        System.out.println("The query sequence was not found in the MSA!");
      }

  }
}
