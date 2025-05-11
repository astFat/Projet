package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @FXML private Label ownerCountLabel;
    @FXML private Label petCountLabel;
    @FXML private Label vetCountLabel;
    
    private DataService dataService;
    
    @FXML
    public void initialize() {
        dataService = DataService.getInstance();
        updateCounts();
    }
    
    private void updateCounts() {
        ownerCountLabel.setText(String.valueOf(dataService.getAllOwners().size()));
        petCountLabel.setText(String.valueOf(dataService.getAllPets().size()));
        vetCountLabel.setText(String.valueOf(dataService.getAllVeterinarians().size()));
    }
    
    @FXML
    private void goHome() { Navigator.navigateTo("Home.fxml"); }
    
    @FXML
    private void goFindOwners() { Navigator.navigateTo("FindOwners.fxml"); }
    
    @FXML
    private void goVets() { Navigator.navigateTo("Veterinarians.fxml"); }
    
    @FXML
    private void goAppointments() { Navigator.navigateTo("AppointmentsView.fxml"); }
    
    @FXML
    private void onPetsClick() {
        Navigator.navigateTo("PetList.fxml");
    }
    
    @FXML
    private void onOwnersClick() {
        Navigator.navigateTo("OwnerList.fxml");
    }
    
    @FXML
    private void onVetsClick() {
        Navigator.navigateTo("VeterinarianList.fxml");
    }
    
    @FXML
    private void onAddPetClick() {
        Navigator.navigateTo("NewPetForm.fxml");
    }
    
    @FXML
    private void onAddOwnerClick() {
        Navigator.navigateTo("NewOwnerForm.fxml");
    }
    
    @FXML
    private void onAddVetClick() {
        Navigator.navigateTo("NewVeterinarianForm.fxml");
    }
    
    @FXML
    private void onAddVisitClick() {
        Navigator.navigateTo("AddVisitForm.fxml");
    }
}
