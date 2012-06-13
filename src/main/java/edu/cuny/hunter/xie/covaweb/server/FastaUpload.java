package edu.cuny.hunter.xie.covaweb.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;


import static gwtupload.shared.UConsts.PARAM_SHOW;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class FastaUpload extends UploadAction {
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
          
          // / Send a customized message to the client.
          response += "File saved as " + file.getAbsolutePath();
          
          /*
           * Do some custom things here
           */
          
          Pipeline foo = new Pipeline(file);
          ExecuteCOVA.getXML(file);
          
          
          
        } catch (Exception e) {
          throw new UploadActionException(e);
        }
      }
    }
    
    // / Remove files from session because we have a copy of them
    removeSessionFileItems(request);
    
    // / Send your customized message to the client.
    return response;
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
