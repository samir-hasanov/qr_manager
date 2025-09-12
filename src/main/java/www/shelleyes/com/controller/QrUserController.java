package www.shelleyes.com.controller;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.shelleyes.com.dto.request.ReqQrUser;
import www.shelleyes.com.dto.response.PaginationResponse;
import www.shelleyes.com.dto.response.RespQrUser;
import www.shelleyes.com.service.impl.QrUserServiceImpl;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr")
public class QrUserController {

    private final QrUserServiceImpl userService;


// kubernetes k8s
    @PostMapping("/add")
    public ResponseEntity<byte[]> addQrUser(@RequestBody ReqQrUser reqQrUser) throws WriterException, IOException {
       return (userService.addQrUser(reqQrUser));
    }


    @GetMapping("/qr-image/{id}")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable Long id) {
        byte[] qrImage = userService.getQRUserImage(id);
        if (qrImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public List<RespQrUser> getQrUserList(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        return userService.getQrUserLis(page,size);
    }


    @GetMapping("lp")
    public PaginationResponse<RespQrUser> getQrUserListPage(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){

        return userService.getQrUserListPage(page,size);
    }


    @PostMapping("/del/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


    @PutMapping("/edit/{id}")
    public void upDateUser(@PathVariable Long id,@RequestBody ReqQrUser reqQrUser) throws IOException, WriterException {
        userService.upDateUser(id,reqQrUser);

    }




}
