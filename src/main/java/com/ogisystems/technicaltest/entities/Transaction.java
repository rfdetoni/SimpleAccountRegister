package com.ogisystems.technicaltest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ogisystems.technicaltest.entities.model.BasicEntityData;
import com.ogisystems.technicaltest.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "transactions" )
@EntityListeners( AuditingEntityListener.class )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class Transaction extends BasicEntityData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransactionTypes type;

    private BigDecimal amount;

}
