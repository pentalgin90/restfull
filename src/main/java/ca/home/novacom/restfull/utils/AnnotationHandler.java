package ca.home.novacom.restfull.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationHandler {
    /**
     * Method get type class and condition search
     * return list ready filters with condition search
     * and names field class
     * list field get of reflection with annotation @
     *
     * @param entity     : type class entity
     * @param condition: type string
     */
    public static List<ConditionSearchForProduct> getFields(Class<?> entity, String condition) {
        Field[] declaredField = entity.getDeclaredFields();
        List<ConditionSearchForProduct> conditionSearchForProducts = new ArrayList<>();
        for (Field a : declaredField) {
            if (a.isAnnotationPresent(ConditionalForSearch.class)) {
                conditionSearchForProducts.add(new ConditionSearchForProduct(a.getName(), condition));
            }
        }
        return conditionSearchForProducts;
    }
}
