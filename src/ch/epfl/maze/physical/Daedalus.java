package ch.epfl.maze.physical;
import java.util.ArrayList;
import java.util.List;
/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */
public final class Daedalus extends World {

	private List<Predator> predator;
	private List<Prey> prey;
	private List<Predator> allPredators;
	private	List<Prey> allPreys;
	
	/**
	 * Constructs a Daedalus with a labyrinth structure
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */
	public Daedalus(int[][] labyrinth) {
		super(labyrinth);
		predator = new ArrayList<Predator>();
		prey = new ArrayList<Prey>();
		allPredators = new ArrayList<Predator>();
		allPreys = new ArrayList<Prey>();
	}

	@Override
	public boolean isSolved() {
		return prey.isEmpty();
	}

	/**
	 * Determines if the list contains a predator.
	 * @param list
	 *            The predator list to check
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator class is present in the list, <b>false</b>
	 *         otherwise.
	 */
	public boolean contains(List<Predator> list, Predator p){
		for(int i = 0; i < list.size(); i++){
			if (list.get(i).getClass().equals(p.getClass())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines if the list contains a prey.
	 * @param list
	 *            The prey list to check
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey class is present in the list, <b>false</b>
	 *         otherwise.
	 */
	public boolean contains(List<Prey> list, Prey p){
		for(int i = 0; i < list.size(); i++){
			if (list.get(i).getClass().equals(p.getClass())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a predator to the daedalus.
	 * 
	 * @param p
	 *            The predator to add
	 */
	public void addPredator(Predator p) { //possible encapsulation fail
		predator.add(p);
		if(!contains(allPredators, p)){
			allPredators.add(p);
		}
	}

	/**
	 * Adds a prey to the daedalus.
	 * 
	 * @param p
	 *            The prey to add
	 */
	public void addPrey(Prey p){  //possible encapsulation fail
		prey.add(p);
		if(!contains(allPreys, p)){
			allPreys.add(p);
		}
	}

	/**
	 * Removes a predator from the daedalus.
	 * 
	 * @param p
	 *            The predator to remove
	 */
	public void removePredator(Predator p) {//what if >1?
		predator.remove(p);
	}

	/**
	 * Removes a prey from the daedalus.
	 * 
	 * @param p
	 *            The prey to remove
	 */
	public void removePrey(Prey p) { //what if >1?
		prey.remove(p);
	}

	@Override
	public List<Animal> getAnimals() {
		List<Animal> animals = new ArrayList<Animal>();
		animals.addAll(predator);
		animals.addAll(prey);
		return animals;
	}

	/**
	 * Returns a copy of the list of all current predators in the daedalus.
	 * 
	 * @return A list of all predators in the daedalus
	 */
	public List<Predator> getPredators() { //encapsulation fail [should deep copy???]
		List<Predator> predatorCopy = new ArrayList<Predator>();
		predatorCopy.addAll(predator);
		return predator;
	}

	/**
	 * Returns a copy of the list of all current preys in the daedalus.
	 * 
	 * @return A list of all preys in the daedalus
	 */
	public List<Prey> getPreys() { //encapsulation fail [should deep copy???]
		List<Prey> preyCopy = new ArrayList<Prey>();
		preyCopy.addAll(prey);
		return preyCopy;
	}

	/**
	 * Determines if the daedalus contains a predator.
	 * 
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator belongs to the world, <b>false</b>
	 *         otherwise.
	 */
	public boolean hasPredator(Predator p) {
		return contains(predator, p);
	}

	/**
	 * Determines if the daedalus contains a prey.
	 * 
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey belongs to the world, <b>false</b>
	 *         otherwise.
	 */
	public boolean hasPrey(Prey p) {
		return contains(prey, p);
	}

	@Override
	public void reset() { //is it ok to cast like that?
		predator.clear();
		prey.clear();
		for(int i = 0; i < allPredators.size(); i++){
			addPredator((Predator)allPredators.get(i).copy());
		}
		for(int i = 0; i < allPreys.size(); i++){
			addPrey((Prey)allPreys.get(i).copy());
		}
	}
}
