package com.group4.patientdoctorconsultation.utilities;

import java.util.List;

public class ArrayUtils {
    public static double calculateAverage(List<Integer> values) {
        Integer sum = 0;
        if(!values.isEmpty()) {
            for (Integer value : values) {
                sum += value;
            }
            return sum.doubleValue() / values.size();
        }
        return sum;
    }
}
