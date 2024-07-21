package www.shelleyes.com.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import www.shelleyes.com.dto.request.ReqQrUser;
import www.shelleyes.com.dto.response.PaginationResponse;
import www.shelleyes.com.dto.response.RespQrUser;
import www.shelleyes.com.entity.QrUser;
import www.shelleyes.com.exception.NotFoundException;
import www.shelleyes.com.repository.QrUserRepository;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrUserServiceImpl implements www.shelleyes.com.service.QrUserService {

    private final QrUserRepository qrUserRepository;

    @Override
    public ResponseEntity<byte[]> addQrUser(ReqQrUser reqQrUser) throws WriterException, IOException {

        if (reqQrUser == null) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST, "qr elave olunmadi");
        }
        String name = reqQrUser.getName();
        String surname = reqQrUser.getSurname();
        String text = reqQrUser.getQrText();
        log.info("text " + text);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 800, 800);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrImage = pngOutputStream.toByteArray();
        log.info(String.valueOf(qrImage));
        QrUser qrCode = new QrUser();
        qrCode.setName(name);
        qrCode.setSurname(surname);
        qrCode.setQrText(text);
        qrCode.setQrImage(qrImage);
        qrUserRepository.save(qrCode);
        return ResponseEntity.ok().header("Content-Type", "image/png")
                .body(qrImage);
    }

    public byte[] getQRUserImage(Long id) {
        return qrUserRepository.findById(id).map(QrUser::getQrImage).orElse(null);

    }

    public List<RespQrUser> getQrUserLis(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<QrUser> list = qrUserRepository.findAll(pageable);

        if (list == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "user list not found");
        }
        List<RespQrUser> resList = list.stream().map(qrUser ->
                new RespQrUser(qrUser.getId(),
                        qrUser.getName(), qrUser.getSurname(), qrUser.getQrText(),
                        qrUser.getQrImage())).collect(Collectors.toList());
        return resList;

    }


    public void deleteUser(Long id) {

        qrUserRepository.deleteById(id);
    }
    @Transactional
    public void upDateUser(Long id, ReqQrUser reqQrUser) throws IOException, WriterException {
        log.info("request field "+reqQrUser.toString());
        try {
            if (id == null) {
                throw new NotFoundException(HttpStatus.BAD_REQUEST, "request null");
            }

            log.info("request field "+reqQrUser.toString());

            QrUser qrUser = qrUserRepository.findQrUserById(id);
            log.info("from db "+qrUser.toString());
            if(qrUser==null){
                throw new NotFoundException(HttpStatus.NOT_FOUND, "data not found db null");
            }
            qrUser.setName(reqQrUser.getName());
            qrUser.setSurname(reqQrUser.getSurname());
            qrUser.setQrText(reqQrUser.getQrText());
            log.info("qrUser " + id + "  -- " + qrUser);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(reqQrUser.getQrText(), BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] qrImage = pngOutputStream.toByteArray();
            log.info("qrUser " + id + "  -- " + qrUser);
            qrUser.setQrImage(qrImage);
            qrUserRepository.save(qrUser);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public PaginationResponse<RespQrUser> getQrUserListPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<QrUser> productPage = qrUserRepository.findAll(pageable);
            List<QrUser> productList = productPage.getContent();

            List<RespQrUser> resList = productList.stream().map(qrUser ->
                    new RespQrUser(qrUser.getId(),
                            qrUser.getName(), qrUser.getSurname(), qrUser.getQrText(),
                            qrUser.getQrImage())).collect(Collectors.toList());

            int totalPages = productPage.getTotalPages();

            return new PaginationResponse<RespQrUser>(resList, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
//
//    public PaginationResponse<Product> getProducts(int pageNumber, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Product> productPage = productRepository.findAll(pageable);
//
//        List<Product> productList = productPage.getContent();
//        int totalPages = productPage.getTotalPages();
//
//        return new PaginationResponse<>(productList, totalPages);
//    }


}


