package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import java.util.Scanner;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    ListView list;

    @FXML
    TextField nameBar;

    @FXML
    TextField phoneBar;

    @FXML
    TextField emailBar;

    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();


    public void addContact() throws Exception{

        Contact c = new Contact(nameBar.getText(),phoneBar.getText(),emailBar.getText());
        if(nameBar.getText().isEmpty() | phoneBar.getText().isEmpty() | emailBar.getText().isEmpty()) {

        }else {
            contacts.add(c);
            nameBar.setText("");
            phoneBar.setText("");
            emailBar.setText("");
            saveContact();

        }

    }
    public void removeContact() throws Exception{
        Contact contact = (Contact) list.getSelectionModel().getSelectedItem();
        contacts.remove(contact);
        saveContact();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        list.setItems(contacts);
        try {
            loadContact();
        } catch (Exception e) {}



    }
    public static void saveContact() throws IOException {
        JsonSerializer s = new JsonSerializer();
        String json = s.include("*").serialize(contacts);
        File f = new File("contact.json");
        FileWriter fw = new FileWriter(f);
        fw.write(json);
        fw.close();
    }
    public static Contact loadContact() throws FileNotFoundException {
        File f = new File("contact.json");
        Scanner s = new Scanner(f);
        s.useDelimiter("\\Z");
        String contents = s.next();
        JsonParser p = new JsonParser();
        return p.parse(contents, Contact.class);
    }



}
