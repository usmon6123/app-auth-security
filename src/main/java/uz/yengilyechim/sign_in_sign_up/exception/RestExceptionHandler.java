package uz.yengilyechim.sign_in_sign_up.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.yengilyechim.sign_in_sign_up.payload.ApiResult;
import uz.yengilyechim.sign_in_sign_up.payload.ErrorData;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<?> exceptionHandling(RestException restException) {
        restException.printStackTrace();
        return ResponseEntity.
                status(restException.getStatus())
                .body(ApiResult.errorResponse(restException.getMessage(),restException.getObject()));
    }


    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<?> exceptionHandling() {
        return ResponseEntity.
                status(498)
                .body(ApiResult.errorResponse("Token expired"));
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> exceptionHandling(MethodArgumentNotValidException e){
        List<ErrorData> errorData = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> errorData.add(
                new ErrorData(objectError.getDefaultMessage(), 400)
                )
        );
        return ResponseEntity.status(400).body(ApiResult.errorResponse(errorData));
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionHandling(Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(ApiResult.errorResponse("Internal server error"));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> exceptionHandling(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.errorResponse("Parametr xato tipda berildi!"));
    }

}
