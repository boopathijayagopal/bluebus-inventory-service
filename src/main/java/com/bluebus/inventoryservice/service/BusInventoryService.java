package com.bluebus.inventoryservice.service;

import com.bluebus.inventoryservice.entity.BusInventory;
import com.bluebus.inventoryservice.exception.ResourceNotFoundException;
import com.bluebus.inventoryservice.repo.BusInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class BusInventoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusInventoryService.class);

    @Autowired
    BusInventoryRepository businventoryRepository;

    public void createInventory(BusInventory businventory) {
        LOGGER.info("Creating new bus inventory for bus number: {}", businventory.getBusnumber());
        businventory.setCreationdate(getDateTime());
        businventory.setLastupdateddate(getDateTime());
        businventoryRepository.save(businventory);
        LOGGER.info("New Businventory created for bus number: {}", businventory.getBusnumber());
    }

    public Optional<BusInventory> getBusInventry(String busNumber) {
        LOGGER.info("Fetching bus inventory for bus number: {}", busNumber);
        return Optional.ofNullable(businventoryRepository.findById(busNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Busroute not found for bus number: " + busNumber)));
    }

    public void updateInventory(BusInventory businventory) {
        LOGGER.info("Editing bus inventory for bus number: {}", businventory.getBusnumber());
        BusInventory existingInventory = getBusInventry(businventory.getBusnumber()).orElseThrow();
        existingInventory.setAvailableseats(businventory.getAvailableseats());
        existingInventory.setLastupdateddate(getDateTime());
        businventoryRepository.save(existingInventory);
        LOGGER.info("Edited Businventory for bus number: {}", businventory.getBusnumber());
    }

    public void removeInventory(String busNumber) {
        LOGGER.info("Deleting bus inventory for bus number: {}", busNumber);
        businventoryRepository.deleteById(busNumber);
    }

    private String getDateTime() {
        LOGGER.info("Generating current date and time in dd-MM-yyyy HH:mm:ss format");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(dateTimeFormatter);
    }
}
