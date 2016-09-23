package es.unican.istr.tests;

import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorResultsManager;
import es.unican.istr.rtgen.resultsmanagers.config.StorerConfig;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class SQLiteResultsTest {

    public static void main(String[] args) {

        StorerConfig config = new StorerConfig();
        SQLiteMastUtilizationGeneratorResultsManager res = new SQLiteMastUtilizationGeneratorResultsManager(config);
        System.out.println("Finished Test");

    }

}
