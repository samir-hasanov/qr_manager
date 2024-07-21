package www.shelleyes.com.service;

import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;
import www.shelleyes.com.dto.request.ReqQrUser;

import java.io.IOException;

public interface QrUserService {
    ResponseEntity<byte[]> addQrUser(ReqQrUser reqQrUser) throws WriterException, IOException;
}
