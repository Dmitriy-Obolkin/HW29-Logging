package ua.ithillel.coffee.order;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeOrderBoard {
    private HashMap<Integer, Order> orders;
    private int lastNumber = 0;

    public CoffeeOrderBoard(){
        orders = new LinkedHashMap<>();
    }

    public int add(String name){
        lastNumber++;
        Order order = new Order(lastNumber, name);
        orders.put(lastNumber, order);
        return lastNumber;
    }

    public Order deliver() {
        if(orders.isEmpty()) {
            throw new IllegalStateException("No orders have been placed yet.");
        }

        Integer firstKey = orders.keySet().iterator().next();
        Order firstOrder = orders.get(firstKey);
        orders.remove(firstKey);
        return firstOrder;
    }

    public Order deliver(int orderNumber) {
        if(orders.isEmpty()) {
            throw new IllegalStateException("No orders have been placed yet.");
        }

        Order order = orders.get(orderNumber);
        if (order == null) {
            throw new IllegalArgumentException("Order with given number doesn't exist.");
        }
        orders.remove(orderNumber);
        return order;
    }

    public void draw() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
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
    }

}
