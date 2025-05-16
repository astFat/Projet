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

            primaryStage.setTitle("VetCare360 - Veterinary clinic");
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
    dataService.addVeterinarian("Ali", "Alami", "General Medicine", "1234567890", "ali.alami@vetcare360.com");
    dataService.addVeterinarian("Nour", "Mohemadi", "Surgery", "987654321", "nour.mohemadi@vetcare360.com");
    dataService.addVeterinarian("Omar", "Yousefy", "Dermatology", "5678904321", "omar.yousefy@vetcare360.com");
    dataService.addVeterinarian("Sara", "Lemrini", "Cardiology", "0987654321", "sara.lemrini@vetcare360.com");
    dataService.addVeterinarian("Fatima", "Mahmoudy", "Neurology", "5678904321", "fatima.mahmoudy@vetcare360.com");

    dataService.addOwner("Layla", "Alami", "0612893746", "layla.alami@gmail.com", "12 Avenue Al-Nahda, Nador");
    dataService.addOwner("Zaid", "Mouradi", "0673920186", "zaid.mouradi@gmail.com", "34 Rue Tahrir, Nador");
    dataService.addOwner("Rania", "Fahmy", "0686329853", "rania.fahmy@gmail.com", "56 Rue Al-Maghrib Al-Arabi, Nador");
    dataService.addOwner("Youssef", "Adely", "0654321789", "youssef.adely@gmail.com", "78 Boulevard Hassan II, Nador");
    dataService.addOwner("Mariam", "Saada", "0612344321", "mariam.saada@gmail.com", "90 Rue Ibn Khaldoun, Nador");
    dataService.addOwner("Khaled", "Nariji", "0769430218", "khaled.nariji@gmail.com", "123 Avenue Ennour, Nador");
    dataService.addOwner("Huda", "Omaaw", "0634554321", "huda.omaaw@gmail.com", "456 Route de Selouane, Nador");
    dataService.addOwner("Tariq", "Salama", "0751303210", "tariq.salama@gmail.com", "789 Boulevard Al-Qods, Nador");


        dataService.addVisit("Rex", "Dr. Amira Mamnouyi", LocalDateTime.now(), "Regular checkup", "Healthy");
        dataService.addVisit("Lulu", "Dr. Omar Lotfy", LocalDateTime.now(), "Coughing", "Mild upper respiratory infection");
        dataService.addVisit("Noono", "Dr. Amira Mamnouyi", LocalDateTime.now(), "Limping", "Sprained leg");
        dataService.addVisit("Bobi", "Dr. Salma Khawa", LocalDateTime.now(), "Lack of appetite", "Digestive issues");
        dataService.addVisit("Kimo", "Dr. Omar Lotfy", LocalDateTime.now(), "Vaccination", "Vaccinated, no issues");
        dataService.addVisit("Susu", "Dr. Salma Khawa", LocalDateTime.now(), "Grooming visit", "Healthy, no issues");
        dataService.addVisit("Fido", "Dr. Amira Mamnouyi", LocalDateTime.now(), "Skin rash", "Allergic dermatitis");
        dataService.addVisit("Tamtam", "Dr. Omar Lotfy", LocalDateTime.now(), "Weight loss", "Early stage diabetes");
        dataService.addVisit("Zuzu", "Dr. Salma Khawa", LocalDateTime.now(), "Ear infection", "Otitis externa");
        dataService.addVisit("SimSim", "Dr. Amira Mamnouyi", LocalDateTime.now(), "Routine check", "Healthy kitten");


        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dataService.addVisit("Rex", "Ali Alami", java.time.LocalDateTime.parse("2025-05-10 09:00", fmt), "Annual Checkup");
        dataService.addVisit("Lulu", "Nour Mohemadi", java.time.LocalDateTime.parse("2025-05-11 10:30", fmt), "Vaccination");
        dataService.addVisit("Roro", "Omar Youssef", java.time.LocalDateTime.parse("2025-05-12 14:00", fmt), "Skin Rash");
        dataService.addVisit("Nono", "Sara Khaled", java.time.LocalDateTime.parse("2025-05-13 11:15", fmt), "Dental Cleaning");
        dataService.addVisit("Kimo", "Fatima Mahmoud", java.time.LocalDateTime.parse("2025-05-14 16:45", fmt), "Neurology Consultation");
        dataService.addVisit("Susu", "Ali Alami", java.time.LocalDateTime.parse("2025-05-15 13:00", fmt), "General Checkup");
        dataService.addVisit("Fido", "Nour Mohemadi", java.time.LocalDateTime.parse("2025-05-16 09:30", fmt), "Vaccination");
        dataService.addVisit("Tamtam", "Omar Youssef", java.time.LocalDateTime.parse("2025-05-17 15:00", fmt), "Dermatology Exam");
        dataService.addVisit("Zuzu", "Sara Khaled", java.time.LocalDateTime.parse("2025-05-18 10:00", fmt), "Cardiology Exam");
        dataService.addVisit("SimSim", "Fatima Mahmoud", java.time.LocalDateTime.parse("2025-05-19 11:45", fmt), "Routine Checkup");

    }
    public static void main(String[] args) {
        launch(args);
    }
}
