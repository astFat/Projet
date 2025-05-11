package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Visit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
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
    }

    private void refreshTable() {
        ObservableList<Visit> list = FXCollections.observableArrayList(dataService.getAllVisits());
        visitTable.setItems(list);
    }

    @FXML
    private void onAddClick() {
        Dialog<Visit> dialog = new Dialog<>();
        dialog.setTitle("Add Appointment");
        dialog.setHeaderText("Enter appointment details");
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField petNameField = new TextField();
        petNameField.setPromptText("Pet Name (e.g. Max)");
        TextField vetNameField = new TextField();
        vetNameField.setPromptText("Veterinarian (e.g. John Smith)");
        TextField dateTimeField = new TextField();
        dateTimeField.setPromptText("Date & Time (yyyy-MM-dd HH:mm)");
        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason");
        TextField diagnosisField = new TextField();
        diagnosisField.setPromptText("Diagnosis");

        VBox content = new VBox(8, new Label("Pet Name:"), petNameField,
                new Label("Veterinarian:"), vetNameField,
                new Label("Date & Time:"), dateTimeField,
                new Label("Reason:"), reasonField,
                new Label("Diagnosis:"), diagnosisField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    Visit visit = new Visit(0, petNameField.getText(), vetNameField.getText(), dateTime, reasonField.getText());
                    visit.setDiagnosis(diagnosisField.getText());
                    return visit;
                } catch (Exception e) {
                    showAlert("Invalid date/time format. Use yyyy-MM-dd HH:mm");
                }
            }
            return null;
        });

        Optional<Visit> result = dialog.showAndWait();
        result.ifPresent(visit -> {
            dataService.getAllVisits().add(visit);
            refreshTable();
        });
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
            dataService.getAllVisits().remove(selected);
            refreshTable();
        }
    }

    @FXML
    private void onAddVisitClick() {
        com.vetcare360.utils.Navigator.navigateTo("AddVisitForm.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 