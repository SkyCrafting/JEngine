/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.world.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author SkyCrafting_
 */
public class ArrayUtil {

    public static <T extends Object> List<T[]> splitArray(T[] array, int max) {

        int x = array.length / max;
        int r = (array.length % max); // remainder

        int lower = 0;
        int upper = 0;

        List<T[]> list = new ArrayList<T[]>();

        int i = 0;

        for (i = 0; i < x; i++) {

            upper += max;

            list.add(Arrays.copyOfRange(array, lower, upper));

            lower = upper;
        }

        if (r > 0) {

            list.add(Arrays.copyOfRange(array, lower, (lower + r)));

        }

        return list;
    }
}
