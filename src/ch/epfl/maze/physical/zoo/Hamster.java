package ch.epfl.maze.physical.zoo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */
public class Hamster extends Animal {

	private Direction prevChoice;
	private List<Vector2D> deadends;
	
	/**
	 * Constructs a hamster with a starting position.
	 * 
	 * @param position
	 *            Starting position of the hamster in the labyrinth
	 */
	public Hamster(Vector2D position) {
		super(position);
		deadends = new ArrayList<Vector2D>();
	}

	/**
	 * Moves without retracing directly its steps and by avoiding the dead-ends
	 * it learns during its journey.
	 */
	@Override
	public Direction move(Direction[] choices) {
		Random rand = new Random();
		if(choices.length == 1){
			deadends.add(this.getPosition());
			prevChoice = choices[0];
		}else{
			if (prevChoice == null){
				prevChoice = choices[rand.nextInt(choices.length)];
			}else{
				List<Direction> newChoices = new ArrayList<Direction>();
				for (int i = 0; i < choices.length; i++){
					if (!deadends.contains(this.getPosition().addDirectionTo(choices[i]))){
						newChoices.add(choices[i]);
					}
				}
				if (newChoices.size() == 0){
					return Direction.NONE;
				}else if (newChoices.size() == 1){
					prevChoice = newChoices.get(0);
					deadends.add(this.getPosition());
				}else{
					for (int i = 0; i < newChoices.size(); i++){
						if (newChoices.get(i).equals(prevChoice.reverse())){
							newChoices.remove(i);
							break;
						}
					}
					prevChoice = newChoices.get(rand.nextInt(newChoices.size()));
				}
			}
		}
		return prevChoice;
	}

	@Override
	public Animal copy() {
		return new Hamster(this.getPosition());
	}
}
