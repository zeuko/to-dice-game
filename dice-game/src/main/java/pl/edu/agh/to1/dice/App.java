package pl.edu.agh.to1.dice;

import java.util.Arrays;

import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.DiceGame;


public class App {
    public static void main(String[] args) {
        new DiceGame(Arrays.asList(new Player("Andy"),new Player("Tom"))).play();
    }
}
