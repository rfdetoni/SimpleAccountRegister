package com.ogisystems.technicaltest.controllers;


import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts", description = "Operations related to accounts")
@RestController
@RequestMapping(value = "/conta", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountInfoDTO> createAccount(@Valid @RequestBody AccountInfoDTO account) {
        return ResponseEntity.status( HttpStatus.CREATED )
                .body( accountService.createAccount( account ) );
    }

    @GetMapping()
    public ResponseEntity<AccountInfoDTO> getAccount(@PathParam("numero_conta") Long accountNumber) {
        return ResponseEntity.ok( accountService.getAccount( accountNumber ) );
    }
}
