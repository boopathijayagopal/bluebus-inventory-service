package com.bluebus.inventoryservice.repo;

import com.bluebus.inventoryservice.entity.BusInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusInventoryRepository extends JpaRepository<BusInventory, String> {
}