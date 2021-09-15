package com.table.reservation.restaurant.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity<T> {
    T id;
    String name;
}
