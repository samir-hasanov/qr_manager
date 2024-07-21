package www.shelleyes.com.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import www.shelleyes.com.entity.QrUser;
import www.shelleyes.com.repository.QrUserRepository;
import www.shelleyes.com.service.ExcelService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    private final QrUserRepository qrUserRepository;

    @Override
    public void importExcelData(MultipartFile file) {

        List<QrUser> users = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // skip header row
                }

                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                String text = row.getCell(2).getStringCellValue();
                QrUser user = new QrUser();
                user.setName(name);
                user.setSurname(surname);
                user.setQrText(text);
                BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 800, 800);
                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
                byte[] qrImage = pngOutputStream.toByteArray();
                user.setQrImage(qrImage);
                log.info("user " + user);
                qrUserRepository.save(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}