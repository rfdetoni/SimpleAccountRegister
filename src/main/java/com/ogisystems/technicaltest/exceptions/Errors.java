package com.ogisystems.technicaltest.exceptions;

import lombok.Getter;

@Getter
public enum Errors {
    NOT_FOUND("Not found"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    BAD_REQUEST("Bad request"),
    CONFLICT("Conflict"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    ACCOUNT_ALREADY_EXISTS("Account already exists"),
    INITIAL_BALANCE_CANNOT_BE_NEGATIVE("Initial balance cannot be negative"),
    INSUFFICIENT_FUNDS("Insufficient funds"),
    VALUE_MUST_BE_GREATER_THAN_ZERO("Value must be greater than zero");


    private final String message;

    Errors(String message) {
        this.message = message;    }
}
