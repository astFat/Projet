package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Owner;
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
        
        if (OwnerListController.selectedOwner != null) {
            Owner owner = OwnerListController.selectedOwner;
            firstNameField.setText(owner.getFirstName());
            lastNameField.setText(owner.getLastName());
            phoneField.setText(owner.getPhone());
            emailField.setText(owner.getEmail());
            addressField.setText(owner.getAddress());
        }
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
            Owner ownerToEdit = OwnerListController.selectedOwner;
            
            if (ownerToEdit != null) {
                ownerToEdit.setFirstName(firstName);
                ownerToEdit.setLastName(lastName);
                ownerToEdit.setPhone(phone);
                ownerToEdit.setEmail(email);
                ownerToEdit.setAddress(address);
                showAlert("Owner updated successfully");
            } else {
                dataService.addOwner(firstName, lastName, phone, email, address);
                showAlert("Owner added successfully");
            }
            
            OwnerListController.selectedOwner = null;
            Navigator.navigateTo("OwnerList.fxml");
        } catch (Exception e) {
            showAlert("Error saving owner: " + e.getMessage());
            e.printStackTrace();
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
