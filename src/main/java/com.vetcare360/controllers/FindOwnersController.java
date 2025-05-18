package com.vetcare360.controllers;

import com.vetcare360.models.DataService;
import com.vetcare360.models.Owner;
import com.vetcare360.models.Pet;
import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class FindOwnersController {
    @FXML private TextField lastNameField;
    @FXML private TableView<Owner> ownersTable;
    @FXML private TableColumn<Owner, String> nameColumn;
    @FXML private TableColumn<Owner, String> addressColumn;
    @FXML private TableColumn<Owner, String> telephoneColumn;
    @FXML private TableColumn<Owner, String> petsColumn;

    private final DataService dataService = DataService.getInstance();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        petsColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getPetsString(cellData.getValue())));
        refreshTable(dataService.getAllOwners());
    }

    private void refreshTable(List<Owner> owners) {
        ObservableList<Owner> list = FXCollections.observableArrayList(owners);
        ownersTable.setItems(list);
    }

    @FXML
    private void onFindOwnerClick() {
        String lastName = lastNameField.getText().trim().toLowerCase();
        List<Owner> allOwners = dataService.getAllOwners();
        List<Owner> filtered = FXCollections.observableArrayList();
        
        for (Owner owner : allOwners) {
            if (owner.getLastName().toLowerCase().contains(lastName)) {
                filtered.add(owner);
            }
        }
        
        refreshTable(filtered);
    }

    @FXML
    private void onAddOwnerClick() {
        Navigator.navigateTo("NewOwnerForm.fxml");
    }

    private String getPetsString(Owner owner) {
        List<Pet> allPets = dataService.getAllPets();
        List<Pet> ownerPets = FXCollections.observableArrayList();
        
        for (Pet pet : allPets) {
            if (pet.getOwnerName().equals(owner.getFullName())) {
                ownerPets.add(pet);
            }
        }
        
        String petNames = "";
        for (int i = 0; i < ownerPets.size(); i++) {
            petNames += ownerPets.get(i).getName();
            if (i < ownerPets.size() - 1) {
                petNames += ", ";
            }
        }
        
        return petNames;
    }
}
