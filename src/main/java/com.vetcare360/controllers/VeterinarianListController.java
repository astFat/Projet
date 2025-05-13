package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Veterinarian;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class VeterinarianListController {
    @FXML private TableView<Veterinarian> vetTable;
    @FXML private TableColumn<Veterinarian, String> firstNameColumn;
    @FXML private TableColumn<Veterinarian, String> lastNameColumn;
    @FXML private TableColumn<Veterinarian, String> specializationColumn;
    @FXML private TableColumn<Veterinarian, String> phoneColumn;
    @FXML private TableColumn<Veterinarian, String> emailColumn;

    private DataService dataService = DataService.getInstance();
    private Veterinarian selectedVeterinarian;

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        refreshTable();
    }

    private void refreshTable() {
        List<Veterinarian> vets = dataService.getAllVeterinarians();
        ObservableList<Veterinarian> list = FXCollections.observableArrayList(vets);
        vetTable.setItems(list);
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("Home.fxml");
    }

    @FXML
    private void onAddClick() {
        VeterinarianFormController.selectedVeterinarian = null;
        Navigator.navigateTo("NewVeterinarianForm.fxml");
    }

    @FXML
    private void onEditClick() {
        selectedVeterinarian = vetTable.getSelectionModel().getSelectedItem();
        if (selectedVeterinarian == null) {
            showAlert("Please select a veterinarian to edit");
            return;
        }
        VeterinarianFormController.selectedVeterinarian = selectedVeterinarian;
        Navigator.navigateTo("NewVeterinarianForm.fxml");
    }

    @FXML
    private void onDeleteClick() {
        Veterinarian selectedVet = vetTable.getSelectionModel().getSelectedItem();
        if (selectedVet == null) {
            showAlert("Please select a veterinarian to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this veterinarian?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                vetTable.getItems().remove(selectedVet);
                showAlert("Veterinarian deleted successfully");
            } catch (Exception e) {
                showAlert("Error deleting veterinarian: " + e.getMessage());
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
