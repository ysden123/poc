package com.stulsoft.poc.pgson;

import java.util.Objects;

public class Dto {
    private int id;
    private String name;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dto)) return false;
        Dto dto = (Dto) o;
        return getId() == dto.getId() && Objects.equals(getName(), dto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Dto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
