package www.shelleyes.com.dto.response;

import lombok.Data;

@Data
public class Response<T> {

    private T t;
}
