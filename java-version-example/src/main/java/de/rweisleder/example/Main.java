package de.rweisleder.example;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> words = List.of("Hello", "World");
        System.out.println(String.join(" ", words));
    }
}
