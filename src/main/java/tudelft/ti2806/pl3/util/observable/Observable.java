package tudelft.ti2806.pl3.util.observable;

import tudelft.ti2806.pl3.util.observers.Observer;

import java.util.ArrayList;

/**
 * Interface for the Observable.
 * Created by Kasper on 27-5-2015.
 */
public interface Observable {
	/**
	 * Add a (Observer to the Observable.
	 *
	 * @param observer
	 * 		observer to add
	 */
	void addObserver(Observer observer);

	/**
	 * Add multiple Observers to the Observable.
	 *
	 * @param observers
	 * 		observers to add
	 */
	void addObserversList(ArrayList<Observer> observers);

	/**
	 * Delete the Observers.
	 *
	 * @param observer
	 * 		observer to remove
	 */
	void deleteObserver(Observer observer);

	/**
	 * Notify all the Observers with a message.
	 *
	 * @param arguments
	 * 		to give to the Observer
	 */
	void notifyObservers(Object arguments);

}
