package ch.epfl.maze.physical;
import java.util.ArrayList;
import java.util.List;
/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */
public final class Maze extends World {
	
	private List<Animal> animals;
	private List<Animal> initialAnimals;
	
	/**
	 * Constructs a Maze with a labyrinth structure.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */
	public Maze(int[][] labyrinth) {
		super(labyrinth);
		animals = new ArrayList<Animal>();
		initialAnimals = new ArrayList<Animal>();
	}

	@Override
	public boolean isSolved() {
		return (animals.isEmpty());
	}

	@Override
	public List<Animal> getAnimals() {
		return this.animals;
	}

	/**
	 * Determines if the maze contains an animal.
	 * 
	 * @param a
	 *            The animal in question
	 * @return <b>true</b> if the animal belongs to the world, <b>false</b>
	 *         otherwise.
	 */
	public boolean hasAnimal(Animal a) {
		return contains(animals, a);
	}
	
	/**
	 * Determines if the list contains an animal.
	 * @param list
	 *            The list to check
	 * @param a
	 *            The animal in question
	 * @return <b>true</b> if the animal class is present in the list, <b>false</b>
	 *         otherwise.
	 */
	public boolean contains(List<Animal> list, Animal a){
		for(int i = 0; i < list.size(); i++){
			if (list.get(i).getClass().equals(a.getClass())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds an animal to the maze.
	 * 
	 * @param a
	 *            The animal to add
	 */
	public void addAnimal(Animal a) { //possible encapsulation fail?
		if (!contains(initialAnimals, a)){
			initialAnimals.add(a);
		}
		animals.add(a);
		a.setPosition(getStart());
	}

	/**
	 * Removes an animal from the maze.
	 * 
	 * @param a
	 *            The animal to remove
	 */
	public void removeAnimal(Animal a) {
		animals.remove(a);
	}
	

	@Override
	public void reset() {
		animals.clear();
		for(int i = 0; i < initialAnimals.size(); i++){
			addAnimal(initialAnimals.get(i).copy());
		}
	}
}