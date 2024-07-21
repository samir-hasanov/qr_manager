package www.shelleyes.com.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCode {

    Active(1), DeActive(0);

    private final Integer value;
}
