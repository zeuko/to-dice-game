package pl.edu.agh.to1.dice.logic;

import java.util.Random;

public class Dice {
	
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 6; 
	private static final Random roller = new Random();

	private int value;
    private boolean frozen = false;
    
    public Dice() {
    	value = roller.nextInt(MAX_VALUE-MIN_VALUE+1) + MIN_VALUE;
    }
    
    public int getValue() {
        return value;
    }

    public boolean isFrozen() {
        return frozen;
    }
    
    public int roll() {
        value = roller.nextInt(MAX_VALUE-MIN_VALUE+1) + MIN_VALUE;
        return value;
    }

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

	public static Dice valueOf(int value) {
		if (value < MIN_VALUE || value > MAX_VALUE) {
			throw new IllegalArgumentException("invalid number of pips for dice given");
		}
		Dice newDice = new Dice();
		newDice.value = value;
		return newDice;
	}

    @Override
    public String toString() {
    	return String.valueOf(value);
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(Dice.class)) return false;
		return ((Dice)obj).getValue() == value;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
}
