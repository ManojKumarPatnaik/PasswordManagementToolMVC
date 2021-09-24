package com.epam.rd.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GroupDTO {
    private int id;

    @Pattern(regexp = "(?=.*[A-Z])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper}!")
    private String name;

    @Size(min = 5, max = 100, message = "Size:{5, 20} required!")
    private String description;

    public GroupDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public GroupDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "GroupDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
