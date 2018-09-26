package com.google.sample.cloudvision;

/**
 * Created by hp on 3/22/2018.
 */

public class FilterImage {
    String labels;
    Boolean isCorrect;

    FilterImage(String a , Boolean b)
    {
        labels = a;
        isCorrect = b;
    }

    public String getLabels() {
        return labels;
    }
}
