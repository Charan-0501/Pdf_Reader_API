
package com.bankstatement.api.controller;

import com.bankstatement.api.model.BankStatementInfo;
import com.bankstatement.api.service.PdfParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfParserService pdfParserService;

    @Autowired
    public PdfController(PdfParserService pdfParserService) {
        this.pdfParserService = pdfParserService;
    }

    @PostMapping("/parse")
    public ResponseEntity<BankStatementInfo> parsePdf(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().build();
            }
            
            BankStatementInfo result = pdfParserService.extractInfoFromPdf(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
