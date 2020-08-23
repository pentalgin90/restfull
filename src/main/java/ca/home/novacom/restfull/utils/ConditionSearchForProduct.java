package ca.home.novacom.restfull.utils;
/**
 * Class save object name field for entity and conditionSearch
 * */
public class ConditionSearchForProduct {

    private String field;
    private Object value;

    public ConditionSearchForProduct(String field, Object value) {
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