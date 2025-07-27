package com.bluebus.inventoryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "businventory")
@Getter
@Setter
public class BusInventory {
    @Id
    @Column(name = "busnumber", nullable = false, length = Integer.MAX_VALUE)
    private String busnumber;

    @Column(name = "availableseats", length = Integer.MAX_VALUE)
    private String availableseats;

    @Column(name = "creationddate", length = Integer.MAX_VALUE)
    private String creationdate;

    @Column(name = "lastupdateddate", length = Integer.MAX_VALUE)
    private String lastupdateddate;

}