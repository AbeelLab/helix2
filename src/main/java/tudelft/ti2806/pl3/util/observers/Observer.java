package tudelft.ti2806.pl3.util.observers;

import tudelft.ti2806.pl3.util.observable.Observable;

/**
 * Observer is a interface for a standard Observer.
 * Created by Kasper on 15-6-2015.
 */
public interface Observer {

	void update(Observable observable, Object arguments);

}
