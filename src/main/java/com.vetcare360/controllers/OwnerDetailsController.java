package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Owner;
import com.vetcare360.models.Pet;
import com.vetcare360.models.Visit;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class OwnerDetailsController {
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label telephoneLabel;
    @FXML private TableView<Pet> petsTable;
    @FXML private TableColumn<Pet, String> petNameColumn;
    @FXML private TableColumn<Pet, String> petBirthDateColumn;
    @FXML private TableColumn<Pet, String> petTypeColumn;
    @FXML private TableColumn<Pet, String> petVisitsColumn;

    private DataService dataService = DataService.getInstance();
    private Owner currentOwner;
    public static Owner selectedOwner;

    @FXML
    public void initialize() {
        if (selectedOwner != null) {
            showOwner(selectedOwner);
        } else {
            List<Owner> owners = dataService.getAllOwners();
            if (!owners.isEmpty()) {
                showOwner(owners.get(0));
            }
        }
    }

    public void showOwner(Owner owner) {
        this.currentOwner = owner;
        nameLabel.setText(owner.getFullName());
        addressLabel.setText(owner.getAddress());
        telephoneLabel.setText(owner.getPhone());
        setupPetsTable(owner);
    }

    private void setupPetsTable(Owner owner) {
        petNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        petBirthDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("N/A"));
        petTypeColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        petVisitsColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getVisitsString(cellData.getValue())));
        
        List<Pet> allPets = dataService.getAllPets();
        List<Pet> ownerPets = FXCollections.observableArrayList();
        
        for (Pet pet : allPets) {
            if (pet.getOwnerName().equals(owner.getFullName())) {
                ownerPets.add(pet);
            }
        }
        
        ObservableList<Pet> list = FXCollections.observableArrayList(ownerPets);
        petsTable.setItems(list);
    }

    private String getVisitsString(Pet pet) {
        List<Visit> allVisits = dataService.getAllVisits();
        List<Visit> petVisits = FXCollections.observableArrayList();
        
        for (Visit visit : allVisits) {
            if (visit.getPetName().equals(pet.getName())) {
                petVisits.add(visit);
            }
        }
        
        String visitsInfo = "";
        for (int i = 0; i < petVisits.size(); i++) {
            Visit visit = petVisits.get(i);
            visitsInfo += visit.getDateTime().toLocalDate() + ": " + visit.getReason();
            if (i < petVisits.size() - 1) {
                visitsInfo += "; ";
            }
        }
        
        return visitsInfo;
    }

    @FXML
    private void onEditOwnerClick() {
        Navigator.navigateTo("NewOwnerForm.fxml");
    }

    @FXML
    private void onAddPetClick() {
        Navigator.navigateTo("NewPetForm.fxml");
    }
}
