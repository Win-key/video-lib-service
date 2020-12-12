package com.video.lib.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Venkatesh Rajendran
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private boolean isAuthenticated;

    private List<String> errors;

    public void error(String error){
        if(Objects.isNull(errors)){
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }

}
