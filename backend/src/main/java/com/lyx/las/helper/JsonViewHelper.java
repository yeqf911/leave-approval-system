package com.lyx.las.helper;

import org.springframework.validation.ObjectError;

import java.util.List;

public class JsonViewHelper {
    public interface SimpleView {
    }

    public interface DetailView {
    }

    public static String getErrorMessages(List<ObjectError> strings) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (ObjectError item : strings) {
            if (first)
                first = false;
            else
                sb.append(",");
            sb.append(item.getDefaultMessage());
        }
        sb.append("]");
        return sb.toString();
    }
}
