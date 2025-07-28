package com.bluebus.inventoryservice.controller;

import com.bluebus.inventoryservice.entity.BusInventory;
import com.bluebus.inventoryservice.service.BusInventoryService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class InventoryServiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceController.class);
    @Autowired
    private BusInventoryService busInventoryService;

    @PostMapping("addInventory")
    public ResponseEntity<String> addInventory(@RequestBody @NonNull BusInventory businventory, @RequestHeader("Authorization") String token){
        if (!busInventoryService.validateToken(token)) {
            LOGGER.info("Unauthorized access attempt with token: {}", token);
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid token");
        }
        LOGGER.info("entered into inventory service");
        busInventoryService.createInventory(businventory);
        LOGGER.info("New Businventory created for bus number: {}", businventory.getBusnumber());
        return ResponseEntity.ok("New Businventory for bus number: " + businventory.getBusnumber());
    }

    @GetMapping("fetchInventory/{busNumber}")
    public ResponseEntity<?> fetchInventory(@PathVariable String busNumber, @RequestHeader("Authorization") String token){
        if (!busInventoryService.validateToken(token)) {
            LOGGER.info("Unauthorized access attempt with token: {}", token);
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid token");
        }
        LOGGER.info("Fetching inventory for bus number: {}", busNumber);
        return ResponseEntity.ok(busInventoryService.getBusInventry(busNumber));
    }

    @PutMapping("editInventory")
    public ResponseEntity<String> editInventory(@RequestBody BusInventory businventory, @RequestHeader("Authorization") String token){
        if (!busInventoryService.validateToken(token)) {
            LOGGER.info("Unauthorized access attempt with token: {}", token);
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid token");
        }
        LOGGER.info("Editing inventory for bus number: {}", businventory.getBusnumber());
        busInventoryService.updateInventory(businventory);
        LOGGER.info("Edited Businventory for bus number: {}", businventory.getBusnumber());
        return ResponseEntity.ok("Edited Businventory for bus number: " + businventory.getBusnumber());
    }

    @DeleteMapping("deleteInventory/{busNumber}")
    public ResponseEntity<String> deleteInventory(@PathVariable String busNumber, @RequestHeader("Authorization") String token){
        if (!busInventoryService.validateToken(token)) {
            LOGGER.info("Unauthorized access attempt with token: {}", token);
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid token");
        }
        LOGGER.info("Deleting inventory for bus number: {}", busNumber);
        busInventoryService.removeInventory(busNumber);
        return ResponseEntity.ok("Deleted Businventory for bus number: " + busNumber);
    }

}
