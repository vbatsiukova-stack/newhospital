package com.solvd.hospital.utils;

import com.solvd.hospital.people.Patient;

public class MergeSortUtil {

    public static void mergeSort(Patient[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        Patient[] temp = new Patient[array.length];
        mergeSort(array, temp, 0, array.length - 1);
    }

    private static void mergeSort(Patient[] array, Patient[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = (left + right) / 2;

        mergeSort(array, temp, left, middle);
        mergeSort(array, temp, middle + 1, right);

        merge(array, temp, left, middle, right);
    }

    private static void merge(Patient[] array, Patient[] temp,
                              int left, int middle, int right) {

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (comparePatients(array[i], array[j]) <= 0) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (int index = left; index <= right; index++) {
            array[index] = temp[index];
        }
    }

    private static int comparePatients(Patient p1, Patient p2) {
        int lastNameCompare = p1.getLastName().compareToIgnoreCase(p2.getLastName());
        if (lastNameCompare != 0) {
            return lastNameCompare;
        }
        return p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
    }
}
