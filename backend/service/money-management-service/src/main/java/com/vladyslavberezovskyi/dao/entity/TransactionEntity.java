package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "transaction")
@AllArgsConstructor
public class TransactionEntity extends BaseEntity {

    private String name;
    private Double value;
    private UUID userId;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "balance_id")
    private BalanceEntity balance;
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
