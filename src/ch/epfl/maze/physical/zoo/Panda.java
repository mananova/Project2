package ch.epfl.maze.physical.zoo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Panda A.I. that implements Trémeaux's Algorithm.
 * 
 */
public class Panda extends Animal {

	private Direction prevChoice;
	private List<Vector2D> markedOnce;
	private List<Vector2D> markedTwice;
	
	/**
	 * Constructs a panda with a starting position.
	 * 
	 * @param position
	 *            Starting position of the panda in the labyrinth
	 */
	public Panda(Vector2D position) {
		super(position);
		markedOnce = new ArrayList<Vector2D>();
		markedTwice = new ArrayList<Vector2D>();
	}

	/**
	 * Moves according to <i>Trémeaux's Algorithm</i>: when the panda
	 * moves, it will mark the ground at most two times (with two different
	 * colors). It will prefer taking the least marked paths. Special cases
	 * have to be handled, especially when the panda is at an intersection.
	 */
	@Override
	public Direction move(Direction[] choices) {
		Random rand = new Random();
		List<Integer> nulMark = new ArrayList<Integer>();
		List<Integer> oneMark = new ArrayList<Integer>();
		List<Integer> twoMark = new ArrayList<Integer>();
		for(int i = 0; i < choices.length; i++){
			
			
			if (markedTwice.contains(this.getPosition().addDirectionTo(choices[i]))){
				twoMark.add(i);
			}else if (markedOnce.contains(this.getPosition().addDirectionTo(choices[i]))){
				oneMark.add(i);
			}else{
				nulMark.add(i);
			}
		}
		
		//CHOOSING THE PATH
		if (nulMark.size() > 0){//if non-marked choices are available
			prevChoice = choices[nulMark.get(rand.nextInt(nulMark.size()))];//choose randomly from unmarked choices
		}else if (oneMark.size() > 0){//if no unmarked, but some one-marked choices are available
			if (oneMark.size() == 3 && choices.length == 3){//if all choices are one-marked at the intersection
				prevChoice = prevChoice.reverse();//turn back
			}else{
				if (oneMark.size() == 1){//if only 1 one-marked choice
					prevChoice = choices[oneMark.get(0)];//choose it
				}else{//if more than one one-marked choice available, but not all one-marked
					List<Direction> newChoices = new ArrayList<Direction>();
					for (int i = 0; i < oneMark.size(); i++){
						if (!choices[oneMark.get(i)].equals(prevChoice.reverse())){//remove where we came from
							newChoices.add(choices[oneMark.get(i)]);
						}
					}
					prevChoice = newChoices.get(rand.nextInt(newChoices.size()));//choose randomly from  one-marked choices that don't lead back
				}
			}
		}else if (twoMark.size() > 0){//if only twice-marked choices left
			if (twoMark.size() == 1){//if only 1 choice
				prevChoice = choices[twoMark.get(0)];//choose it
			}else{
				List<Direction> newChoices = new ArrayList<Direction>();
				for (int i = 0; i < twoMark.size(); i++){
					if (!choices[twoMark.get(i)].equals(prevChoice.reverse())){//remove where we came from
						newChoices.add(choices[twoMark.get(i)]);
					}
				}
				prevChoice = newChoices.get(rand.nextInt(newChoices.size()));//choose randomly from  two-marked choices that don't lead back
			}
		}
		
		//MARKING THE CURRENT POSITION
		if (choices.length == 1 && oneMark.size() == 1){//if reached dead-end (third special case)
			markedTwice.add(getPosition());//mark as twice-marked
		}else{
			if (!markedOnce.contains(getPosition()) && !markedTwice.contains(getPosition())){//if current position is unmarked
				markedOnce.add(getPosition());//mark it as one-marked
			}else if(markedOnce.contains(getPosition()) && !markedTwice.contains(getPosition())){//if one-marked but not two-marked
				if (choices.length > 1){//if more than 1 choice
					if(nulMark.size() == 0 && oneMark.size() == 1){//if only 1 one-marked choice left
						markedTwice.add(getPosition());//mark current as twice-marked
					}
				}else{//if only one choice
					markedTwice.add(getPosition());//mark as twice-marked
				}
			}
		}
		return prevChoice;
	}

	@Override
	public Animal copy() {
		return new Panda(this.getPosition());
	}
}
