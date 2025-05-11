package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Pet;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PetListController {
    @FXML private TableView<Pet> petTable;
    @FXML private TableColumn<Pet, String> nameColumn;
    @FXML private TableColumn<Pet, String> speciesColumn;
    @FXML private TableColumn<Pet, String> breedColumn;
    @FXML private TableColumn<Pet, Integer> ageColumn;
    @FXML private TableColumn<Pet, String> ownerColumn;

    private DataService dataService;
    private Pet selectedPet;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        setupTable();
        refreshTable();
    }

    private void setupTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        petTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onEditClick();
            }
        });
    }

    private void refreshTable() {
        petTable.getItems().clear();
        petTable.getItems().addAll(dataService.getAllPets());
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("Home.fxml");
    }

    @FXML
    private void onAddClick() {
        Navigator.navigateTo("NewPetForm.fxml");
    }

    @FXML
    private void onEditClick() {
        Pet selected = petTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a pet to edit");
            return;
        }
        PetFormController.selectedPet = selected;
        Navigator.navigateTo("NewPetForm.fxml");
    }

    @FXML
    private void onDeleteClick() {
        Pet selectedPet = petTable.getSelectionModel().getSelectedItem();
        if (selectedPet == null) {
            showAlert("Please select a pet to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this pet?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                petTable.getItems().remove(selectedPet);
                showAlert("Pet deleted successfully");
            } catch (Exception e) {
                showAlert("Error deleting pet: " + e.getMessage());
            }
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