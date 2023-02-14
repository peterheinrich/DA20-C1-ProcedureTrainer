package ch.peterheinrich.flightsim.s5web;

public class DataDTO {
    private final String name;
    private final Object value;

    public DataDTO(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    
}
