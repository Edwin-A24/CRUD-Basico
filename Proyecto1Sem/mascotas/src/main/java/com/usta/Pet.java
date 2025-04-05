package com.usta;

public class Pet {
    public String animal;
    public String other;
    public String type;
    public String name;  
    public String age;  
    public String sex;  
    public String color;
    public String hair;
    
    public Pet() {
    }
    public Pet(String animal, String other,  String type, String name, String age, String sex, String color,
            String hair) {
        this.animal = animal;
        this.other = other;
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.color = color;
        this.hair = hair;
    }
    public String getAnimal() {
        return animal;
    }
    public void setAnimal(String animal) {
        this.animal = animal;
    }
    public String getOther() {
        return other;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getHair() {
        return hair;
    }
    public void setHair(String hair) {
        this.hair = hair;
    }

    @Override
    public String toString() {
        return animal + "|" + other 
                + "|" + type + "|" + name + "|" + age + "|" + sex + "|" + color + "|"
                + hair;
    }


}
