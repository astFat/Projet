package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Pet;
import com.vetcare360.models.Veterinarian;
import com.vetcare360.models.Visit;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;

public class VisitFormController {
    @FXML private ComboBox<Pet> petComboBox;
    @FXML private ComboBox<Veterinarian> vetComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextArea reasonField;
    @FXML private TextArea diagnosisArea;

    private DataService dataService;
    public static Visit editVisit;

    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        setupComboBoxes();
        if (editVisit != null) {
            for (Pet pet : petComboBox.getItems()) {
                if (pet.getName().equals(editVisit.getPetName())) {
                    petComboBox.setValue(pet);
                    break;
                }
            }
            for (Veterinarian vet : vetComboBox.getItems()) {
                String vetFullName = vet.getFirstName() + " " + vet.getLastName();
                if (vetFullName.equals(editVisit.getVetName())) {
                    vetComboBox.setValue(vet);
                    break;
                }
            }
            if (editVisit.getDateTime() != null) {
                datePicker.setValue(editVisit.getDateTime().toLocalDate());
                timeField.setText(String.format("%02d:%02d", editVisit.getDateTime().getHour(), editVisit.getDateTime().getMinute()));
            }
            reasonField.setText(editVisit.getReason());
            diagnosisArea.setText(editVisit.getDiagnosis());
        }
    }

    private void setupComboBoxes() {
        petComboBox.getItems().addAll(dataService.getAllPets());
        petComboBox.setCellFactory(lv -> new ListCell<Pet>() {
            @Override
            protected void updateItem(Pet pet, boolean empty) {
                super.updateItem(pet, empty);
                setText(empty ? "" : pet.getName());
            }
        });
        petComboBox.setButtonCell(petComboBox.getCellFactory().call(null));

        vetComboBox.getItems().addAll(dataService.getAllVeterinarians());
        vetComboBox.setCellFactory(lv -> new ListCell<Veterinarian>() {
            @Override
            protected void updateItem(Veterinarian vet, boolean empty) {
                super.updateItem(vet, empty);
                setText(empty ? "" : vet.getFirstName() + " " + vet.getLastName());
            }
        });
        vetComboBox.setButtonCell(vetComboBox.getCellFactory().call(null));
    }

    @FXML
    private void onBackClick() {
        Navigator.navigateTo("AppointmentsView.fxml");
    }

    @FXML
    private void onSaveClick() {
        if (petComboBox.getValue() == null || vetComboBox.getValue() == null ||
            datePicker.getValue() == null || timeField.getText().isEmpty() ||
            reasonField.getText().isEmpty()) {
            showAlert("All fields must be filled");
            return;
        }

        try {
            String[] timeParts = timeField.getText().split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            LocalDateTime dateTime = LocalDateTime.of(
                datePicker.getValue(),
                java.time.LocalTime.of(hour, minute)
            );
            Pet selectedPet = petComboBox.getValue();
            Veterinarian selectedVet = vetComboBox.getValue();

            if (editVisit != null) {
                editVisit.setPetName(selectedPet.getName());
                editVisit.setVetName(selectedVet.getFirstName() + " " + selectedVet.getLastName());
                editVisit.setDateTime(dateTime);
                editVisit.setReason(reasonField.getText());
                editVisit.setDiagnosis(diagnosisArea.getText());
                showAlert("Visit updated successfully");
                editVisit = null;
            } else {
                Visit visit = new Visit(0,
                    selectedPet.getName(),
                    selectedVet.getFirstName() + " " + selectedVet.getLastName(),
                    dateTime,
                    reasonField.getText()
                );
                visit.setDiagnosis(diagnosisArea.getText());
                dataService.getAllVisits().add(visit);
                showAlert("Visit added successfully");
            }
            Navigator.navigateTo("AppointmentsView.fxml");
        } catch (Exception e) {
            showAlert("Error adding/updating visit: " + e.getMessage());
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