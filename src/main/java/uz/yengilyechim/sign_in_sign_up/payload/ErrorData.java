package uz.yengilyechim.sign_in_sign_up.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ErrorData {

    private String message;
    private Integer errorCode;
}