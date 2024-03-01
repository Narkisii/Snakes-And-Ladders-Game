package Intrefaces;

import enums.GameEvent;

public interface GameEventObserver {
	 void onEventTriggered(GameEvent event);
}
