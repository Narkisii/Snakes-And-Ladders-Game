package Intrefaces;

import enums.GameEvent;

public interface GameEventSubject {
	void attach(GameEventObserver observer);
    void detach(GameEventObserver observer);
    void notifyObservers(GameEvent event);
}
