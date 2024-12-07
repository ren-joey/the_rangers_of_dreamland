package com.trod.entity.abstractentity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class UUIDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
