package com.bluebus.inventoryservice.controller;

import com.bluebus.inventoryservice.entity.Booking;
import com.bluebus.inventoryservice.entity.Businventory;
import com.bluebus.inventoryservice.repo.BusinventoryRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class InventoryServiceController {
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    BusinventoryRepository businventoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceController.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    InventoryServiceController(BusinventoryRepository businventoryRepository)
    {
        this.businventoryRepository = businventoryRepository;
    }

    @PostMapping("addInventory")
    public ResponseEntity<String> addInventory(@RequestBody Businventory businventory){
        LocalDate myObj = LocalDate.now();
        businventory.setLastupdateddate(myObj.format(myFormatObj));
        businventoryRepository.save(businventory);
        return ResponseEntity.ok("New Businventory added");
    }

    @JmsListener(destination = "payment-service-queue")
    public void receiveMessage(String message) {
        LOGGER.info("Received message: {}", message);
        Booking booking = new Gson().fromJson(message, Booking.class);
        LOGGER.info("Message Consumed   {}",booking.getBusnumber());
        Businventory businventory = new Businventory();
        ResponseEntity<Optional<Businventory>> res = fetchInventory(booking.getBusnumber());
        businventory.setAvailableseats(String.valueOf(Integer.parseInt(res.getBody().get().getAvailableseats())-Integer.parseInt(booking.getNumberofseats())));
        businventory.setBusnumber(booking.getBusnumber());
        editInventory(businventory);
        jmsTemplate.convertAndSend("inventory-service-queue", message);
    }

    @PutMapping("editInventory")
    public ResponseEntity<String> editInventory(@RequestBody Businventory businventory){
        LocalDate myObj = LocalDate.now();
        businventory.setLastupdateddate(myObj.format(myFormatObj));
        businventoryRepository.save(businventory);
        return ResponseEntity.ok("Edited Businventory");
    }

    @DeleteMapping("deleteInventory/{busNumber}")
    public ResponseEntity<String> deleteInventory(@PathVariable String busNumber){
        businventoryRepository.deleteById(busNumber);
        return ResponseEntity.ok("Deleted Businventory");
    }

    @GetMapping("fetchInventory/{busNumber}")
    public ResponseEntity<Optional<Businventory>> fetchInventory(@PathVariable String busNumber){
        return ResponseEntity.ok(businventoryRepository.findById(busNumber));
    }

}
