package control;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import enums.CPUNames;
import enums.Colors;
import enums.Tokens;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Board;
import model.GameData;
import model.Player;

public class PlayersControl {

	@FXML
	private Button return_Btn; // Add this line

	@FXML
	private Button start_game_Btn; // Add this line

	@FXML

	private VBox playerContainer;
	@FXML
	private ScrollPane PlayersPane;

	@FXML
	private Button add_CPU;

	@FXML
	private Button remove_Cpu;
	private int num_cpu = 0;
//	private LinkedList <Player> players = new LinkedList<Player>();
	List<String> tokens = Arrays.stream(Tokens.values()).map(Enum::name).collect(Collectors.toList());
	List<String> colors = Arrays.stream(Colors.values()).map(Enum::name).collect(Collectors.toList());
	List<CPUNames> cpuNames = Arrays.asList(CPUNames.values());
	int index = 0;
	int numberOfPlayers = GameData.getInstance().getNumberOfPlayers();

	@FXML
	public void initialize() {
		start_game_Btn.setDisable(true);
		PlayersPane.setFitToWidth(true);
		PlayersPane.setFitToHeight(true);
		if (numberOfPlayers == 5) {
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
			remove_CPU();
		});
	}

	private void remove_CPU() {
		// TODO Auto-generated method stub
		numberOfPlayers--;
		num_cpu--;
		if (num_cpu == 0) {
			remove_Cpu.setDisable(true);
		}
		if (num_cpu + numberOfPlayers == 5) {
			add_CPU.setDisable(false);
		}
		List<String> colors_temp = colors;
		List<String> token_temp = tokens;

		if (!playerContainer.getChildren().isEmpty()) {
			// Get the last row
			HBox lastRow = (HBox) playerContainer.getChildren().get(playerContainer.getChildren().size() - 1);
			// Get the color and token ComboBoxes from the last row
			ComboBox<String> colorComboBox = (ComboBox<String>) lastRow.getChildren().get(2);
			ComboBox<String> tokenComboBox = (ComboBox<String>) lastRow.getChildren().get(3);

			// Get the selected color and token
			String color = colorComboBox.getValue();
			String token = tokenComboBox.getValue();

			// Remove the last row
			playerContainer.getChildren().remove(lastRow);

			// Add the color and token back to all other ComboBoxes
			for (Node node : playerContainer.getChildren()) {
				if (node instanceof HBox) {
					HBox row = (HBox) node;
					ComboBox<String> rowColorComboBox = (ComboBox<String>) row.getChildren().get(2);
					ComboBox<String> rowTokenComboBox = (ComboBox<String>) row.getChildren().get(3);

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

	private void addCpu() {
		num_cpu++;
		remove_Cpu.setDisable(false);
		String colorForNewCPU = null;
		String tokenForNewCPU = null;
		Random random = new Random();
		CPUNames randomName = cpuNames.get(random.nextInt(cpuNames.size()));
		String nameCpu = randomName.name();

		List<String> colors_temp = colors;
		// TODO Auto-generated method stub
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						colors_temp.remove(comboBox.getValue()); // Remove the selected color from the list
					}
				}
			}
		}
		List<String> token_temp = tokens;
		// TODO Auto-generated method stub
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof ComboBox) {
						ComboBox<String> comboBox = (ComboBox<String>) child;
						token_temp.remove(comboBox.getValue()); // Remove the selected color from the list
					}
				}
			}
		}
		if (!colors_temp.isEmpty()) {
			colorForNewCPU = colors_temp.get(0);
			// Add your code here to set the color for the new CPU player
		}
		if (!token_temp.isEmpty()) {
			tokenForNewCPU = token_temp.get(0);
			// Add your code here to set the color for the new CPU player
		}
		innit_cpuRow(nameCpu, colorForNewCPU, tokenForNewCPU);
	}

	private void innit() {

		for (int index = 0; index < numberOfPlayers; index++) {
			innit_PlayerRow();
		}
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
//		playerName.setId("playerName" + (index + 1)); // unique id
		playerName.setId("playerName"); // unique id
		playerName.setPromptText("playerName" + (index + 1));
		playerName.setPrefHeight(65.0);
		playerName.setPrefWidth(191.0);
		playerName.getStylesheets().add("/view/resources/Css/Buttons.css");
		playerName.getStyleClass().add("comboBox_Nornal");
		playerRow.getChildren().add(playerName); // Add to the playerRow HBox
		HBox.setHgrow(playerName, Priority.ALWAYS); // Make 'playerName' expand to fill available horizontal space

		ComboBox<String> color = new ComboBox<>(); // Create a new color for the player's color
		color.setId("color"); // unique id
//		color.setId("color" + (index + 1)); // unique id

		color.setPrefHeight(65.0);
		color.setPrefWidth(240.0);
		color.getStylesheets().add("/view/resources/Css/Buttons.css");
		color.getStyleClass().add("comboBox_Nornal");
		playerRow.getChildren().add(color); // Add to the playerRow HBox
		HBox.setHgrow(color, Priority.ALWAYS); // Make 'color' expand to fill available horizontal space
		// Assuming 'color' is your ComboBox and 'colors' is the ObservableList of items
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
//		token.setId("token" + (index + 1)); // unique id
		token.setId("token"); // unique id

		token.setPrefHeight(65.0);
		token.setPrefWidth(240.0);
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
//		"Red", "Blue", "Green", "Yellow", "Purple"
		return playerRow;
	}

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
//		playerName.setId("playerName" + (index + 1)); // unique id
		playerName.setId("playerName"); // unique id

		playerName.setPrefHeight(65.0);
		playerName.setPrefWidth(191.0);
		playerName.getStylesheets().add("/view/resources/Css/Buttons.css");
		playerName.getStyleClass().add("comboBox_Nornal");
		playerName.setText(nameCpu);
		playerName.setDisable(true);
		playerRow.getChildren().add(playerName); // Add to the playerRow HBox
		HBox.setHgrow(playerName, Priority.ALWAYS); // Make 'playerName' expand to fill available horizontal space

		ComboBox<String> color = new ComboBox<>(); // Create a new color for the player's color
//		color.setId("color" + (index + 1)); // unique id
		color.setId("color"); // unique id

		color.setPrefHeight(65.0);
		color.setPrefWidth(240.0);
		color.getStylesheets().add("/view/resources/Css/Buttons.css");
		color.getStyleClass().add("comboBox_Nornal");
		color.setValue(colorForNewCPU);
		color.setDisable(true);
		playerRow.getChildren().add(color); // Add to the playerRow HBox
		HBox.setHgrow(color, Priority.ALWAYS); // Make 'color' expand to fill available horizontal space

		ComboBox<String> token = new ComboBox<>(); // Create a new token for the player's token
//		token.setId("token" + (index + 1)); // unique id
		token.setId("token"); // unique id
		token.setPrefHeight(65.0);
		token.setPrefWidth(240.0);
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
//		"Red", "Blue", "Green", "Yellow", "Purple"
		return playerRow;
	}

	private void startGame(int numberOfPlayers) {
		int counter = 1;

		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				HBox row = (HBox) node;

				// Get the player name, color, and token from the row
				String playerName = ((TextField) row.getChildren().get(1)).getText(); // Replace 1 with the index of the
																						// player name TextField in your
																						// HBox
				String color = ((ComboBox<String>) row.getChildren().get(2)).getValue(); // Replace 2 with the index of
																							// the color ComboBox in
																							// your HBox
				String token = ((ComboBox<String>) row.getChildren().get(3)).getValue(); // Replace 3 with the index of
																							// the token ComboBox in
																							// your HBox

				// Create a new Player object and add it to the list
				Player p = new Player(counter, color, playerName, token);
				counter++;
//	            players.add(p);
				GameData.getInstance().addPlayer(p);
				GameData.getInstance().setNumberOfPlayers(GameData.getInstance().getplayer_list().size());
			}

		}
//		GameData.getInstance().setPlayers(players);

		// Navigate to the game board
		navigateTo("/view/BoardView.fxml");
	}

	public void reDraw_comboBox(String comboboxname, String newValue) {

		// Loop through all ComboBoxes
		for (Node node : playerContainer.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
//					String str = child.getId().substring(0, child.getId().length() - 1);
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

	private void navigateTo(String fxmlFile) {
		try {
			Stage stage = (Stage) start_game_Btn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}