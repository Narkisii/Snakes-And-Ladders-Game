package control;
import exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.QuestionsFromJson;
import model.User;

public class AdminSettingsControl {
	   private QuestionWizControl previousWindow;
	@FXML
    private TextField username_text;

    @FXML
    private TextField password_text;
    
    @FXML
    private Button save_password_btn;
    
    @FXML
    private Button save_username_btn;
    
	private String path;
	private File file;
	private User admin;

    public void initialize()  {
    	
    	save_username_btn.setOnAction(event -> {
			try {
				saveUsername();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	save_password_btn.setOnAction(event -> {
			try {
				savePassword();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    	    path = "src/Json/Admin.txt";
    	    File file = new File(path);
    	    admin = mapper.readValue(file, User.class);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

    	
		//Show as text
	 	username_text.setText(decrypt(admin.getUsername()));
    	password_text.setText(decrypt(admin.getPassword()));
		
	}
    

    public void setPreviousWindow(QuestionWizControl questionWizControl2) {
        previousWindow = questionWizControl2;
    }

    @FXML
    public void saveUsername() throws IOException {
    	
    	String encryptedUsername = encrypt(username_text.getText());
        admin.setUsername(encryptedUsername);
        writeAdminJson(admin);
    }

    @FXML
    public void savePassword() throws IOException {
        String encryptedPassword = encrypt(password_text.getText());
        admin.setPassword(encryptedPassword);
        writeAdminJson(admin);

    }
    
    
    @FXML
    public void cancel() {

    
    }
    public void writeAdminJson(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Convert object to JSON string and save into a file directly
        try {
            mapper.writeValue(new File(path), user);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException
        }
    }
    
    public String encrypt(String str) {
        // This is a very basic encryption method known as the Caesar Cipher.
        // In a production environment, please use a more secure encryption method.
        String shifted = str.chars().mapToObj(c -> (char)(c + 3)).map(String::valueOf).collect(Collectors.joining());
        return Base64.getEncoder().encodeToString(shifted.getBytes());
    }
    
    public String decrypt(String str) {
        String decoded = new String(Base64.getDecoder().decode(str));
        return decoded.chars().mapToObj(c -> (char)(c - 3)).map(String::valueOf).collect(Collectors.joining());
    }


  
   }
	

