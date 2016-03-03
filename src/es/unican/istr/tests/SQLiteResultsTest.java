package es.unican.istr.tests;

import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorStorer;
import es.unican.istr.rtgen.storers.config.StorerConfig;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class SQLiteResultsTest {

    public static void main(String[] args) {

        StorerConfig config = new StorerConfig();
        SQLiteMastUtilizationGeneratorStorer res = new SQLiteMastUtilizationGeneratorStorer(config);
        System.out.println("Finished Test");

    }

}
