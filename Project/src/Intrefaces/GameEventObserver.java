package Intrefaces;

import enums.GameEvent;
/**
 * The GameEventObserver interface defines the contract for classes that wish to listen to
 * game events. Implementing this interface allows an object to be notified of specific
 * events that occur within the game, as defined by the GameEvent enum.
 */
public interface GameEventObserver {
	/**
     * Called when a game event that the observer has registered interest in is triggered.
     * Implementing classes should define how they respond to the event within this method.
     *
     * @param event The game event that was triggered. The event is an instance of the GameEvent enum,
     *              which defines the possible events that can occur within the game.
     */
	 void onEventTriggered(GameEvent event);
}
