package com.bluebus.inventoryservice.service;

import com.bluebus.inventoryservice.entity.Booking;
import com.bluebus.inventoryservice.entity.BusInventory;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class JMSCustomListener {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BusInventoryService busInventoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSCustomListener.class);
    @JmsListener(destination = "payment-service-queue")
    public void receiveMessage(String message) {
        LOGGER.info("Received message from payment-service-queue: {}", message);
        Booking booking = new Gson().fromJson(message, Booking.class);
        LOGGER.info("Message Consumed   {}",booking.getBusnumber());
        BusInventory businventory = new BusInventory();
        Optional<BusInventory> res = busInventoryService.getBusInventry(booking.getBusnumber());
        businventory.setAvailableseats(String.valueOf(Integer.parseInt(res.get().getAvailableseats())-Integer.parseInt(booking.getNumberofseats())));
        businventory.setBusnumber(booking.getBusnumber());
        busInventoryService.updateInventory(businventory);
        LOGGER.info("Updated Businventory for bus number: {}", businventory.getBusnumber());
        jmsTemplate.convertAndSend("inventory-service-queue", message);
        LOGGER.info("Message sent to inventory-service-queue: {}", message);
    }
}
