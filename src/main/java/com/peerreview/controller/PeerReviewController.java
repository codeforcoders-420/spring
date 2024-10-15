import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/peer-review")
public class PeerReviewController {

    private final ProgressController progressController;

    @Autowired
    public PeerReviewController(ProgressController progressController) {
        this.progressController = progressController;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected for validation.", HttpStatus.BAD_REQUEST);
        }

        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            // Simulate line processing and send updates
            int totalLines = 300;  // Assume you know the total lines
            for (int currentLine = 1; currentLine <= totalLines; currentLine++) {
                double progressPercentage = (double) currentLine / totalLines * 100;
                updateProgress(progressPercentage);  // Send progress updates
                Thread.sleep(100); // Simulate processing time (remove in production)
            }

            return new ResponseEntity<>("File validation completed successfully.", HttpStatus.OK);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload/validation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateProgress(double percentage) {
        System.out.println("Progress: " + percentage + "%");
        progressController.sendProgressUpdate(percentage); // Send progress via WebSocket
    }
}
