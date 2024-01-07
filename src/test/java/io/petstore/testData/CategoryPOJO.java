package io.petstore.testData;

import com.github.javafaker.Faker;

public class CategoryPOJO {

    private int id;
    private String name;

    public CategoryPOJO(){
        Faker faker=new Faker();

        this.id=faker.number().numberBetween(100000000, 2147483647);
        this.name="categoryName"+faker.number().numberBetween(1, 1000);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryPOJO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
