package enums;

public enum GameEvent {
	PLAYER_HIT_SNAKE,//Board - activateTile!!!!!!V
	PLAYER_HIT_LADDER,//Board - activateTile!!!!!V
	PLAYER_WINS,//Can be on BoardControl - checkwin(int gameEnd)
	//Or on winScreen_Controller
	CORRECT_ANSWER,//QuestionPopControl - check_Answer(String selectedAnswer)!!!!V
	INCORRECT_ANSWER,//Can be used for the "times up" !!!!
	//QuestionPopControl - check_Answer(String selectedAnswer)
	PLAYER_MISSES_TURN,//BoardControl - createCountDown()!!!
	PLAYER_TURN,
	PLAYER_MOVE,//BoardControl - Animate!!!
	DICE_ROLL,//CPU_PLAYER, BoardControl - Inside roll func DOESNT WORK
	GOOD_SURPRISE,//Board - activateTile!!!!
	BAD_URPRISE,//Board - activateTile!!!
	RED_SNAKE, //Board - activateTile!!!
	QUESTION_POP
}
