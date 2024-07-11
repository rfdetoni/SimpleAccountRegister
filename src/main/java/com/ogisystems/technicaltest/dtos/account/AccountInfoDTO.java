package com.ogisystems.technicaltest.dtos.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ogisystems.technicaltest.entities.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfoDTO {
    @JsonProperty("numero_conta")
    @NotNull
    private Long accountNumber;

    @JsonProperty("saldo")
    @NotNull
    private BigDecimal balance;

    public AccountInfoDTO(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }

    public Account toEntity() {
        return Account.builder()
                .accountNumber(accountNumber)
                .balance(balance)
                .build();
    }
}
