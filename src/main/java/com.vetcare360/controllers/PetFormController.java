package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Owner;
import com.vetcare360.models.Pet;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PetFormController {
    @FXML private TextField nameField;
    @FXML private TextField speciesField;
    @FXML private TextField breedField;
    @FXML private TextField ageField;
    @FXML private ComboBox<Owner> ownerComboBox;
    @FXML private Label formTitleLabel;

    private DataService dataService;
    public static Pet selectedPet = null;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        
        // Set up owner combo box
        ownerComboBox.getItems().addAll(dataService.getAllOwners());
        ownerComboBox.setCellFactory(lv -> new ListCell<Owner>() {
            @Override
            protected void updateItem(Owner owner, boolean empty) {
                super.updateItem(owner, empty);
                setText(empty ? "" : owner.getFirstName() + " " + owner.getLastName());
            }
        });
        ownerComboBox.setButtonCell(ownerComboBox.getCellFactory().call(null));

        // If editing existing pet, populate fields
        if (selectedPet != null) {
            formTitleLabel.setText("Edit Pet");
            nameField.setText(selectedPet.getName());
            speciesField.setText(selectedPet.getSpecies());
            breedField.setText(selectedPet.getBreed());
            ageField.setText(String.valueOf(selectedPet.getAge()));
            // Find and select the owner
            for (Owner owner : ownerComboBox.getItems()) {
                if ((owner.getFirstName() + " " + owner.getLastName()).equals(selectedPet.getOwnerName())) {
                    ownerComboBox.setValue(owner);
                    break;
                }
            }
        } else {
            formTitleLabel.setText("Add New Pet");
        }
    }

    @FXML
    private void onBackClick() {
        selectedPet = null;
        Navigator.navigateTo("PetList.fxml");
    }

    @FXML
    private void onSaveClick() {
        try {
            String name = nameField.getText().trim();
            String species = speciesField.getText().trim();
            String breed = breedField.getText().trim();
            String ageText = ageField.getText().trim();
            Owner selectedOwner = ownerComboBox.getValue();

            if (name.isEmpty() || species.isEmpty() || breed.isEmpty() || 
                ageText.isEmpty() || selectedOwner == null) {
                showAlert("Please fill in all fields");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                String ownerName = selectedOwner.getFirstName() + " " + selectedOwner.getLastName();
                
                if (selectedPet != null) {
                    // Update existing pet
                    selectedPet.setName(name);
                    selectedPet.setSpecies(species);
                    selectedPet.setBreed(breed);
                    selectedPet.setAge(age);
                    selectedPet.setOwnerName(ownerName);
                    showAlert("Pet updated successfully");
                } else {
                    // Add new pet
                    dataService.addPet(name, species, breed, age, ownerName);
                    showAlert("Pet added successfully");
                }
                selectedPet = null;
                Navigator.navigateTo("PetList.fxml");
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid age");
            }
        } catch (Exception e) {
            showAlert("Error saving pet: " + e.getMessage());
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
