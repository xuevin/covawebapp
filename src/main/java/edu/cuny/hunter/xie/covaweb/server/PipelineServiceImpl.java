package edu.cuny.hunter.xie.covaweb.server;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.SimpleSubstitutionMatrix;
import org.biojava3.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cuny.hunter.xie.covaweb.client.service.PipelineService;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineServiceImpl extends RemoteServiceServlet implements
    PipelineService {
  
  static Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);
  
  @Override
  public String runPipeline(DataObject object) throws PipelineException {
    Pipeline pipeline = new Pipeline(object.getQueryString());
    pipeline.run();
    return "Success";
    
  }
  
  /**
   * Parses a simple interleaved alignment file into a MultipleSequenceAlignment
   * 
   * @param string
   *          a string version of the MSA
   * @return a MultipleSequensceAlignment
   */
  public static MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(
      String string) {
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound>();
    
    StringTokenizer tokenizer = new StringTokenizer(string);
    while (tokenizer.hasMoreTokens()) {
      String identifier = tokenizer.nextToken();
      ProteinSequence sequence = new ProteinSequence(tokenizer.nextToken()
          .toUpperCase());
      sequence.setOriginalHeader(identifier);
      sequence.setAccession(new AccessionID(identifier));
      msa.addAlignedSequence(sequence);
    }
    return msa;
    
  }
  
  public static Structure getStructureFromPDB(String string) throws IOException {
    // Write the PDB string to file
    
    File temp = File.createTempFile("covawebapp", "tmp");
    Files.write(string, temp, Charsets.UTF_8);
    
    // Read the Structure from the file
    return getStructureFromPDBFile(temp);
  }
  
  public static Structure getStructureFromPDBFile(File file) throws IOException {
    PDBFileReader reader = new PDBFileReader();
    return reader.getStructure(file);
  }
  
  /**
   * Aligns a protein sequence to the query sequence.
   * 
   * @param query
   *          input query sequence
   * @param inputProtein
   *          input protein
   */
  public static SequencePair<ProteinSequence,AminoAcidCompound> alignPDBSequenceToQuery(
      ProteinSequence query, Structure inputProtein) {
    
    // System.out.println(inputProtein.getChain(0).getAtomGroup(0));
    // System.out.println(inputProtein.getChain(0).getAtomGroup(1));
    // System.out.println(inputProtein.getChain(0).getAtomSequence());
    
    String chainZeroAsString = inputProtein.getChain(0).getAtomSequence();
    
    ProteinSequence chainZero = new ProteinSequence(chainZeroAsString);
    
    SubstitutionMatrix<AminoAcidCompound> matrix = new SimpleSubstitutionMatrix<AminoAcidCompound>();
    SequencePair<ProteinSequence,AminoAcidCompound> pair = Alignments
        .getPairwiseAlignment(query, chainZero,
            PairwiseSequenceAlignerType.GLOBAL, new SimpleGapPenalty(), matrix);
    
    // SimpleAlignedSequence<Sequence<C>,Compound>
    
    // System.out.println("Test of MSA");
    // MultipleSequenceAlignment<AlignedSequence<ProteinSequence,AminoAcidCompound>,AminoAcidCompound>
    // msa = new
    // MultipleSequenceAlignment<AlignedSequence<ProteinSequence,AminoAcidCompound>,AminoAcidCompound>();
    // msa.addAlignedSequence(pair.getQuery());
    // msa.addAlignedSequence(pair.getTarget());
    
    // System.out.println(msa.toString());
    // System.out.println(pair.toString());
    return pair;
    
  }
  
}
