package com.peerreview.controller;

import com.peerreview.service.ReadDataFromExcel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/peer-review")
public class PeerReviewController {

	@PostMapping("/validate")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("environment") String environment) {
	    try {
	        // Save the uploaded file temporarily
	        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
	        file.transferTo(tempFile);

	        // Call your validation logic here
	        ReadDataFromExcel.UploadFileAndValidate(tempFile.getAbsolutePath(), environment);  // Pass the environment too

	        return "Validation successful! Output saved at the specified location.";
	    } catch (Exception e) {
	        return "File upload and validation failed: " + e.getMessage();
	    }
	}

}
