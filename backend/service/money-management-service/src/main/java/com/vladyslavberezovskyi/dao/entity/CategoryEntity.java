package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "category")
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {

    private String name;
    private UUID userId;
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<BudgetEntity> budgets = new ArrayList<>();
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

}
