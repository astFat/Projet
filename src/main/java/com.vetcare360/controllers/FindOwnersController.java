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
import java.util.stream.Collectors;

public class FindOwnersController {
    @FXML private TextField lastNameField;
    @FXML private TableView<Owner> ownersTable;
    @FXML private TableColumn<Owner, String> nameColumn;
    @FXML private TableColumn<Owner, String> addressColumn;
    @FXML private TableColumn<Owner, String> cityColumn;
    @FXML private TableColumn<Owner, String> telephoneColumn;
    @FXML private TableColumn<Owner, String> petsColumn;

    private final DataService dataService = DataService.getInstance();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getCityFromAddress(cellData.getValue().getAddress())));
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
        List<Owner> filtered = dataService.getAllOwners().stream()
                .filter(o -> o.getLastName().toLowerCase().contains(lastName))
                .collect(Collectors.toList());
        refreshTable(filtered);
    }

    @FXML
    private void onAddOwnerClick() {
        Navigator.navigateTo("NewOwnerForm.fxml");
    }

    private String getCityFromAddress(String address) {
        if (address == null) return "";
        String[] parts = address.split(",");
        return parts.length > 1 ? parts[parts.length-1].trim() : "";
    }

    private String getPetsString(Owner owner) {
        List<Pet> pets = dataService.getAllPets().stream()
                .filter(p -> p.getOwnerName().equals(owner.getFullName()))
                .collect(Collectors.toList());
        return pets.stream().map(Pet::getName).collect(Collectors.joining(", "));
    }
} 
