package control;

import exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QuestionsFromJson;
import model.User;

public class AdminSettingsControl {
	private QuestionWizControl previousWindow;

	@FXML
	private Button cancel_btn;

	@FXML
	private Label password;

	@FXML
	private TextField password_text;

	@FXML
	private Button save_btn;

	@FXML
	private Label username;

	@FXML
	private TextField username_text;

	private String path = "Json/Admin.txt";
	private File file;
	private User admin;
	List<TextField> textAreaList;
	Pattern pattern = Pattern.compile("[a-zA-Z0-9]{0,10}");

	public void initialize() {
		textAreaList = new ArrayList<>();

		save_btn.setOnAction(event -> {
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		ObjectMapper mapper = new ObjectMapper();
		try {
			File file = new File(path);
			admin = mapper.readValue(file, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cancel_btn.setOnAction(event -> cancel());
		// Show as text
		username_text.setText(admin.getDecryptedUsername());
		password_text.setText(admin.getDecryptedPassword());
		textAreaList.add(username_text);
		textAreaList.add(password_text);
		UnaryOperator<TextFormatter.Change> filter = change -> {
			try {
				if (pattern.matcher(change.getControlNewText()).matches()) {
					return change;
				} else {
					throw new IllegalCharacter();
				}
			} catch (IllegalCharacter e) {
				// TODO Auto-generated catch block
				HandleExceptions.showException(e, this, username_text.getScene().getWindow());
				return null;
			}
		};

		username_text.setTextFormatter(new TextFormatter<>(filter));
		password_text.setTextFormatter(new TextFormatter<>(filter));

	}

	private void save() throws IOException {
		// TODO Auto-generated method stub
		try {
			if(checkEmpty()) {
			String encryptedUsername = admin.encrypt(username_text.getText());
			admin.setUsername(encryptedUsername);
			admin.writeAdminJson(admin);
			String encryptedPassword = admin.encrypt(password_text.getText());
			admin.setPassword(encryptedPassword);
			admin.writeAdminJson(admin);
			showSaveMessage();
			// Close the window after saving
			Stage stage = (Stage) username_text.getScene().getWindow();
			stage.close();

			}
		} catch (InputIsEmpty | InputIsNotUnique e) {
			// TODO Auto-generated catch block
			HandleExceptions.showException(e, this, username_text.getScene().getWindow());
		}
	}
	public void showSaveMessage() {
		Stage ownerPopUp = null;

		DialogPane dialogPane = new DialogPane();
		dialogPane.setHeaderText(null);
		dialogPane.getButtonTypes().addAll(ButtonType.OK);
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setDialogPane(dialogPane);
		dialog.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
		dialog.setTitle("Save Question");

		try {
			ownerPopUp = (Stage) (username_text).getScene().getWindow();
			dialog.initOwner(ownerPopUp); // Initialize the owner of the pop-up stage
		} catch (Exception e) {
			e.printStackTrace();
		}
		dialogPane.setContentText("Change info succesfuly");

		dialog.showAndWait();
		ownerPopUp.close();
	}
	public boolean checkEmpty() throws InputIsEmpty, InputIsNotUnique {
//		Set<String> inputs = new HashSet<>();
//		String temp = username_text.getText().replaceAll("\\p{Punct}", "").replaceAll("\\s", "").replaceAll("\\d", "");
//		String temp2 = username_text.getText().replaceAll("\\p{Punct}", "").replaceAll("\\s", "").replaceAll("\\d", "");
//
//		if (temp.isEmpty() || temp2.isEmpty()) {
//			throw new InputIsEmpty(username_text.getId());
//		}

		for (TextField f : textAreaList) {
			String input = f.getText().toLowerCase();
			input = input.replaceAll("\\p{Punct}", "").replaceAll("\\s", "");
			if (input.isEmpty()) {
				throw new InputIsEmpty(f.getId());
			}
		}
		return true;
	}

	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		previousWindow = questionWizControl2;
	}

	@FXML
	public void saveUsername() throws IOException {
		String encryptedUsername = admin.encrypt(username_text.getText());
		admin.setUsername(encryptedUsername);
		admin.writeAdminJson(admin);
//    	String encryptedUsername = admin.encrypt(username_text.getText());
//      admin.setUsername(username_text.getText());
//      admin.writeAdminJson(admin);
	}

	@FXML
	public void savePassword() throws IOException {
		String encryptedPassword = admin.encrypt(password_text.getText());
		admin.setPassword(encryptedPassword);
		admin.writeAdminJson(admin);
//        String encryptedPassword = admin.encrypt(password_text.getText());
//        admin.setPassword(password_text.getText());
//        admin.writeAdminJson(admin);
	}

	@FXML
	public void cancel() {
		Stage stage = (Stage) cancel_btn.getScene().getWindow();
		stage.close();
	}

}
