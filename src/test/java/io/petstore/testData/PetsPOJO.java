package io.petstore.testData;

import com.github.javafaker.Faker;

import java.util.Collections;
import java.util.List;

public class PetsPOJO {
    private int id;
    private CategoryPOJO category;
    private String name;
    private List<String> photoUrls;
    private List<TagsPOJO> tags;
    private String status;

    public PetsPOJO() {
        Faker faker = new Faker();
        this.id = faker.number().numberBetween(100000000, 2147483647);
        this.category = new CategoryPOJO();
        this.name = faker.animal().name();
        this.photoUrls = Collections.singletonList(faker.internet().image());
        this.tags = Collections.singletonList(new TagsPOJO());
        String[] statuses = {"available", "pending", "sold"};
        int index = faker.number().numberBetween(0, statuses.length);
        this.status = statuses[index];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryPOJO getCategory() {
        return category;
    }

    public void setCategory(CategoryPOJO category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<TagsPOJO> getTags() {
        return tags;
    }

    public void setTags(List<TagsPOJO> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "petsPOJO{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }

    public void setId(Integer o) {
        this.id=o;
    }
}
