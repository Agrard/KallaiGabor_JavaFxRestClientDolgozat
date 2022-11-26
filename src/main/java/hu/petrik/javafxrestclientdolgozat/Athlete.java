package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;
public class Athlete {

    private int id;

    @Expose
    private String name;

    @Expose
    private int age;

    @Expose
    private String home;

    @Expose
    private boolean active;

    public Athlete(int id, String name, int age, String home, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.home = home;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHome() {
        return home;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
