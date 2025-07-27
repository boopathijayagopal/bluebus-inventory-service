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
    BusInventoryService busInventoryService;

    public InventoryServiceController(BusInventoryService busInventoryService) {
        this.busInventoryService = busInventoryService;
    }

    @PostMapping("addInventory")
    public ResponseEntity<String> addInventory(@RequestBody @NonNull BusInventory businventory){
        LOGGER.info("entered into inventory service");
        busInventoryService.createInventory(businventory);
        LOGGER.info("New Businventory created for bus number: {}", businventory.getBusnumber());
        return ResponseEntity.ok("New Businventory for bus number: " + businventory.getBusnumber());
    }

    @GetMapping("fetchInventory/{busNumber}")
    public ResponseEntity<Optional<BusInventory>> fetchInventory(@PathVariable String busNumber){
        LOGGER.info("Fetching inventory for bus number: {}", busNumber);
        return ResponseEntity.ok(busInventoryService.getBusInventry(busNumber));
    }

    @PutMapping("editInventory")
    public ResponseEntity<String> editInventory(@RequestBody BusInventory businventory){
        LOGGER.info("Editing inventory for bus number: {}", businventory.getBusnumber());
        busInventoryService.updateInventory(businventory);
        LOGGER.info("Edited Businventory for bus number: {}", businventory.getBusnumber());
        return ResponseEntity.ok("Edited Businventory for bus number: " + businventory.getBusnumber());
    }

    @DeleteMapping("deleteInventory/{busNumber}")
    public ResponseEntity<String> deleteInventory(@PathVariable String busNumber){
        LOGGER.info("Deleting inventory for bus number: {}", busNumber);
        busInventoryService.removeInventory(busNumber);
        return ResponseEntity.ok("Deleted Businventory for bus number: " + busNumber);
    }

}
