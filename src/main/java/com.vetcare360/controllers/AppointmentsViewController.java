package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Visit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AppointmentsViewController {
    @FXML private TableView<Visit> visitTable;
    @FXML private TableColumn<Visit, String> petNameColumn;
    @FXML private TableColumn<Visit, String> vetNameColumn;
    @FXML private TableColumn<Visit, String> dateTimeColumn;
    @FXML private TableColumn<Visit, String> reasonColumn;
    @FXML private TableColumn<Visit, String> diagnosisColumn;

    private final DataService dataService = DataService.getInstance();

    @FXML
    public void initialize() {
        petNameColumn.setCellValueFactory(new PropertyValueFactory<>("petName"));
        vetNameColumn.setCellValueFactory(new PropertyValueFactory<>("vetName"));
        dateTimeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        
        refreshTable();
        
        visitTable.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        ObservableList<Visit> list = FXCollections.observableArrayList(dataService.getAllVisits());
        visitTable.setItems(list);
    }

    @FXML
    private void onAddVisitClick() {
        com.vetcare360.utils.Navigator.navigateTo("AddVisitForm.fxml");
    }


    @FXML
    private void onEditClick() {
        Visit selected = visitTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an appointment to edit.");
            return;
        }
        com.vetcare360.controllers.VisitFormController.editVisit = selected;
        com.vetcare360.utils.Navigator.navigateTo("AddVisitForm.fxml");
    }
    @FXML
    private void onDeleteClick() {
        Visit selected = visitTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an appointment to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Delete Appointment");
        confirm.setHeaderText(null);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            dataService.deleteVisit(selected);
            refreshTable();
            showAlert("Appointment deleted successfully.");
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
