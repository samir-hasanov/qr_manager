package www.shelleyes.com.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.shelleyes.com.service.ExcelService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr/ex")
@CrossOrigin(origins = "http://localhost:49246")
public class ExcelController {

    private final ExcelService excelService;
    @PostMapping("/add")
    public ResponseEntity<String> importExcelFile(@RequestParam("file") MultipartFile file) {
        excelService.importExcelData(file);
        return ResponseEntity.status(HttpStatus.OK).body("Excel file imported successfully.");
    }


}
