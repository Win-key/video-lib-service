package com.video.lib.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Venkatesh Rajendran
 *
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse <T>{

    private T data;
    private String error;
    private HttpStatus status;

    public BaseResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseEntity<BaseResponse> asResponseEntity(){
        return ResponseEntity.status(status).body(this);
    }

}
