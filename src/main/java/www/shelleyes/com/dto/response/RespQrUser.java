package www.shelleyes.com.dto.response;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Getter
@Setter
@AllArgsConstructor
public class RespQrUser {
    private Long id;
    private String name;
    private String surname;
    private String qrText;
    private byte[] qrImage;




}
