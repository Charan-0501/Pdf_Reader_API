
package com.bankstatement.api.service;

import com.bankstatement.api.model.BankStatementInfo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfParserService {

    public BankStatementInfo extractInfoFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            
            return extractInformation(text);
        }
    }
    
    private BankStatementInfo extractInformation(String text) {
        BankStatementInfo info = new BankStatementInfo();
        
        // Extract customer name (this pattern will need to be adjusted based on actual bank statement format)
        Pattern namePattern = Pattern.compile("(?:Name|Customer|Account Holder):\\s*([\\w\\s]+)", Pattern.CASE_INSENSITIVE);
        Matcher nameMatcher = namePattern.matcher(text);
        if (nameMatcher.find()) {
            info.setName(nameMatcher.group(1).trim());
        }
        
        // Extract email (this pattern will need to be adjusted based on actual bank statement format)
        Pattern emailPattern = Pattern.compile("(?:Email|E-mail):\\s*([\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,})", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(text);
        if (emailMatcher.find()) {
            info.setEmail(emailMatcher.group(1).trim());
        }
        
        // Extract opening balance (this pattern will need to be adjusted based on actual bank statement format)
        Pattern openingPattern = Pattern.compile("(?:Opening Balance|Beginning Balance):\\s*[$£€]?\\s*(\\d+(?:[.,]\\d+)?)", Pattern.CASE_INSENSITIVE);
        Matcher openingMatcher = openingPattern.matcher(text);
        if (openingMatcher.find()) {
            String balanceStr = openingMatcher.group(1).replaceAll("[^\\d.]", "");
            info.setOpeningBalance(new BigDecimal(balanceStr));
        }
        
        // Extract closing balance (this pattern will need to be adjusted based on actual bank statement format)
        Pattern closingPattern = Pattern.compile("(?:Closing Balance|Ending Balance):\\s*[$£€]?\\s*(\\d+(?:[.,]\\d+)?)", Pattern.CASE_INSENSITIVE);
        Matcher closingMatcher = closingPattern.matcher(text);
        if (closingMatcher.find()) {
            String balanceStr = closingMatcher.group(1).replaceAll("[^\\d.]", "");
            info.setClosingBalance(new BigDecimal(balanceStr));
        }
        
        return info;
    }
}
