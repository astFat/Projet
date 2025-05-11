package com.vetcare360.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static DataService instance;
    private List<Owner> owners;
    private List<Pet> pets;
    private List<Veterinarian> veterinarians;
    private List<Visit> visits;
    private int nextOwnerId = 1;
    private int nextPetId = 1;
    private int nextVetId = 1;
    private int nextVisitId = 1;

    private DataService() {
        owners = new ArrayList<>();
        pets = new ArrayList<>();
        veterinarians = new ArrayList<>();
        visits = new ArrayList<>();
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public void addOwner(String firstName, String lastName, String phone, String email, String address) {
        Owner owner = new Owner(nextOwnerId++, firstName, lastName, phone, email, address);
        owners.add(owner);
    }

    public List<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }

    public Owner findOwnerById(int id) {
        return owners.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addPet(String name, String species, String breed, int age, String ownerName) {
        Pet pet = new Pet(nextPetId++, name, species, breed, age, ownerName);
        pets.add(pet);
    }

    public List<Pet> getAllPets() {
        return new ArrayList<>(pets);
    }

    public Pet findPetById(int id) {
        return pets.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addVeterinarian(String firstName, String lastName, String specialization, String phone, String email) {
        Veterinarian vet = new Veterinarian(nextVetId++, firstName, lastName, specialization, phone, email);
        veterinarians.add(vet);
    }

    public List<Veterinarian> getAllVeterinarians() {
        return new ArrayList<>(veterinarians);
    }

    public Veterinarian findVeterinarianById(int id) {
        return veterinarians.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addVisit(String petName, String vetName, LocalDateTime dateTime, String reason) {
        Visit visit = new Visit(nextVisitId++, petName, vetName, dateTime, reason);
        visits.add(visit);
    }

    public List<Visit> getAllVisits() {
        return new ArrayList<>(visits);
    }

    public Visit findVisitById(int id) {
        return visits.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
