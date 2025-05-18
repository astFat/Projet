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
        Owner newOwner = new Owner(nextOwnerId++, firstName, lastName, phone, email, address);
        owners.add(newOwner);
    }
    
    public List<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }
    
    public Owner findOwnerById(int id) {
        for (Owner owner : owners) {
            if (owner.getId() == id) {
                return owner;
            }
        }
        return null;
    }
    
    public void addPet(String name, String species, String breed, int age, String ownerName) {
        Pet newPet = new Pet(nextPetId++, name, species, breed, age, ownerName);
        pets.add(newPet);
    }
    
    public List<Pet> getAllPets() {
        return new ArrayList<>(pets);
    }
    
    public Pet findPetById(int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        return null;
    }
    
    public void addVeterinarian(String firstName, String lastName, String specialization, String phone, String email) {
        Veterinarian newVet = new Veterinarian(nextVetId++, firstName, lastName, specialization, phone, email);
        veterinarians.add(newVet);
    }
    
    public List<Veterinarian> getAllVeterinarians() {
        return new ArrayList<>(veterinarians);
    }
    
    public Veterinarian findVeterinarianById(int id) {
        for (Veterinarian vet : veterinarians) {
            if (vet.getId() == id) {
                return vet;
            }
        }
        return null;
    }
    
    public void addVisit(String petName, String vetName, LocalDateTime dateTime, String reason) {
        Visit newVisit = new Visit(nextVisitId++, petName, vetName, dateTime, reason);
        visits.add(newVisit);
    }
    
    public List<Visit> getAllVisits() {
        return new ArrayList<>(visits);
    }
    
    public Visit findVisitById(int id) {
        for (Visit visit : visits) {
            if (visit.getId() == id) {
                return visit;
            }
        }
        return null;
    }
    
    public void deleteVisit(Visit visit) {
        visits.remove(visit);
    }
    
    public int getOwnerCount() {
        return owners.size();
    }
    
    public int getPetCount() {
        return pets.size();
    }
    
    public int getVeterinarianCount() {
        return veterinarians.size();
    }
    
    public int getVisitCount() {
        return visits.size();
    }
    
    public void clearAllData() {
        owners.clear();
        pets.clear();
        veterinarians.clear();
        visits.clear();
        
        nextOwnerId = 1;
        nextPetId = 1;
        nextVetId = 1;
        nextVisitId = 1;
    }
}
