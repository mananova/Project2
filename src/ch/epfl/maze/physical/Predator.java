package ch.epfl.maze.physical;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */
abstract public class Predator extends Animal {

	private Direction prevChoice;
	private Vector2D maison;
	private static int targetIndex = 0;
	/* constants relative to the Pac-Man game */
	public static final int SCATTER_DURATION = 14;
	public static final int CHASE_DURATION = 40;

	/**
	 * Constructs a predator with a specified position.
	 * 
	 * @param position
	 *            Position of the predator in the labyrinth
	 */
	public Predator(Vector2D position) {
		super(position);
		this.maison = position;
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not hunting in a
	 * {@code MazeSimulation}.
	 * 
	 */
	@Override
	public final Direction move(Direction[] choices) {
		Random rand = new Random();
		if(choices.length == 1){
			prevChoice = choices[0];
		}else{
			if (prevChoice == null){
				prevChoice = choices[rand.nextInt(choices.length)];
			}else{
				List<Direction> newChoices = new ArrayList<Direction>();
				for (int i = 0; i < choices.length; i++){
					if (!choices[i].equals(prevChoice.reverse())){
						newChoices.add(choices[i]);
					}
				}
				prevChoice = newChoices.get(rand.nextInt(newChoices.size()));
			}
		}
		return prevChoice;
	}

	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to hunt more
	 * effectively.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)})
	 * @param daedalus
	 *            The world in which the animal moves
	 * @return The next direction of the animal, chosen in {@code choices}
	 */
	abstract public Direction move(Direction[] choices, Daedalus daedalus);
	
	/**
	 * Decides on a path while following a target
	 * 
	 * @param choices
	 *            Possible directions to choose from.
	 * @param target
	 *            Position of the selected prey.
	 * @return 
	 * 			  Direction to take
	 */
	public final Direction standardMove(Direction[] choices, Vector2D target){
		//Si un fantôme arrive à une impasse, il emprunte simplement la seule direction proposée.
		if(choices.length == 1){
			return choices[0];
		}
		//Au cas où un fantôme se trouve dans un couloir, il prend la direction qui ne le fait pas revenir en arrière, indépendamment de sa cible
		else if (choices.length == 2){
			if(prevChoice != null){
				if (choices[0].equals(prevChoice.reverse())){
					prevChoice = choices[1];
					return choices[1];
				}
				prevChoice = choices[0];
				return choices[0];
			}else{
				List<Direction> newChoices = new ArrayList<Direction>();
				for (int i = 0; i < choices.length; i++){
					newChoices.add(choices[i]);
				}
				return choosePath(newChoices, target);
			}
		}
		//third case
		else{
			List<Direction> newChoices = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++){
				if (!choices[i].equals(prevChoice.reverse())){
					newChoices.add(choices[i]);
				}
			}
			return choosePath(newChoices, target);
		}
	}
	
	/**
	 * Chooses the shortest path to a target
	 * 
	 * @param choices
	 *            Possible directions to choose from.
	 * @param target
	 *            Position of the selected prey.
	 * @return
	 * 			  Direction to take
	 */
	public final Direction choosePath(List<Direction> choices, Vector2D target){
		double minDist = Double.POSITIVE_INFINITY;
		int choice = 0;
		for (int i = 0; i < choices.size(); i++){
			Vector2D position = getPosition().addDirectionTo(choices.get(i));
			double dist = position.negate().add(target).dist();
			if (dist <= minDist){
				minDist = dist;
				choice = i;
			}
		}
		prevChoice = choices.get(choice);
		return choices.get(choice);
	}

	/**
	 * Returns the target object
	 * 
	 * @param daedalus
	 * 			The current maze
	 * @return
	 * 			Prey that should be targeted
	 */
	public final Prey getTarget(Daedalus daedalus){
		return daedalus.getPreys().get(targetIndex);
	}
	
	/**
	 * Moves on to the next target
	 */
	public final void changeTarget(){
		Predator.targetIndex++;
	}

	public final Vector2D getMaison(){
		return this.maison;
	}
	
}
