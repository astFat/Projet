package com.vetcare360;

import com.vetcare360.models.DataService;
import com.vetcare360.utils.Navigator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VetCare360App extends Application {
    private DataService dataService;

    @Override
    public void start(Stage primaryStage) {
        try {
            dataService = DataService.getInstance();

            primaryStage.setTitle("VetCare360 - Veterinary Management System");
            primaryStage.setMinWidth(1280);
            primaryStage.setMinHeight(800);
            primaryStage.setMaximized(true);
            Navigator.setMainStage(primaryStage);

            loadSampleData();

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 1280, 800);
            primaryStage.setScene(scene);

            Navigator.navigateTo("Home.fxml");

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSampleData() {
        dataService.addVeterinarian("Ali", "Hassan", "General Medicine", "1234567890", "ali.hassan@vetcare360.com");
        dataService.addVeterinarian("Nour", "Ahmad", "Surgery", "987654321", "nour.ahmad@vetcare360.com");
        dataService.addVeterinarian("Omar", "Youssef", "Dermatology", "5678904321", "omar.youssef@vetcare360.com");
        dataService.addVeterinarian("Sara", "Khaled", "Cardiology", "0987654321", "sara.khaled@vetcare360.com");
        dataService.addVeterinarian("Fatima", "Mahmoud", "Neurology", "5678904321", "fatima.mahmoud@vetcare360.com");

        dataService.addOwner("Layla", "Ali", "1234567890", "layla.ali@gmail.com", "12 Avenue Al-Nahda, Nador");
        dataService.addOwner("Zaid", "Hussein", "0987654321", "zaid.hussein@gmail.com", "34 Rue Tahrir, Nador");
        dataService.addOwner("Rania", "Fahmy", "1234567890", "rania.fahmy@gmail.com", "56 Rue Al-Maghrib Al-Arabi, Nador");
        dataService.addOwner("Youssef", "Adel", "0987654321", "youssef.adel@gmail.com", "78 Boulevard Hassan II, Nador");
        dataService.addOwner("Mariam", "Saad", "1234567890", "mariam.saad@gmail.com", "90 Rue Ibn Khaldoun, Nador");
        dataService.addOwner("Khaled", "Nabil", "0987654321", "khaled.nabil@gmail.com", "123 Avenue Ennour, Nador");
        dataService.addOwner("Huda", "Omar", "1234554321", "huda.omar@gmail.com", "456 Route de Selouane, Nador");
        dataService.addOwner("Tariq", "Salem", "5551303210", "tariq.salem@gmail.com", "789 Boulevard Al-Qods, Nador");


        dataService.addPet("Rex", "Dog", "Labrador", 5, "Layla Ali");
        dataService.addPet("Lulu", "Cat", "Siamese", 3, "Layla Ali");
        dataService.addPet("Badr", "Dog", "Beagle", 4, "Zaid Hussein");
        dataService.addPet("Noor", "Cat", "Persian", 2, "Rania Fahmy");
        dataService.addPet("Kimo", "Dog", "German Shepherd", 6, "Youssef Adel");
        dataService.addPet("Susu", "Cat", "Maine Coon", 4, "Mariam Saad");
        dataService.addPet("Fido", "Dog", "Golden Retriever", 3, "Khaled Nabil");
        dataService.addPet("Tamtam", "Cat", "British Shorthair", 5, "Huda Omar");
        dataService.addPet("Zuzu", "Dog", "Poodle", 2, "Tariq Salem");
        dataService.addPet("SimSim", "Cat", "Ragdoll", 1, "Tariq Salem");

        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dataService.addVisit("Rex", "Ali Hassan", java.time.LocalDateTime.parse("2025-05-10 09:00", fmt), "Annual Checkup");
        dataService.addVisit("Lulu", "Nour Ahmad", java.time.LocalDateTime.parse("2025-05-11 10:30", fmt), "Vaccination");
        dataService.addVisit("Roro", "Omar Youssef", java.time.LocalDateTime.parse("2025-05-12 14:00", fmt), "Skin Rash");
        dataService.addVisit("Nono", "Sara Khaled", java.time.LocalDateTime.parse("2025-05-13 11:15", fmt), "Dental Cleaning");
        dataService.addVisit("Kimo", "Fatima Mahmoud", java.time.LocalDateTime.parse("2025-05-14 16:45", fmt), "Neurology Consultation");
        dataService.addVisit("Susu", "Ali Hassan", java.time.LocalDateTime.parse("2025-05-15 13:00", fmt), "General Checkup");
        dataService.addVisit("Fido", "Nour Ahmad", java.time.LocalDateTime.parse("2025-05-16 09:30", fmt), "Vaccination");
        dataService.addVisit("Tamtam", "Omar Youssef", java.time.LocalDateTime.parse("2025-05-17 15:00", fmt), "Dermatology Exam");
        dataService.addVisit("Zuzu", "Sara Khaled", java.time.LocalDateTime.parse("2025-05-18 10:00", fmt), "Cardiology Exam");
        dataService.addVisit("SimSim", "Fatima Mahmoud", java.time.LocalDateTime.parse("2025-05-19 11:45", fmt), "Routine Checkup");

    }
    public static void main(String[] args) {
        launch(args);
    }
}
