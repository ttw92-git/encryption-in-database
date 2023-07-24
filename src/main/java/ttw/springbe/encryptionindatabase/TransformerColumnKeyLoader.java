package ttw.springbe.encryptionindatabase;

import org.hibernate.annotations.ColumnTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ttw.springbe.encryptionindatabase.entities.TableA;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

@Component(value = "transformerColumnKeyLoader")
public class TransformerColumnKeyLoader {

    public static final String KEY_ANNOTATION_PROPERTY = "${password}";

    @Value(value = "${password}")
    private String key;

    @PostConstruct
    public void postConstruct() {
        setKey(TableA.class);
    }

    private void setKey(Class<?> clazz) {
        String columnName = "";
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field temp : fields) {

                Field field = clazz.getDeclaredField(temp.getName());

                if (field.getDeclaredAnnotation(ColumnTransformer.class) != null) {
                    columnName = temp.getName();
                    ColumnTransformer columnTransformer = field.getDeclaredAnnotation(ColumnTransformer.class);
                    updateAnnotationValue(columnTransformer, "read");
                    updateAnnotationValue(columnTransformer, "write");
                }
            }
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(
                    String.format("Encryption key cannot be loaded into %s,%s", clazz.getName(), columnName));
        }
    }

    @SuppressWarnings("unchecked")
    private void updateAnnotationValue(Annotation annotation, String annotationProperty) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field merberValuesField;
        try {
            merberValuesField = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        merberValuesField.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) merberValuesField.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(annotationProperty);
        if (oldValue == null || oldValue.getClass() != String.class) {
            throw new IllegalArgumentException(String.format(
                    "Annotation value should be String. Current value is of type: %s", oldValue.getClass().getName()));
        }

        String oldValueString = oldValue.toString();
        if (oldValueString.contains(TransformerColumnKeyLoader.KEY_ANNOTATION_PROPERTY)) {
            String newValueString = oldValueString.replace(TransformerColumnKeyLoader.KEY_ANNOTATION_PROPERTY, key);
            memberValues.put(annotationProperty, newValueString);
        }
    }
}