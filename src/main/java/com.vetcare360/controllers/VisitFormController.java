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
            loadEditData();
        }
    }

    private void loadEditData() {
        List<Pet> pets = petComboBox.getItems();
        for (Pet pet : pets) {
            if (pet.getName().equals(editVisit.getPetName())) {
                petComboBox.setValue(pet);
                break;
            }
        }
        
        List<Veterinarian> vets = vetComboBox.getItems();
        for (Veterinarian vet : vets) {
            String vetFullName = vet.getFirstName() + " " + vet.getLastName();
            if (vetFullName.equals(editVisit.getVetName())) {
                vetComboBox.setValue(vet);
                break;
            }
        }
        
        if (editVisit.getDateTime() != null) {
            datePicker.setValue(editVisit.getDateTime().toLocalDate());
            
            int hour = editVisit.getDateTime().getHour();
            int minute = editVisit.getDateTime().getMinute();
            String timeString = String.format("%02d:%02d", hour, minute);
            timeField.setText(timeString);
        }
        
        reasonField.setText(editVisit.getReason());
        diagnosisArea.setText(editVisit.getDiagnosis());
    }

    private void setupComboBoxes() {
        List<Pet> allPets = dataService.getAllPets();
        for (Pet pet : allPets) {
            petComboBox.getItems().add(pet);
        }
        
        petComboBox.setCellFactory(listView -> {
            return new ListCell<Pet>() {
                @Override
                protected void updateItem(Pet pet, boolean empty) {
                    super.updateItem(pet, empty);
                    
                    if (empty || pet == null) {
                        setText("");
                    } else {
                        setText(pet.getName());
                    }
                }
            };
        });
        petComboBox.setButtonCell(petComboBox.getCellFactory().call(null));
        
        List<Veterinarian> allVets = dataService.getAllVeterinarians();
        for (Veterinarian vet : allVets) {
            vetComboBox.getItems().add(vet);
        }
        
        vetComboBox.setCellFactory(listView -> {
            return new ListCell<Veterinarian>() {
                @Override
                protected void updateItem(Veterinarian vet, boolean empty) {
                    super.updateItem(vet, empty);
                    
                    if (empty || vet == null) {
                        setText("");
                    } else {
                        setText(vet.getFirstName() + " " + vet.getLastName());
                    }
                }
            };
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
            String timeText = timeField.getText().trim();
            String[] timeParts = timeText.split(":");
            
            if (timeParts.length != 2) {
                throw new NumberFormatException("Invalid time format");
            }
            
            int hour = Integer.parseInt(timeParts[0].trim());
            int minute = Integer.parseInt(timeParts[1].trim());
            
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                throw new NumberFormatException("Invalid time");
            }
            
            dateTime = LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(hour, minute));
            
        } catch (NumberFormatException e) {
            showAlert("Entre une heure valide HH:MM format (exemple, 14:30)");
            return;
        } catch (Exception e) {
            showAlert("Please enter a valid time in HH:MM format");
            return;
        }

        Pet selectedPet = petComboBox.getValue();
        Veterinarian selectedVet = vetComboBox.getValue();
        String vetFullName = selectedVet.getFirstName() + " " + selectedVet.getLastName();
        String diagnosis = diagnosisArea.getText();
        if (diagnosis == null) {
            diagnosis = "";
        }

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
