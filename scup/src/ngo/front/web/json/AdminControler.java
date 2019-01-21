package ngo.front.web.json;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ngo.front.storage.entity.UpgradePack;
import ngo.front.web.service.CourseService;
import ngo.front.web.service.UpgradeService;

@Controller
public class AdminControler {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UpgradeService upgradeService;

	@RequestMapping(value = "/admin/aupload", method = RequestMethod.POST)
	public ResponseEntity<String> activityUpload(HttpServletRequest request) throws IllegalStateException, IOException {

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {
			
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String path = "/cpack" + file.getOriginalFilename();
					
					file.transferTo(new File(path));
				}
			}
		}
		return ResponseEntity.ok("activity upload done");
	}
	
	@RequestMapping(value = "/admin/aregist", method = RequestMethod.GET)
    public ResponseEntity<String> request(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("aid") String aid, 
    		@RequestParam("aname") String aname,
    		@RequestParam("atype") String atype
    		) {
		
		
		return ResponseEntity.ok("activity registion done");
	}
	
	
	@RequestMapping(value = "/admin/cmupload", method = RequestMethod.POST)
	public ResponseEntity<String> cmetaUpload(HttpServletRequest request) throws IllegalStateException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	    if (isMultipart) {
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);

		    try {
		        List items = upload.parseRequest(request);
		        Iterator iterator = items.iterator();
		        while (iterator.hasNext()) {
		            FileItem item = (FileItem) iterator.next();
	
		            if (!item.isFormField()) {
		                String fileName = item.getName();
	
		                String root = request.getServletContext().getRealPath("/");
		                File path = new File(root + "/cmeta");
		                if (!path.exists()) {
		                    boolean status = path.mkdirs();
		                }
	
		                File uploadedFile = new File(path + "/" + fileName);
		                System.out.println(uploadedFile.getAbsolutePath());
		                item.write(uploadedFile);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		    }
	    }
		return ResponseEntity.ok("course meta upload done");
	}
	
	@RequestMapping(value = "/admin/cmregist", method = RequestMethod.GET)
    public ResponseEntity<String> cmregist(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("cmkey") String cmkey, 
    		@RequestParam("cmpath") String cmurl
    		) {
		
		String cmkeyServer = upgradeService.getUpgradePackCmkey();
		if (cmkey.equals(cmkeyServer)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Not Modified.");
		}
		UpgradePack pack = new UpgradePack(cmkey, cmurl);
		upgradeService.updateUpgradePackObject(pack);
		return ResponseEntity.ok("course meta registion done");
	}
}
