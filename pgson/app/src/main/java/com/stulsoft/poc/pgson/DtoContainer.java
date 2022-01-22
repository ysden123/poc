package com.stulsoft.poc.pgson;

import java.util.Collection;
import java.util.Objects;

public class DtoContainer {
    private String name;
    private Collection<Dto> dtos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Dto> getDtos() {
        return dtos;
    }

    public void setDtos(Collection<Dto> dtos) {
        this.dtos = dtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DtoContainer)) return false;
        DtoContainer that = (DtoContainer) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDtos(), that.getDtos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDtos());
    }

    @Override
    public String toString() {
        return "DtoContainer{" +
                "name='" + name + '\'' +
                ", dtos=" + dtos +
                '}';
    }
}
