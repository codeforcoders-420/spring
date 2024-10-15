@RestController
@RequestMapping("/peer-review")
public class PeerReviewController {

    @PostMapping("/validate")
    public ResponseEntity<String> validateFile(@RequestParam("file") MultipartFile file) {
        // Logs to store system output
        StringBuilder logs = new StringBuilder();

        // Check if a file is selected
        if (file.isEmpty()) {
            logs.append("No file selected for validation.");
            return new ResponseEntity<>(logs.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the file to a temporary directory
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            // Log file save message
            logs.append("File saved successfully at: ").append(tempFile.getAbsolutePath()).append("\n");

            // Call the Excel processing method (your existing Java code)
            String filePath = tempFile.getAbsolutePath();
            logs.append("File path for validation: ").append(filePath).append("\n");

            // Example: Assuming your logic from the provided code
            ReadDataFromExcel.UploadFileAndValidate(filePath);  // Modify this call as per your logic

            // Return a success response
            logs.append("File validation completed successfully.");
            return new ResponseEntity<>(logs.toString(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            logs.append("File upload/validation failed: ").append(e.getMessage());
            return new ResponseEntity<>(logs.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
