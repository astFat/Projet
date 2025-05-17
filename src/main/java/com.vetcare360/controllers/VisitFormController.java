package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Pet;
import com.vetcare360.models.Veterinarian;
import com.vetcare360.models.Visit;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.util.List;

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
            LocalDateTime dateTime;
            try {
                String[] timeParts = timeField.getText().trim().split(":");
                if (timeParts.length != 2) {
                    throw new NumberFormatException("Invalid time format");
                }
                int hour = Integer.parseInt(timeParts[0].trim());
                int minute = Integer.parseInt(timeParts[1].trim());
                
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    throw new NumberFormatException("Invalid time");
                }
                
                dateTime = LocalDateTime.of(
                    datePicker.getValue(),
                    java.time.LocalTime.of(hour, minute)
                );
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                showAlert("Entre une heure valide HH:MM format (exemple, 14:30)");
                return;
            }

            Pet selectedPet = petComboBox.getValue();
            Veterinarian selectedVet = vetComboBox.getValue();
            String vetFullName = selectedVet.getFirstName() + " " + selectedVet.getLastName();
            String diagnosis = diagnosisArea.getText() != null ? diagnosisArea.getText() : "";

            if (editVisit != null) {
                editVisit.setPetName(selectedPet.getName());
                editVisit.setVetName(vetFullName);
                editVisit.setDateTime(dateTime);
                editVisit.setReason(reasonField.getText().trim());
                editVisit.setDiagnosis(diagnosis);
                showAlert("Visit updated successfully");
                editVisit = null;
            } else {
                dataService.addVisit(
                    selectedPet.getName(),
                    vetFullName,
                    dateTime,
                    reasonField.getText().trim()
                );
                List<Visit> allVisits = dataService.getAllVisits();
                if (!allVisits.isEmpty()) {
                    Visit lastVisit = allVisits.get(allVisits.size() - 1);
                    lastVisit.setDiagnosis(diagnosis);
                }
                showAlert("Visit added successfully");
            }
            
            Navigator.navigateTo("AppointmentsView.fxml");


    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
