package control;
import exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    
	private String path = "/Json/Admin.txt";
	private File file;
	private User admin;
	
    public void initialize()  {
    	

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
		//Show as text
	 	username_text.setText(admin.getDecryptedUsername());
    	password_text.setText(admin.getDecryptedPassword());
		
	}
    

    private void save() throws IOException {
		// TODO Auto-generated method stub
    	String encryptedUsername = admin.encrypt(username_text.getText());
        admin.setUsername(encryptedUsername);
        admin.writeAdminJson(admin);
        String encryptedPassword = admin.encrypt(password_text.getText());
        admin.setPassword(encryptedPassword);
        admin.writeAdminJson(admin);

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
    	Stage stage = (Stage)cancel_btn.getScene().getWindow();
    	stage.close();
    }


  
   }
	

