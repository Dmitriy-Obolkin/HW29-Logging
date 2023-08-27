package ua.ithillel.coffee.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeOrderBoard {
    private HashMap<Integer, Order> orders;
    private int lastNumber = 0;

    private static final Logger LOGGER = LogManager.getLogger(CoffeeOrderBoard.class);

    public CoffeeOrderBoard(){
        orders = new LinkedHashMap<>();
    }

    public int add(String name){
        lastNumber++;
        Order order = new Order(lastNumber, name);

        LOGGER.info("Adding new order: {}.", order);

        orders.put(lastNumber, order);
        return lastNumber;
    }

    public Order deliver() {
        LOGGER.info("Attempting to deliver the earliest order");

        if(orders.isEmpty()) {
            IllegalStateException illegalStateException = new IllegalStateException("No orders have been placed yet.");
            LOGGER.error("Failed to deliver the earliest order. No orders have been placed yet.", illegalStateException);
            throw illegalStateException;
        }

        Integer firstKey = orders.keySet().iterator().next();
        Order earliestOrder = orders.get(firstKey);
        orders.remove(firstKey);

        LOGGER.info("Successfully delivered the earliest order: {}", earliestOrder);
        return earliestOrder;
    }

    public Order deliver(int orderNumber) {
        LOGGER.info("Attempting to deliver order with number: {}", orderNumber);

        if(orders.isEmpty()) {
            IllegalStateException illegalStateException = new IllegalStateException("No orders have been placed yet.");
            LOGGER.error("Failed to deliver order {}. No orders have been placed yet.", orderNumber, illegalStateException);
            throw illegalStateException;
        }

        Order order = orders.get(orderNumber);
        if (order == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Order with given number doesn't exist.");
            LOGGER.error("Failed to deliver order {}. Order with given number doesn't exist.", orderNumber, illegalArgumentException);
            throw illegalArgumentException;
        }

        orders.remove(orderNumber);
        LOGGER.info("Successfully delivered order: {}", order);
        return order;
    }

    public void draw() {
        LOGGER.info("Drawing the order queue...");

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            LOGGER.info("Order queue is empty.");
            return;
        }

        int maxNameLength = orders.values().stream()
                .mapToInt(order -> order.getName().length())
                .max()
                .orElse(0);

        String headerFormat = "Num | %-"+ maxNameLength + "s%n";
        String orderFormat = "%-3d | %-"+ maxNameLength + "s%n";

        System.out.printf("=".repeat(8 + maxNameLength) + "\n");

        System.out.printf(headerFormat, "Name");

        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            System.out.printf(orderFormat, entry.getKey(), entry.getValue().getName());
        }

        LOGGER.info("Finished drawing the order queue.");
    }

}
