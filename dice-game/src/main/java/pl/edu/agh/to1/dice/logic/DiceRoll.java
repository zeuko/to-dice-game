package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DiceRoll {

	private final List<Dice> diceSet = new ArrayList<Dice>();
	private final int diceNumber;
	
	public DiceRoll(int diceNumber) {
		this.diceNumber = diceNumber;
		for (int i = 0; i < diceNumber; i++) {
	            diceSet.add(new Dice());
	    }
		roll();
	}
	
	public void roll() {
		for (Dice dice : diceSet)
			if(!dice.isFrozen()) dice.roll();		
		unfreezeAll();
	}
	
	/**
	 * Freezes dice with indexes given in list.
	 * 
	 * @param diceToFreezeIndexesList list containing
	 * @throws GameLogicException when diceToFreezeIndexesList contains index out of bounds 
	 */
	public void freeze(List<Integer> diceToFreezeIndexesList) throws GameLogicException {
		try {
			for (Integer dIndex : diceToFreezeIndexesList) {
				diceSet.get(dIndex-1).freeze();
			}
		} catch (IndexOutOfBoundsException ex) {
			unfreezeAll();
			throw new GameLogicException("invalid index of dice to freeze given");
		}
	}
	
	
	/**
	 * Unfreezes all dice from diceSet.
	 */
	private void unfreezeAll() {
		for (Dice d : diceSet)
			d.unfreeze();
	}
	
	/**
	 * Utility method. Method checks if any number was rolled specified number of times,
	 * repeating values are not taken into consideration. Examples: --- if 5d6
	 * roll consisted of 5 twos (2,2,2,2,2), hasEqualUnique(5) returns true but
	 * hasEqualUnique(4,5) returns false --- if 5d6 roll consisted of 3 twos and
	 * 2 threes (2,2,2,3,3) both hasEqualUnique(3) and hasEqualUnique(3,2)
	 * returns true --- hasEqualUnique(1,1,1,1,1) returns true for 5d6 roll with
	 * unique values
	 * 
	 * @param values
	 * @return
	 */
	public boolean hasEqualUnique(int... values) {
		if (values.length > diceNumber)
			return false; // impossible when we use more dice
		
		List<Integer> inDiceSet = new ArrayList<Integer>();
		List<Integer> requiredNumbers = new ArrayList<Integer>();

		for (int i = 0; i < values.length; i++)
			requiredNumbers.add(values[i]);
		for (int pipsNr = Dice.MIN_VALUE; pipsNr <= Dice.MAX_VALUE; pipsNr++)
			if (diceSet.contains(Dice.valueOf(pipsNr))) inDiceSet.add(pipsNr);

		Collections.sort(requiredNumbers);
		Collections.reverse(requiredNumbers); // descending order

		for (Iterator<Integer> iterator = requiredNumbers.iterator(); iterator.hasNext();) {
			Integer count = iterator.next();
			for (Iterator<Integer> iterator2 = inDiceSet.iterator(); iterator2.hasNext();) {
				Integer i = iterator2.next();
				if (count(i) >= count) {
					iterator2.remove();
					iterator.remove();
					break;
				}
			}
		}

		return requiredNumbers.size() == 0;
	}

	/**
	 * Utility method. Computes sum, from all dice.
	 * @return sum of pips on all dice in roll
	 */
	public int sum() {
		int sum = 0;
		for (Dice d : diceSet)
			sum += d.getValue();
		return sum;
	}

	/**
	 * Utility method. Counts number of given pips rolled .
	 * @param pips	nr of pips on dice to count
	 * @return number of rolled pips
	 */
	public int count(int pips) {
		int count = 0;
		for (Dice d : diceSet)
			if (d.getValue() == pips)
				count++;
		return count;
	}

	/**
	 * Utility method. Informs whether given number of pips was rolled.
	 * @param i
	 * @return
	 */
	public boolean contains(int i) {
		return diceSet.contains(Dice.valueOf(i));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(diceSet.get(0));
		for (int i = 1; i < 5; i++) {
			sb.append(", ");
			sb.append(diceSet.get(i));
		}
		return sb.toString();
	}

}
