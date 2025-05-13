package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Owner;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OwnerListController {
    @FXML private TableView<Owner> ownerTable;
    @FXML private TableColumn<Owner, String> firstNameColumn;
    @FXML private TableColumn<Owner, String> lastNameColumn;
    @FXML private TableColumn<Owner, String> phoneColumn;
    @FXML private TableColumn<Owner, String> emailColumn;
    @FXML private TableColumn<Owner, String> addressColumn;

    private DataService dataService;
    private Owner selectedOwner;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        setupTable();
        refreshTable();
    }

    private void setupTable() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        ownerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Owner selected = ownerTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    com.vetcare360.controllers.OwnerDetailsController.selectedOwner = selected;
                    Navigator.navigateTo("OwnerDetails.fxml");
                }
            }
        });
    }

    private void refreshTable() {
        ownerTable.getItems().clear();
        ownerTable.getItems().addAll(dataService.getAllOwners());
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("Home.fxml");
    }

    @FXML
    private void onAddClick() {
        Navigator.navigateTo("NewOwnerForm.fxml");
    }

    @FXML
    private void onEditClick() {
        selectedOwner = ownerTable.getSelectionModel().getSelectedItem();
        if (selectedOwner == null) {
            showAlert("Please select an owner to edit");
            return;
        }

        Navigator.navigateTo("NewOwnerForm.fxml");
    }

    @FXML
    private void onDeleteClick() {
        Owner selectedOwner = ownerTable.getSelectionModel().getSelectedItem();
        if (selectedOwner == null) {
            showAlert("Please select an owner to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this owner?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                ownerTable.getItems().remove(selectedOwner);
                showAlert("Owner deleted successfully");
            } catch (Exception e) {
                showAlert("Error deleting owner: " + e.getMessage());
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
