package ca.home.novacom.restfull.utils;

public class FilterProduct {

    private String field;
    private Object value;

    public FilterProduct(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}