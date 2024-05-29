package com.vladyslavberezovskyi.dao.entity;

import com.vladyslavberezovskyi.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "budget")
public class BudgetEntity extends BaseEntity {

    private String name;
    private Double currentValue;
    private Double neededValue;
    private UUID userId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
