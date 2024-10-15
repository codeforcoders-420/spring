// ReadDataFromExcel.java
package com.yourpackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadDataFromExcel {

    @Autowired
    private ProgressService progressService;

    public void processExcelFile(String filePath) {
        // Assume you have a total of 300 lines to process
        int totalLines = 300;

        for (int i = 0; i < totalLines; i++) {
            // Simulate processing a line of code
            try {
                Thread.sleep(100); // Simulate some processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Calculate progress percentage
            double percentage = ((double) (i + 1) / totalLines) * 100;
            updateProgress(percentage);
        }
    }

    // Method to simulate progress updates
    private void updateProgress(double percentage) {
        System.out.println("Progress: " + percentage + "%");
        progressService.sendProgressUpdate(percentage); // Send progress to WebSocket
    }
}
