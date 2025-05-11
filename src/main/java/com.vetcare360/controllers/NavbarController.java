package com.vetcare360.controllers;

import com.vetcare360.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavbarController {
    @FXML private Button homeBtn;
    @FXML private Button findOwnersBtn;
    @FXML private Button vetsBtn;
    @FXML private Button appointmentsBtn;

    @FXML
    public void goHome() {
        Navigator.navigateTo("Home.fxml");
    }

    @FXML
    public void goFindOwners() {
        Navigator.navigateTo("FindOwners.fxml");
    }

    @FXML
    public void goVets() {
        Navigator.navigateTo("VeterinarianList.fxml");
    }

    @FXML
    public void goAppointments() {
        Navigator.navigateTo("AppointmentsView.fxml");
    }
} 