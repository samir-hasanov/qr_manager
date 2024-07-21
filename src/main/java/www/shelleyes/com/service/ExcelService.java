package www.shelleyes.com.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void importExcelData(MultipartFile file);
}
