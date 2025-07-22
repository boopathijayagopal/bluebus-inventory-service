package com.bluebus.inventoryservice.repo;

import com.bluebus.inventoryservice.entity.Businventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinventoryRepository extends JpaRepository<Businventory, String> {
}