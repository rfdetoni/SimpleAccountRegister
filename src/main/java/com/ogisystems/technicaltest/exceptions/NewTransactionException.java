package com.ogisystems.technicaltest.exceptions;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewTransactionException extends RuntimeException {

    public NewTransactionException(Errors error) {
        super( error.getMessage() );
    }

}
