package ngo.front.web.json;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

 
/**
	NGO Kids Math Resource request controller	 
 */
@Controller
public class CourseControler {
 
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final static String FILE_PATH = "/cmeta";
    
    @RequestMapping(value = "/json/course", method = RequestMethod.GET)
    public void request(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key, @RequestParam("token") String token) {
        try {
        	//validate token here
        	if (!isTokenValidated(token))
        		response.sendError(response.SC_BAD_REQUEST);
        	
        	//get latest key of course meta info
        	String courseMetaKey = "";
        	if (key.equals(courseMetaKey)) {
        		response.sendError(response.SC_NOT_MODIFIED);
        	} else {
        		String downloadFile = request.getServletContext().getRealPath(FILE_PATH);
        		InputStream inputStream = new FileInputStream(downloadFile);

    			// Write response
    			OutputStream outStream = response.getOutputStream();
    			IOUtils.copy(inputStream, outStream);
	    	    response.flushBuffer();
        	}
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	try {
				response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			} catch (IOException e1) {
				//ignore it.
			}
        }
    }
    
    private boolean isTokenValidated(String token) {
    	int idx0, idx1, idx2;
    	idx0 = token.charAt(0);
    	idx1 = token.charAt(1);
    	idx2 = token.charAt(2);
    	return (token.charAt(idx0) == 'N' && token.charAt(idx1)== 'G' && token.charAt(idx2) =='O');
        	
    }
}

