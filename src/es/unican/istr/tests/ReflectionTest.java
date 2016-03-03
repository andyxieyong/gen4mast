package es.unican.istr.tests;

import es.unican.istr.gen4mast.system.MastSystem;

/**
 * Created by Administrador on 19/01/2016.
 */
public class ReflectionTest {

    public static void main(String[] args) {

        System.out.println(MastSystem.class.getName());
        System.out.println(MastSystem.class.getCanonicalName());
        System.out.println(MastSystem.class.getSimpleName());
        System.out.println(MastSystem.class.getTypeName());
        System.out.println(MastSystem.class);

    }

}
