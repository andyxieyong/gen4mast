package es.unican.istr.tests;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrador on 18/01/2016.
 */
public class TestMaps {

    public static void main(String[] args) {
        HashMap<String, String> map = new LinkedHashMap<>();

        for (int i=0; i<=20; i++){
            map.put(Integer.toString(i), Integer.toString(i));
        }

        System.out.println(map.keySet());

    }

}
