package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import edu.cuny.hunter.xie.covaweb.server.local.RunHMMER;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;

public class MapperToolTest {
  
  @Before
  public void setUp() throws Exception {}
  
  @Test
  public void testAlignTwoMSAToQuery() throws IOException, IllegalArgumentException {
    
    // Get query aligned to HMM
    RunHMMER runHmmer = new RunHMMER();
    runHmmer.run();
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA = runHmmer
        .getMSA();
    // System.out.println(hmmMSA.getAlignedSequence(hmmMSA.getSize()).getLength());
    
    // get protein aligned to query
    String string = Files.toString(new File(getClass().getClassLoader()
        .getResource("NP_001096969.fa").getPath()), Charsets.UTF_8);
    ProteinSequence sequence = FastaParser.getProteinSequenceFromFasta(string);
    File pdbFile = new File(getClass().getClassLoader().getResource(
        "NP_001096969.pdb").getPath());
    Structure protein = PipelineServiceImpl.getStructureFromPDBFile(pdbFile);
    
    SequencePair<ProteinSequence,AminoAcidCompound> queryTo_PDB_Pair = PipelineServiceImpl
        .alignPDBSequenceToQuery(sequence, protein);
    
    MapperTool mapper = new MapperTool();
    mapper.alignTwoMSAToQuery(queryTo_PDB_Pair, hmmMSA);
    
  }
}
