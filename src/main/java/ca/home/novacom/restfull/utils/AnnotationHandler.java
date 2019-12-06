package ca.home.novacom.restfull.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationHandler {

    public static List<FilterProduct> getFields(Class<?> entity, String condition) {
        Field[] declaredField = entity.getDeclaredFields();
        List<FilterProduct> filterProducts = new ArrayList<>();
        for (Field a : declaredField) {
            if (a.isAnnotationPresent(ConditionalForFilter.class)) {
                filterProducts.add(new FilterProduct(a.getName(), condition));
            }
        }
        return filterProducts;
    }
}
