package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import enums.CPUNames;
import enums.Colors;
import enums.Tokens;
import exceptions.HandleExceptions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Board;
import model.GameData;
import model.InputIsEmpty;
import model.InputIsNotUnique;
import model.Player;
import model.cpu_Player;

/**
 * This class controls the Players view, where users can add and remove players,
 * and choose their names, colors, and tokens, add CPU players
 */

public class PlayersControl {

	/**
	 * Button to return to the Settings view.
	 */
	@FXML
	private Button return_Btn;

	/**
	 * Button to start the game.
	 */
	@FXML
	private Button start_game_Btn;

	/**
	 * Container for all player rows.
	 */
	@FXML
	private VBox playerContainer;
	/**
	 * Scroll pane for playerContainer.
	 */
	@FXML
	private ScrollPane PlayersPane;

	/**
	 * Button to add a CPU player.
	 */
	@FXML
	private Button add_CPU;

	/**
	 * Button to remove a CPU player.
	 */
	@FXML
	private Button remove_Cpu;

	// Number of cpu players
	private int num_cpu = 0;

	private Set<String> usedCPUNames = new HashSet<String>();

//	private LinkedList <Player> players = new LinkedList<Player>();
	/**
	 * List of all token options.
	 */
	List<String> tokens = Arrays.stream(Tokens.values()).map(Enum::name).collect(Collectors.toList());
	/**
	 * List of all color options.
	 */
	List<String> colors = Arrays.stream(Colors.values()).map(Enum::name).collect(Collectors.toList());
	/**
	 * List of all CPU names.
	 */
	List<CPUNames> cpuNames = Arrays.asList(CPUNames.values());

	int index = 0;
	/**
	 * Number of players retrieved from GameData.
	 */
	int numberOfPlayers = GameData.getInstance().getNumberOfPlayers();

	private ArrayList<TextField> playernames;
	Pattern pattern = Pattern.compile("[a-zA-Z0-9]{0,10}");

	/**
	 * Initializes the Players view.
	 */
	@FXML
	public void initialize() {
		System.out.println(numberOfPlayers);
		start_game_Btn.setDisable(true);
		PlayersPane.setFitToWidth(true);
		PlayersPane.setFitToHeight(true);
		if (numberOfPlayers == 5) {
			remove_Cpu.setDisable(true);
			add_CPU.setDisable(true);
		}
		remove_Cpu.setDisable(true);

		innit();
		return_Btn.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
		start_game_Btn.setOnAction(event -> {
			startGame(numberOfPlayers);
		});

		add_CPU.setOnAction(event -> {
			addCpu();
		});
		remove_Cpu.setOnAction(event -> {
			remove_CPU_function();
		});

		if (numberOfPlayers == 1) {
			addCpu();
			remove_Cpu.setDisable(true);

//			numberOfPlayers++;
		}
	}

	/**
	 * Removes a CPU player from the game. Decreases the total number of players and
	 * CPU count, updates UI elements accordingly, and adjusts available colors and
	 * tokens for player selection.
	 */
	private void remove_CPU_function() {
		System.out.println("numberOfPlayers " + numberOfPlayers + " num_cpu:" + num_cpu);
		// TODO Auto-generated method stub
		numberOfPlayers--;
		num_cpu--;
		index--;

		System.out.println(numberOfPlayers - num_cpu);
		if (num_cpu == 0) {
			remove_Cpu.setDisable(true);
		}
		if (numberOfPlayers == 2 && num_cpu > 0) {
			remove_Cpu.setDisable(true);
		}
		if (numberOfPlayers <= 5) {
			add_CPU.setDisable(false);
		}
		List<String> colors_temp = colors;
		List<String> token_temp = tokens;

		if (!playerContainer.getChildren().isEmpty()) {
			// Get the last row
			HBox lastRow = (HBox) playerContainer.getChildren().get(playerContainer.getChildren().size() - 1);
			// Get the color and token ComboBoxes from the last row
			ComboBox<String> colorComboBox = (ComboBox<String>) lastRow.getChildren().get(2);// get color Combo box from
																								// row
			ComboBox<String> tokenComboBox = (ComboBox<String>) lastRow.getChildren().get(3);// get token Combo box from
																								// row
			String playerName = ((TextField) lastRow.getChildren().get(1)).getText(); // get player name from text field
			usedCPUNames.remove(((TextField) lastRow.getChildren().get(1)).getText());

			// Get the selected color and token
			String color = colorComboBox.getValue();
			String token = tokenComboBox.getValue();

			// Remove the last row
			playerContainer.getChildren().remove(lastRow);

			// Add the color and token back to all other ComboBoxes
			for (Node node : playerContainer.getChildren()) {
				if (node instanceof HBox) {
					HBox row = (HBox) node;
					// Get the color, and token comboboxes from the row
					ComboBox<String> rowColorComboBox = (ComboBox<String>) row.getChildren().get(2); // get color Combo
																										// box from row
					ComboBox<String> rowTokenComboBox = (ComboBox<String>) row.getChildren().get(3);// get token Combo
																									// box from row

					if (!rowColorComboBox.getItems().contains(color)) {
						rowColorComboBox.getItems().add(color);
						if (!colors_temp.contains(color)) {
							colors_temp.add(color);
						}
					}

					if (!rowTokenComboBox.getItems().contains(token)) {
						rowTokenComboBox.getItems().add(token);
						if (!token_temp.contains(token)) {
							token_temp.add(token);
						}
					}
				}
			}
		}

	}

	/**
	 * Adds a CPU player to the game. Increases the CPU count, enables the remove
	 * CPU button, and selects a random name for the CPU player. Also updates
	 * available colors and tokens for player selection.
	 */
	private void addCpu() {
		num_cpu++;
		remove_Cpu.setDisable(false);
		String colorForNewCPU = null;
		String tokenForNewCPU = null;
		Random random = new Random();
		String nameCpu;
		do {
			CPUNames randomName = cpuNames.get(random.nextInt(cpuNames.size()));
			nameCpu = randomName.name();
		} while (usedCPUNames.contains(nameCpu));
		usedCPUNames.add(nameCpu);
		List<String> colors_temp = colors;
		// Remove selected color from the list of available colors
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						colors_temp.remove(comboBox.getValue());
					}
				}
			}
		}
		List<String> token_temp = tokens;
		// Remove selected token from the list of available tokens
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						token_temp.remove(comboBox.getValue());
					}
				}
			}
		}
		// Assign color and token for the new CPU player
		if (!colors_temp.isEmpty()) {
			colorForNewCPU = colors_temp.get(0);
		}
		if (!token_temp.isEmpty()) {
			tokenForNewCPU = token_temp.get(0);
		}
		// Initialize the CPU player row with the generated name, color, and token
		innit_cpuRow(nameCpu, colorForNewCPU, tokenForNewCPU);
	}

	/**
	 * Initializes the player rows and sets up listeners for text fields and combo
	 * boxes. Creates player rows based on the number of players specified. Also
	 * adds listeners to text fields and combo boxes to enable/disable buttons based
	 * on user input.
	 */
	private void innit() {
		// Initialize player rows
		for (int index = 0; index < numberOfPlayers; index++) {
			innit_PlayerRow();
		}
		// Set up listeners for text fields and combo boxes
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof TextField) {
						TextField textField = (TextField) child;
						textField.textProperty().addListener((observable, oldValue, newValue) -> {
							checkFieldsAndSetButtonState();
						});
					} else if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
							checkFieldsAndSetButtonState();
						});
					}
				}
			}
		}
	}

	/**
	 * Checks the input fields in player rows and sets the state of the start game
	 * button accordingly. Disables the start game button if any text field is empty
	 * or any combo box has no selected value. Otherwise, enables the start game
	 * button.
	 */
	private void checkFieldsAndSetButtonState() {
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof TextField) {
						TextField textField = (TextField) child;
						if (textField.getText().trim().isEmpty()) {
							start_game_Btn.setDisable(true);
							return;
						}
					} else if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						if (comboBox.getValue() == null || comboBox.getValue().trim().isEmpty()) {
							start_game_Btn.setDisable(true);
							return;
						}
					}
				}
			}
		}
		start_game_Btn.setDisable(false);
	}

	/**
	 * Initializes a player row with input fields for player name, color selection,
	 * and token selection. Adds listeners to update other players' color and token
	 * options based on the selected values.
	 * 
	 * @return The initialized player row.
	 */
	private HBox innit_PlayerRow() {
		HBox playerRow = new HBox(); // Create a new HBox for each player's elements
		Label num = new Label(); // Create a new Label for the player's number
		num.setId("num" + (index)); // unique id
		num.setContentDisplay(ContentDisplay.CENTER);
		num.setPrefHeight(67.0);
		num.setPrefWidth(5.0);
		num.getStylesheets().add("/view/resources/Css/Buttons.css");
		num.getStyleClass().add("comboBox_Nornal");
		num.setText(Integer.toString(index + 1));
		playerRow.getChildren().add(num); // Add to the playerRow HBox
		HBox.setHgrow(num, Priority.ALWAYS); // Make 'num' expand to fill available horizontal space

		TextField playerName = new TextField(); // Create a new playerName for the player's name

		UnaryOperator<TextFormatter.Change> filter = change -> {
			try {
				if (pattern.matcher(change.getControlNewText()).matches()) {
					return change;
				} else {
					throw new IllegalCharacter();
				}
			} catch (IllegalCharacter e) {
				// TODO Auto-generated catch block
				HandleExceptions.showException(e,this, playerName.getScene().getWindow());
				return null;
			}
		};

		playerName.setTextFormatter(new TextFormatter<>(filter));

//	    playerName.setId("playerName" + (index + 1)); // unique id
		playerName.setId("playerName " + (index + 1)); // unique id
		playerName.setPromptText("playerName" + (index + 1));
		playerName.setPrefHeight(65.0);
		playerName.setPrefWidth(191.0);
		playerName.getStylesheets().add("/view/resources/Css/Buttons.css");
		playerName.getStyleClass().add("comboBox_Nornal");
		playerRow.getChildren().add(playerName); // Add to the playerRow HBox
		HBox.setHgrow(playerName, Priority.ALWAYS); // Make 'playerName' expand to fill available horizontal space

		ComboBox<String> color = new ComboBox<>(); // Create a new color for the player's color
		color.setId("color"); // unique id
//	    color.setId("color" + (index + 1)); // unique id

		color.setPrefHeight(65.0);
		color.setPrefWidth(300.0);
		color.getStylesheets().add("/view/resources/Css/Buttons.css");
		color.getStyleClass().add("comboBox_Nornal");
		playerRow.getChildren().add(color); // Add to the playerRow HBox
		HBox.setHgrow(color, Priority.ALWAYS); // Make 'color' expand to fill available horizontal space
		color.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				// Loop through all ComboBoxes
				for (Node node : playerContainer.getChildren()) {
					if (node instanceof HBox) {
						for (Node child : ((HBox) node).getChildren()) {
							if (child instanceof ComboBox) {
								ComboBox<String> comboBox = (ComboBox<String>) child;
								// If the ComboBox isn't the one that triggered the event
								if (!comboBox.equals(color)) {
									// Remove the selected item
									comboBox.getItems().remove(newValue);
									// If the removed item was the selected item in this ComboBox, clear the
									// selection
									if (newValue.equals(comboBox.getValue())) {
										comboBox.setValue(null);
									}
								}
								// If the old value is not null and not currently selected in any ComboBox, add
								// it back
								if (oldValue != null && !isColorSelected(oldValue)) {
									if (!comboBox.equals(color)) {
										comboBox.getItems().add(oldValue);
									}
								}
							}
						}
					}
				}
			}

			private boolean isColorSelected(String color) {
				for (Node node : playerContainer.getChildren()) {
					if (node instanceof HBox) {
						for (Node child : ((HBox) node).getChildren()) {
							if (child instanceof ComboBox) {
								ComboBox<String> comboBox = (ComboBox<String>) child;
								if (color.equals(comboBox.getValue())) {
									return true;
								}
							}
						}
					}
				}
				return false;
			}
		});

		ComboBox<String> token = new ComboBox<>(); // Create a new token for the player's token
//	    token.setId("token" + (index + 1)); // unique id
		token.setId("token"); // unique id

		token.setPrefHeight(65.0);
		token.setPrefWidth(300.0);
		token.getStylesheets().add("/view/resources/Css/Buttons.css");
		token.getStyleClass().add("comboBox_Nornal");
		playerRow.getChildren().add(token); // Add 'token' to the playerRow HBox
		HBox.setHgrow(token, Priority.ALWAYS); // Make 'token' expand to fill available horizontal space
		token.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				// Loop through all ComboBoxes
				for (Node node : playerContainer.getChildren()) {
					if (node instanceof HBox) {
						for (Node child : ((HBox) node).getChildren()) {
							if (child instanceof ComboBox) {
								ComboBox<String> comboBox = (ComboBox<String>) child;
								// If the ComboBox isn't the one that triggered the event
								if (!comboBox.equals(token)) {
									// Remove the selected item
									comboBox.getItems().remove(newValue);
									// If the removed item was the selected item in this ComboBox, clear the
									// selection
									if (newValue.equals(comboBox.getValue())) {
										comboBox.setValue(null);
									}
								}
								// If the old value is not null and not currently selected in any ComboBox, add
								// it back
								if (oldValue != null && !isTokenSelected(oldValue)) {
									if (!comboBox.equals(token)) {
										comboBox.getItems().add(oldValue);
									}
								}
							}
						}
					}
				}
			}

			@SuppressWarnings("unlikely-arg-type")
			private boolean isTokenSelected(String color) {
				for (Node node : playerContainer.getChildren()) {
					if (node instanceof HBox) {
						for (Node child : ((HBox) node).getChildren()) {
							if (child instanceof ComboBox) {
								ComboBox<String> comboBox = (ComboBox<String>) child;
								if (token.equals(comboBox.getValue())) {
									return true;
								}
							}
						}
					}
				}
				return false;
			}
		});

		index++;
		playerContainer.getChildren().add(playerRow); // Add the playerRow HBox to your playerContainer VBox
		VBox.setVgrow(playerRow, Priority.ALWAYS); // Make 'playerRow' expand to fill available vertical space
		// Add token options
		token.getItems().addAll(tokens);

		// Add color options
		color.getItems().addAll(colors);
//	    "Red", "Blue", "Green", "Yellow", "Purple"
		color.setPromptText("Pick Color");
		token.setPromptText("Pick Token");

		return playerRow;
	}

	/**
	 * Initializes a row for a CPU player with the specified name, color, and token.
	 * Disables input fields for the CPU player.
	 * 
	 * @param nameCpu        The name of the CPU player.
	 * @param colorForNewCPU The color of the CPU player.
	 * @param tokenForNewCPU The token of the CPU player.
	 * @return The initialized CPU player row.
	 */
	private HBox innit_cpuRow(String nameCpu, String colorForNewCPU, String tokenForNewCPU) {
		reDraw_comboBox("color", colorForNewCPU);
		reDraw_comboBox("token", tokenForNewCPU);

		HBox playerRow = new HBox(); // Create a new HBox for each player's elements
		Label num = new Label(); // Create a new Label for the player's number
		num.setId("num" + (index)); // unique id
		num.setContentDisplay(ContentDisplay.CENTER);
		num.setPrefHeight(67.0);
		num.setPrefWidth(5.0);
		num.getStylesheets().add("/view/resources/Css/Buttons.css");
		num.getStyleClass().add("comboBox_Nornal");
		num.setText(Integer.toString(index + 1));
		playerRow.getChildren().add(num); // Add to the playerRow HBox
		HBox.setHgrow(num, Priority.ALWAYS); // Make 'num' expand to fill available horizontal space

		TextField playerName = new TextField(); // Create a new playerName for the player's name
		playerName.setId("cpu_playerName " + (index + 1)); // unique id
//	    playerName.setId("playerName"); // unique id

		playerName.setPrefHeight(65.0);
		playerName.setPrefWidth(191.0);
		playerName.getStylesheets().add("/view/resources/Css/Buttons.css");
		playerName.getStyleClass().add("comboBox_Nornal");
		playerName.setText(nameCpu);
		playerName.setDisable(true);
		playerRow.getChildren().add(playerName); // Add to the playerRow HBox
		HBox.setHgrow(playerName, Priority.ALWAYS); // Make 'playerName' expand to fill available horizontal space

		ComboBox<String> color = new ComboBox<>(); // Create a new color for the player's color
//	    color.setId("color" + (index + 1)); // unique id
		color.setId("color"); // unique id

		color.setPrefHeight(65.0);
		color.setPrefWidth(300.0);
		color.getStylesheets().add("/view/resources/Css/Buttons.css");
		color.getStyleClass().add("comboBox_Nornal");
		color.setValue(colorForNewCPU);
		color.setDisable(true);
		playerRow.getChildren().add(color); // Add to the playerRow HBox
		HBox.setHgrow(color, Priority.ALWAYS); // Make 'color' expand to fill available horizontal space

		ComboBox<String> token = new ComboBox<>(); // Create a new token for the player's token
//	    token.setId("token" + (index + 1)); // unique id
		token.setId("token"); // unique id
		token.setPrefHeight(65.0);
		token.setPrefWidth(300.0);
		token.getStylesheets().add("/view/resources/Css/Buttons.css");
		token.getStyleClass().add("comboBox_Nornal");
		token.setValue(tokenForNewCPU);
		token.setDisable(true);
		playerRow.getChildren().add(token); // Add 'token' to the playerRow HBox
		HBox.setHgrow(token, Priority.ALWAYS); // Make 'token' expand to fill available horizontal space
		index++;
		playerContainer.getChildren().add(playerRow); // Add the playerRow HBox to your playerContainer VBox
		VBox.setVgrow(playerRow, Priority.ALWAYS); // Make 'playerRow' expand to fill available vertical space
		// Add token options
		token.getItems().addAll(tokens);
		numberOfPlayers++;
		if (numberOfPlayers == 5) {
			add_CPU.setDisable(true);
		}

		// Add color options
		color.getItems().addAll(colors);
		return playerRow;
	}

	/**
	 * Starts the game with the specified number of players. Retrieves player
	 * information from the playerContainer and initializes Player objects
	 * accordingly. Adds the initialized Player objects to the game data. If a
	 * player is a CPU player, initializes a cpu_Player object instead. Navigates to
	 * the game board view after initialization.
	 * 
	 * @param numberOfPlayers The total number of players in the game.
	 */
	private void startGame(int numberOfPlayers) {
		int counter = 1;
		System.out.println(num_cpu);
		playernames = new ArrayList<TextField>();
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				HBox row = (HBox) node;
				// Get the player name, color, and token from the row
				String playerName = ((TextField) row.getChildren().get(1)).getText();
				playernames.add(((TextField) row.getChildren().get(1))); // player name TextField
			}
		}
		try {
			checkDup();
		} catch (InputIsNotUnique e) {
			// TODO Auto-generated catch block
			HandleExceptions.showException(e,this, return_Btn.getScene().getWindow());
			return;
		}
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				HBox row = (HBox) node;
				// Get the player name, color, and token from the row
				String playerName = ((TextField) row.getChildren().get(1)).getText();
				playernames.add(((TextField) row.getChildren().get(1))); // player name TextField

				String color = ((ComboBox<String>) row.getChildren().get(2)).getValue(); // get color Combo box from row
				String token = ((ComboBox<String>) row.getChildren().get(3)).getValue(); // get token Combo box from row

				TextField row1 = (TextField) row.getChildren().get(1);
				System.out.println(row1.getId());
				if (((TextField) row.getChildren().get(1)).getId().contains("cpu")) {
					// Create Cpu_player
					cpu_Player cpu_Player = new cpu_Player(counter, color, playerName, token);
					GameData.getInstance().addPlayer(cpu_Player);
				} else {
					Player p = new Player(counter, color, playerName, token);
					GameData.getInstance().addPlayer(p);
				}
				counter++;

			}

		}

		// Navigate to the game board
		navigateTo("/view/BoardView.fxml");
	}

	/**
	 * Redraws the specified ComboBox by removing the provided value. If the removed
	 * value was the selected item in the ComboBox, clears the selection.
	 * 
	 * @param comboboxname The name of the ComboBox to redraw.
	 * @param newValue     The value to remove from the ComboBox.
	 */
	public void reDraw_comboBox(String comboboxname, String newValue) {
		// Loop through all ComboBoxes
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof ComboBox) {
						if (child.getId().equals(comboboxname)) {

							ComboBox<String> comboBox = (ComboBox<String>) child;
							// If the ComboBox isn't the one that triggered the event
							// Remove the selected item

							comboBox.getItems().remove(newValue);
							// If the removed item was the selected item in this ComboBox, clear the
							// selection
							if (newValue.equals(comboBox.getValue())) {
								comboBox.setValue(null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Navigates to a new scene defined by the provided FXML file.
	 * 
	 * @param fxmlFile The path to the FXML file defining the new scene.
	 */
	private void navigateTo(String fxmlFile) {
		try {
			Stage stage = (Stage) start_game_Btn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
			stage.setMinWidth(width + 10); // Minimum width: 300 pixels
			stage.setMinHeight(height + 10); // Minimum height: 200 pixels
//			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if there are duplicate player names entered in the text fields. Throws
	 * an InputIsNotUnique exception if duplicates are found.
	 * 
	 * @return True if there are no duplicate player names, false otherwise.
	 * @throws InputIsNotUnique If duplicate player names are found.
	 */
	private boolean checkDup() throws InputIsNotUnique {
		Set<String> inputs = new HashSet<>();
		for (TextField f : playernames) {
			String input = f.getText();
			if (!inputs.add(input.toLowerCase())) {
				throw new InputIsNotUnique(f.getId() + " " + f.getText());
			}
		}
		return true;
	}

}