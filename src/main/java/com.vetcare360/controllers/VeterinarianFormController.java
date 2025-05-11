package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Veterinarian;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class VeterinarianFormController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField specializationField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private Label formTitleLabel;
    public static Veterinarian selectedVeterinarian = null;

    private DataService dataService;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        if (selectedVeterinarian != null) {
            formTitleLabel.setText("Edit Veterinarian");
            firstNameField.setText(selectedVeterinarian.getFirstName());
            lastNameField.setText(selectedVeterinarian.getLastName());
            specializationField.setText(selectedVeterinarian.getSpecialization());
            phoneField.setText(selectedVeterinarian.getPhone());
            emailField.setText(selectedVeterinarian.getEmail());
        } else {
            formTitleLabel.setText("Add New Veterinarian");
        }
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("VeterinarianList.fxml");
    }

    @FXML
    private void onSaveClick() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
            specializationField.getText().isEmpty() || phoneField.getText().isEmpty() ||
            emailField.getText().isEmpty()) {
            showAlert("All fields must be filled");
            return;
        }
        try {
            if (selectedVeterinarian != null) {
                // Edit mode: update existing veterinarian
                selectedVeterinarian.setFirstName(firstNameField.getText());
                selectedVeterinarian.setLastName(lastNameField.getText());
                selectedVeterinarian.setSpecialization(specializationField.getText());
                selectedVeterinarian.setPhone(phoneField.getText());
                selectedVeterinarian.setEmail(emailField.getText());
                showAlert("Veterinarian updated successfully");
            } else {
                // Add mode: create new veterinarian
                dataService.addVeterinarian(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    specializationField.getText(),
                    phoneField.getText(),
                    emailField.getText()
                );
                showAlert("Veterinarian added successfully");
            }
            selectedVeterinarian = null;
            Navigator.navigateTo("VeterinarianList.fxml");
        } catch (Exception e) {
            showAlert("Error saving veterinarian: " + e.getMessage());
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