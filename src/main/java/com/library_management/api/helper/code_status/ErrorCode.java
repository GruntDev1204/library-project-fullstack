package com.library_management.api.helper.code_status;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode{
     //General error codes
    GENERAL_GET_FAILED(50001, "FAILED TO RETRIEVE DATA!", HttpStatus.NO_CONTENT),
    GENERAL_NOT_FOUND(40400, "DATA NOT FOUND!", HttpStatus.NOT_FOUND),
    DELETED_FAILED(40001, "FAILED TO DELETE DATA! ", HttpStatus.NO_CONTENT),

    // POST/CREATE method
    CREATE_FAILED(42200, "数据无效！请检查输入字段!", HttpStatus.UNPROCESSABLE_ENTITY),
    BAD_REQUEST(40000, "数据无效！请检查输入字段!", HttpStatus.BAD_REQUEST),

    // PUT/UPDATE method
    UPDATE_FAILED(42201, "FAILED TO UPDATE DATA! INVALID INPUT DATA!", HttpStatus.BAD_REQUEST),
    Exist_Email(42202, "FAILED TO CONTINUE  THE PROCESS! Exist Email", HttpStatus.UNPROCESSABLE_ENTITY),
    Exist_UserName(42203, "FAILED TO CONTINUE  THE PROCESS! Exist UserName", HttpStatus.UNPROCESSABLE_ENTITY),

    Token_Creation_Failed(42204, "failed to create a token", HttpStatus.UNPROCESSABLE_ENTITY),
    User_Not_Found(42206, "USER NOT FOUND", HttpStatus.UNPROCESSABLE_ENTITY),
    Wrong_Password(42207, "PASSWORD IS NOT CORRECT", HttpStatus.UNPROCESSABLE_ENTITY),
    Authentication_is_not_ok(40001, "Authentication is not ok ,token is not valid, cook đi cook đi!", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_AVAILABLE(40304, "Role Not Found ,(must be admin or  customer)" , HttpStatus.FORBIDDEN);

    Integer code;
    String message;
    HttpStatus status;
}
