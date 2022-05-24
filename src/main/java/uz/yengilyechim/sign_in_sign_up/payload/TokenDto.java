package uz.yengilyechim.sign_in_sign_up.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @NotBlank(message = "Access token required")
    private String accessToken;

    @NotBlank(message = "Refresh token required")
    private String refreshToken;


}
