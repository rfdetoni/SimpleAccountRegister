package com.ogisystems.technicaltest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ogisystems.technicaltest.entities.model.BasicEntityData;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "accounts" )
@EntityListeners( AuditingEntityListener.class )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class Account extends BasicEntityData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long accountNumber;

    private BigDecimal balance;

    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.REFRESH)
    private List<Transaction> transactions;

}
