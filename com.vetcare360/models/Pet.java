package com.vetcare360.models;

public class Pet {
    private int id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String ownerName;

    public Pet(int id, String name, String species, String breed, int age, String ownerName) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
    }

    // Simple getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
