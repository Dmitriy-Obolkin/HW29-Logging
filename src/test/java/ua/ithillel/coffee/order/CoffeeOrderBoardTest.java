package ua.ithillel.coffee.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

public class CoffeeOrderBoardTest {

    private CoffeeOrderBoard board;

    @BeforeEach
    public void setup() {
        board = new CoffeeOrderBoard();
    }

    @Test
    public void testAddOrder() {
        int orderNumber = board.add("John");
        assertEquals(1, orderNumber);
        Order order = board.deliver(orderNumber);
        assertNotNull(order);
        assertEquals("John", order.getName());
    }

    @Test
    public void testDeliverFirstOrder() {
        board.add("John");
        board.add("Mary");
        Order firstOrder = board.deliver();
        assertNotNull(firstOrder);
        assertEquals("John", firstOrder.getName());
        assertThrows(IllegalArgumentException.class, () -> board.deliver(1));
    }

    @Test
    public void testDeliverSpecificOrder() {
        board.add("John");
        board.add("Mary");
        Order specificOrder = board.deliver(2);
        assertNotNull(specificOrder);
        assertEquals("Mary", specificOrder.getName());
        assertThrows(IllegalArgumentException.class, () -> board.deliver(2));
    }

    @Test
    public void testDeliverFromEmptyBoard() {
        assertThrows(IllegalStateException.class, () -> board.deliver());
        assertThrows(IllegalStateException.class, () -> board.deliver(1));
    }

    @Test
    public void testDeliverNonExistentOrder() {
        board.add("John");
        assertThrows(IllegalArgumentException.class, () -> board.deliver(2));
    }

    @Test
    public void testDraw() {
        board.add("John");
        board.add("Mary");
        board.add("Obi-van");
        board.add("John Snow");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        board.draw();

        System.setOut(originalOut);


        int maxNameLength = Stream.of("John", "Mary", "Obi-van", "John Snow")
                .mapToInt(String::length)
                .max()
                .orElse(0);

        String headerFormat = "Num | %-"+ maxNameLength + "s%n";
        String orderFormat = "%-3d | %-"+ maxNameLength + "s%n";

        String expectedOutput =
                "=".repeat(8 + maxNameLength) + "\n" +
                        String.format(headerFormat, "Name") +
                        String.format(orderFormat, 1, "John") +
                        String.format(orderFormat, 2, "Mary") +
                        String.format(orderFormat, 3, "Obi-van") +
                        String.format(orderFormat, 4, "John Snow");

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testDrawEmptyBoard(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        board.draw();

        System.setOut(originalOut);

        String expectedOutput = "No orders available.\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

}
