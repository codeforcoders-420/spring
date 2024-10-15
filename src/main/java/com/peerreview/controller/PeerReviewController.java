// PeerReviewController.java
package com.yourpackage.controller;

import com.yourpackage.service.ReadDataFromExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peer-review")
public class PeerReviewController {

    @Autowired
    private ReadDataFromExcel readDataFromExcel;

    @PostMapping("/validate")
    public ResponseEntity<String> validateFile(@RequestParam("file") MultipartFile file) {
        // Check if a file is selected
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected for validation.");
        }

        try {
            // Process the Excel file
            String filePath = "/path/to/temp/file"; // Use your logic to get the file path
            readDataFromExcel.processExcelFile(filePath); // Call the processing method

            return ResponseEntity.ok("File validation completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("File upload/validation failed: " + e.getMessage());
        }
    }
}
