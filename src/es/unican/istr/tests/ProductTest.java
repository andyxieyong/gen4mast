package es.unican.istr.tests;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * Created by JuanCTR on 22/09/2015.
 */
public class ProductTest {

    public static void main(String[] args) {

        Set<String> first = Sets.newHashSet("a", "b");
        Set<String> second = Sets.newHashSet("c", "d");

        Set<List<String>> cartesianProduct = Sets.cartesianProduct(first, second, first);

        System.out.println(cartesianProduct.toString());

    }

}
