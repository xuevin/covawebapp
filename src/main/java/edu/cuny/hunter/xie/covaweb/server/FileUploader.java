package edu.cuny.hunter.xie.covaweb.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileParser;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.template.LightweightProfile.StringFormat;

import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.MSAParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.PDBParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;

import static gwtupload.shared.UConsts.PARAM_SHOW;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class FileUploader extends UploadAction {
  private static final long serialVersionUID = 1L;
  
  Hashtable<String,String> receivedContentTypes = new Hashtable<String,String>();
  /**
   * Maintain a list with received files and their content types.
   */
  Hashtable<String,File> receivedFiles = new Hashtable<String,File>();
  
  /**
   * Override executeAction to save the received files in a custom place and
   * delete this items from session.
   */
  @Override
  public String executeAction(HttpServletRequest request,
      List<FileItem> sessionFiles) throws UploadActionException {
    String response = "";
    JSONObject holder = new JSONObject();
    int cont = 0;
    for (FileItem item : sessionFiles) {
      if (false == item.isFormField()) {
        cont++;
        try {
          // / Create a temporary file placed in the default system temp folder
          File file = File.createTempFile("upload-", ".bin");
          item.write(file);
          
          // / Save a list with the received files
          receivedFiles.put(item.getFieldName(), file);
          receivedContentTypes.put(item.getFieldName(), item.getContentType());
          
          
          
          String fileType = request.getParameter("fileType");
          logger.info(fileType);
          //Attempt to parse file as fasta
          //If it is valid then send the sequence to the client
          if(fileType.equals("fasta")){
            ProteinSequence sequence = FastaParser.getProteinSequenceFromFasta(file);
            response+=">";
            response+=sequence.getAccession();
            response+="\n";
            response+= sequence.toString();
            
            //Wrap response in a JSON object
            holder.put("type","fasta");
            holder.put("value", response);
            
          }else if (fileType.equals("pdb")){
            Structure structure = PDBParser.getStructureFromPDBFile(file);
            
            holder.put("type", "pdb");
            holder.put("value",structure.toPDB());
            
          }else if (fileType.equals("msa")){
            //TODO - This only takes in Stockholm formats now. I think it should be able to read more.
            //MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = MSAParser.getMSA(file);
            MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = StockholmParser.getMSA(new FileInputStream(file));
            
            holder.put("type", "msa");
            holder.put("value", msa.toString(StringFormat.ALN));
          }

          
          //response += "File saved as " + file.getAbsolutePath();
          
          /*
           * Do some custom things here
           */
          // Pipeline foo = new Pipeline(file);
          // ExecuteCOVA.getXML(file);          
          
          
        } catch (Exception e) {
          throw new UploadActionException(e);
        }
      }
    }
    
    // / Remove files from session because we have a copy of them
    removeSessionFileItems(request);
    
    //Send a JSON object to the client.
    //type - <fasta, msa, pdb>
    //value - <string>
    return holder.toString();
  }
  
  /**
   * Get the content of an uploaded file.
   */
  @Override
  public void getUploadedFile(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String fieldName = request.getParameter(PARAM_SHOW);
    File f = receivedFiles.get(fieldName);
    if (f != null) {
      response.setContentType(receivedContentTypes.get(fieldName));
      FileInputStream is = new FileInputStream(f);
      copyFromInputStreamToOutputStream(is, response.getOutputStream());
    } else {
      renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
    }
  }
  
  /**
   * Remove a file when the user sends a delete request.
   */
  @Override
  public void removeItem(HttpServletRequest request, String fieldName)
      throws UploadActionException {
    File file = receivedFiles.get(fieldName);
    receivedFiles.remove(fieldName);
    receivedContentTypes.remove(fieldName);
    if (file != null) {
      file.delete();
    }
  }
}
