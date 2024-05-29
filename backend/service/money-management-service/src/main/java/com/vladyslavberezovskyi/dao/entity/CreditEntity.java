package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.dao.entity.enums.CreditType;
import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "credit")
@AllArgsConstructor
public class CreditEntity extends BaseEntity {

    private String name;
    private String currentValue;
    private String neededValue;
    @Enumerated(value = EnumType.STRING)
    private CreditType creditType;
    private UUID userId;
    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

}
