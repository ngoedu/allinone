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

import ngo.front.web.service.CourseService;

 
/**
	NGO Kids Math Resource request controller	 
 */
@Controller
public class CourseControler {
 
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final static String CMETA_FILE_PATH = "/cmeta/dat.js";
	private final static String CPACK_FILE_PATH = "/cpack/";
	
	@Autowired
    private CourseService courseService;
    
    @RequestMapping(value = "/json/cmeta", method = RequestMethod.GET)
    public ResponseEntity<String> request(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key, @RequestParam("token") String token) {
        try {
        	//validate token here
        	if (!isTokenValidated(token)) {      		
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid request");
        	}
        	//get latest key of course meta info
        	String courseMetaKey = courseService.getCourseMetaKey();
        	if (key.equals(courseMetaKey)) {
        		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        	} else {
        		String downloadFileUrl = courseService.getCourseMetaFileUrl();
        		return ResponseEntity.ok(downloadFileUrl);
        	}
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    private boolean isTokenValidated(String token) {
    	int idx0, idx1, idx2;
    	idx0 = token.charAt(0) - 48;
    	idx1 = token.charAt(1) - 48;
    	idx2 = token.charAt(2) - 48;
    	boolean result =  token.charAt(idx0) == (char)'N'&& token.charAt(idx1) == (char)'G'&& token.charAt(idx2) == (char)'O';
        return result;
    }
}

