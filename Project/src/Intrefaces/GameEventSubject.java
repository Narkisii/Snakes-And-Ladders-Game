package Intrefaces;

import enums.GameEvent;
/**
 * The GameEventSubject interface defines the contract for classes that act as subjects
 * in the Observer pattern for game events. It allows observers to attach to or detach from
 * the subject and provides a mechanism to notify all attached observers of a particular
 * game event.
 */
public interface GameEventSubject {
	/**
	 * Attaches an observer to the subject.
	 * This method allows a GameEventObserver to start receiving notifications about game events.
	 *
	 * @param observer The observer that wants to attach to the subject to receive event updates.
	 */
	void attach(GameEventObserver observer);
	/**
	 * Detaches an observer from the subject.
	 * This method allows a GameEventObserver to stop receiving notifications about game events.
	 *
	 * @param observer The observer that wants to detach from the subject to no longer receive event updates.
	 */
	void detach(GameEventObserver observer);
	/**
	 * Notifies all attached observers of a particular game event.
	 * This method is called when an event occurs that observers need to be made aware of.
	 *
	 * @param event The game event that has occurred, to be communicated to all attached observers.
	 */
	void notifyObservers(GameEvent event);
}
