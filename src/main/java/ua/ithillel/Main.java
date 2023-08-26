package ua.ithillel;

import ua.ithillel.coffee.order.CoffeeOrderBoard;

public class Main {
    public static void main(String[] args) {
        CoffeeOrderBoard coffeeOrderBoard = new CoffeeOrderBoard();
        coffeeOrderBoard.add("John");
        coffeeOrderBoard.draw();
    }
}