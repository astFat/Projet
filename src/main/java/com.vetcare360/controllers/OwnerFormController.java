package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OwnerFormController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;

    private DataService dataService;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("OwnerList.fxml");
    }

    @FXML
    private void onSaveClick() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || 
            email.isEmpty() || address.isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }

        try {
            dataService.addOwner(firstName, lastName, phone, email, address);
            showAlert("Owner added successfully");
            Navigator.navigateTo("OwnerList.fxml");
        } catch (Exception e) {
            showAlert("Error adding owner: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
