package org.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {

        log.info("Wall test");

        Wall wall = new Wall();
        wall.assignTestValues();

        log.info("Count {}", wall.count());

        log.info("Color {}", wall.findBlockByColor("Green").get().getColor());
        log.info("Material {}", wall.findBlockByColor("Green").get().getMaterial());

        log.info("Material occurrences {}", wall.findBlocksByMaterial("Glass").size());
        log.info("Material occurrences {}", wall.findBlocksByMaterial("Paper").size());
        log.info("Material occurrences {}", wall.findBlocksByMaterial("Concrete").size());

    }
}