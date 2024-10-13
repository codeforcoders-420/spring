import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/peer-review")
public class PeerReviewController {

    @PostMapping("/validate")
    public ResponseEntity<String> validateFile(@RequestParam("file") MultipartFile file) {
        // Check if a file is selected
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected for validation.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the file to a temporary directory
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            // Call the Excel processing method (your existing Java code)
            String filePath = tempFile.getAbsolutePath();
            // Assuming your logic from the provided code, call this method
            ReadDataFromExcel.UploadFileAndValidate(filePath);  // Modify this call as per your logic

            // Return a success response
            return new ResponseEntity<>("File validation completed successfully.", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload/validation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
