package com.video.lib.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Venkatesh Rajendran
 */

@Data
@NoArgsConstructor
public class LogInDTO {

    @NonNull
    private String userName;
    @NonNull
    private String password;

}
