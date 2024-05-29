package com.vladyslavberezovskyi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected UUID id;
}
