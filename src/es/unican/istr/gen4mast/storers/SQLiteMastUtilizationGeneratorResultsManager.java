package es.unican.istr.gen4mast.storers;

import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.gen4mast.tool.config.MastToolConfigurableOptions;
import es.unican.istr.rtgen.resultsmanagers.UtilizationGeneratorResultsManager;
import es.unican.istr.rtgen.resultsmanagers.config.StorerConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.system.config.LinearSystemConfigurableOptions;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.system.config.deadline.DeadlineOptions;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class SQLiteMastUtilizationGeneratorResultsManager extends UtilizationGeneratorResultsManager {

    public static final String SERIES_RESULTS_TABLE_NAME = "SERIES_RESULTS";
    public static final String SYSTEM_RESULTS_TABLE_NAME = "SYSTEM_RESULTS";

    private Connection con = null;
    private HashMap<String, String> seriesResultsColumns = null;
    private HashMap<String, String> systemResultsColumns = null;


    //////////////////
    // Constructors //
    //////////////////

    public SQLiteMastUtilizationGeneratorResultsManager(StorerConfig config) {
        super(config);
        try {
            System.out.printf("Using SQLite database to store results : ");
            System.out.printf("(%s)\n", config.getResultsFileLocation());

            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(String.format("jdbc:sqlite:%s", config.getResultsFileLocation()));

            // Disable journal (it looks like journal doesnt work in the lustre filesystem)
            String pragma = "PRAGMA journal_mode = OFF";
            Statement stmt = con.createStatement();
            stmt.execute(pragma);
            stmt.close();

            // Establish Series Results Table (creates if it doesn't exist)
            establishSeriesColumns();
            createSeriesTable();

            // Establish System Results Table (creates if it doesn't exist)
            establishSystemColumns();
            createsSystemTable();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /////////////////////
    // Storage methods //
    /////////////////////

    @Override
    public synchronized void storeSystemResults(LinearSystem sys) {

        // Read execution characteristics
        SystemConfig sysConfig = sys.getSystemConfig();
        MastToolConfig m = (MastToolConfig) sys.getToolConfig();

        PreparedStatement p = null;

        // Prepare columns
        ArrayList<String> cols = new ArrayList<>(systemResultsColumns.keySet());
        ArrayList<String> cols2 = new ArrayList<>();
        for (int i = 1; i <= cols.size(); i++) {
            cols2.add("?");
        }

        String query = String.format("INSERT INTO %s (%s) VALUES(%s)",
                SYSTEM_RESULTS_TABLE_NAME, String.join(",", cols), String.join(",", cols2));

        try {
            while (true) {
                try {
                    p = con.prepareStatement(query);
                    break;
                } catch (SQLException e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            int i = 1;

            // Store system characteristics
            p.setInt(i++, sysConfig.getSeed());
            p.setInt(i++, sysConfig.getnProcs());
            p.setInt(i++, sysConfig.getnFlows());
            p.setInt(i++, sysConfig.getnTasks());
            p.setBoolean(i++, sysConfig.getRandomLength());
            p.setFloat(i++, sysConfig.getSingleFlows());
            p.setString(i++, sysConfig.getSchedPolicy());

            p.setString(i++, sysConfig.getPeriod().getDistribution().name());
            p.setFloat(i++, sysConfig.getPeriod().getBase());
            p.setFloat(i++, sysConfig.getPeriod().getRatio());

            if (sysConfig.getDeadline().getValue() == DeadlineOptions.K) {
                p.setString(i++, sysConfig.getDeadline().getValueK().toString());
            } else {
                p.setString(i++, sysConfig.getDeadline().getValue().name());
            }

            p.setString(i++, sysConfig.getLocalization().name());

            p.setInt(i++, sysConfig.getUtilization().getUtilization());
            p.setFloat(i++, sysConfig.getUtilization().getBcetFactor());
            p.setString(i++, sysConfig.getUtilization().getWcetMethod().name());
            p.setString(i++, sysConfig.getUtilization().getBalancing().name());

            // Store MAST options
            p.setString(i++, m.getName());
            p.setString(i++, "");
            p.setString(i++, "");
            p.setString(i++, m.getAnalysis().name());
            p.setString(i++, m.getAssignment().name());
            p.setBoolean(i++, m.getSync());

            p.setString(i++, m.getHospaConfig().getInit().name());
            p.setFloat(i++, m.getHospaConfig().getKa());
            p.setFloat(i++, m.getHospaConfig().getKr());
            p.setInt(i++, m.getHospaConfig().getIterations());
            p.setInt(i++, m.getHospaConfig().getOverIterations());

            p.setFloat(i++, m.getStopFactor());

            p.setBoolean(i++, m.getGsd());
            p.setFloat(i++, m.getDsFactor());

            p.setBoolean(i++, m.getCalculateSlack());
            p.setBoolean(i++, m.getJitterAvoidance());
            p.setLong(i++, m.getAnalysisTimeout());

            // Add Results
            p.setInt(i++, new Double(sys.getSystemUtilization()*100.0).intValue());
            p.setDouble(i++, sys.getSystemAvgWCRT());
            p.setLong(i++, sys.getToolTimeElapsed());


            // Save results
            while (true) {
                try {
                    p.executeUpdate();
                    break;
                } catch (SQLException e) {
                    System.out.println("Busy database, waiting....");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            p.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void storeSeriesResults(
            SystemConfig sysConfig, ToolConfig toolConfig,
            Integer utilizationStart, Integer utilizationTop, Integer utilizationStep,
            Integer maximumSchedulableUtilization, Long timeElapsed) {

        MastToolConfig m = (MastToolConfig) toolConfig;

        PreparedStatement p = null;

        ArrayList<String> cols = new ArrayList<>(seriesResultsColumns.keySet());
        ArrayList<String> cols2 = new ArrayList<>();
        for (int i = 1; i <= cols.size(); i++) {
            cols2.add("?");
        }

        String query = String.format("INSERT INTO %s (%s) VALUES(%s)",
                SERIES_RESULTS_TABLE_NAME, String.join(",", cols), String.join(",", cols2));

        try {
            while (true) {
                try {
                    p = con.prepareStatement(query);
                    break;
                } catch (SQLException e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            int i = 1;

            // Store system characteristics
            p.setInt(i++, sysConfig.getSeed());
            p.setInt(i++, sysConfig.getnProcs());
            p.setInt(i++, sysConfig.getnFlows());
            p.setInt(i++, sysConfig.getnTasks());
            p.setBoolean(i++, sysConfig.getRandomLength());
            p.setFloat(i++, sysConfig.getSingleFlows());
            p.setString(i++, sysConfig.getSchedPolicy());

            p.setString(i++, sysConfig.getPeriod().getDistribution().name());
            p.setFloat(i++, sysConfig.getPeriod().getBase());
            p.setFloat(i++, sysConfig.getPeriod().getRatio());

            if (sysConfig.getDeadline().getValue() == DeadlineOptions.K) {
                p.setString(i++, sysConfig.getDeadline().getValueK().toString());
            } else {
                p.setString(i++, sysConfig.getDeadline().getValue().name());
            }

            p.setString(i++, sysConfig.getLocalization().name());

            p.setInt(i++, sysConfig.getUtilization().getUtilization());
            p.setFloat(i++, sysConfig.getUtilization().getBcetFactor());
            p.setString(i++, sysConfig.getUtilization().getWcetMethod().name());
            p.setString(i++, sysConfig.getUtilization().getBalancing().name());

            // Store MAST options
            p.setString(i++, m.getName());
            p.setString(i++, "");
            p.setString(i++, "");
            p.setString(i++, m.getAnalysis().name());
            p.setString(i++, m.getAssignment().name());
            p.setBoolean(i++, m.getSync());

            p.setString(i++, m.getHospaConfig().getInit().name());
            p.setFloat(i++, m.getHospaConfig().getKa());
            p.setFloat(i++, m.getHospaConfig().getKr());
            p.setInt(i++, m.getHospaConfig().getIterations());
            p.setInt(i++, m.getHospaConfig().getOverIterations());

            p.setFloat(i++, m.getStopFactor());

            p.setBoolean(i++, m.getGsd());
            p.setFloat(i++, m.getDsFactor());

            p.setBoolean(i++, m.getCalculateSlack());
            p.setBoolean(i++, m.getJitterAvoidance());
            p.setLong(i++, m.getAnalysisTimeout());

            // Store utilization range
            p.setInt(i++, utilizationStart);
            p.setInt(i++, utilizationTop);
            p.setInt(i++, utilizationStep);

            // Store Maximum Schedulable Utilization
            p.setInt(i++, maximumSchedulableUtilization);

            // Store Series Time Elapsed
            p.setLong(i++, timeElapsed);

            // Save results
            while (true) {
                try {
                    p.executeUpdate();
                    break;
                } catch (SQLException e) {
                    System.out.println("Busy database, waiting....");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            p.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////
    // Series Results Table Creation Utilities //
    /////////////////////////////////////////////

    private void establishSeriesColumns() {

        seriesResultsColumns = new LinkedHashMap<>(); // guarantees keys in insertion order

        for (LinearSystemConfigurableOptions o : LinearSystemConfigurableOptions.values()) {
            switch (o) {
                case SEED:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_PROCESSORS:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_FLOWS:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_TASKS:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case RANDOM_LENGTH:
                    seriesResultsColumns.put(o.name(), "BIT");
                    break;
                case SINGLE_FLOWS:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case SCHED_POLICY:
                    seriesResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case PERIOD_DISTRIBUTION:
                    seriesResultsColumns.put(o.name(), "VARCHAR(11)");
                    break;
                case PERIOD_BASE:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case PERIOD_RATIO:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case DEADLINE:
                    seriesResultsColumns.put(o.name(), "VARCHAR(6)");
                    break;
                case TASK_LOCALIZATION:
                    seriesResultsColumns.put(o.name(), "VARCHAR(17)");
                    break;
                case UTILIZATION:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case UTILIZATION_BCET_FACTOR:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case UTILIZATION_WCET_METHOD:
                    seriesResultsColumns.put(o.name(), "VARCHAR(8)");
                    break;
                case UTILIZATION_BALANCING:
                    seriesResultsColumns.put(o.name(), "VARCHAR(12)");
                    break;
            }
        }

        for (MastToolConfigurableOptions o : MastToolConfigurableOptions.values()) {
            switch (o) {
                case NAME:
                    seriesResultsColumns.put(o.name(), "VARCHAR(22)");
                    break;
                case WORK_PATH:
                    seriesResultsColumns.put(o.name(), "VARCHAR(1)");
                    break; // for now not stored
                case MAST_PATH:
                    seriesResultsColumns.put(o.name(), "VARCHAR(1)");
                    break; // for now not stored
                case ANALYSIS_TOOL:
                    seriesResultsColumns.put(o.name(), "VARCHAR(14)");
                    break;
                case ASSIGNMENT_TOOL:
                    seriesResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case SYNC:
                    seriesResultsColumns.put(o.name(), "BIT");
                    break;
                case HOSPA_INIT:
                    seriesResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case HOSPA_KA:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case HOSPA_KR:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case HOSPA_ITERATIONS:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case HOSPA_OVERITERATIONS:
                    seriesResultsColumns.put(o.name(), "INTEGER");
                    break;
                case ANALYSIS_STOP_FACTOR:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case LC_EDF_GSD:
                    seriesResultsColumns.put(o.name(), "BIT");
                    break;
                case LC_EDF_DS_FACTOR:
                    seriesResultsColumns.put(o.name(), "REAL");
                    break;
                case CALCULATE_SLACK:
                    seriesResultsColumns.put(o.name(), "BIT");
                    break;
                case JITTER_AVOIDANCE:
                    seriesResultsColumns.put(o.name(), "BIT");
                    break;
                case ANALYSIS_TIMEOUT:
                    seriesResultsColumns.put(o.name(), "BIGINT");
                    break;
            }
        }

        // Results columns
        seriesResultsColumns.put("UTILIZATION_START", "INTEGER");
        seriesResultsColumns.put("UTILIZATION_TOP", "INTEGER");
        seriesResultsColumns.put("UTILIZATION_STEP", "INTEGER");
        seriesResultsColumns.put("MSU", "INTEGER");
        seriesResultsColumns.put("TIME", "BIGINT");
    }

    public synchronized void createSeriesTable() {
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet tables = meta.getTables(null, null, SERIES_RESULTS_TABLE_NAME, null);
            if (!tables.next()) { // Table doesn't exist, create table

                // Creates statement string with columns and type
                Statement stmt = con.createStatement();
                ArrayList<String> stmtStrList = new ArrayList<>();
                stmtStrList.add(String.format("CREATE TABLE %s (", SERIES_RESULTS_TABLE_NAME));

                Iterator<String> iter = seriesResultsColumns.keySet().iterator();
                String k = iter.next();
                while (true) {
                    stmtStrList.add(String.format("%s %s%s", k, seriesResultsColumns.get(k), iter.hasNext() ? "," : ")"));
                    if (iter.hasNext()) {
                        k = iter.next();
                    } else {
                        break;
                    }
                }

                // Execute statement
                stmt.executeUpdate(String.join("", stmtStrList));
                stmt.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////
    // Example Results Table Creation Utilities //
    //////////////////////////////////////////////

    private void establishSystemColumns() {

        systemResultsColumns = new LinkedHashMap<>();

        for (LinearSystemConfigurableOptions o : LinearSystemConfigurableOptions.values()) {
            switch (o) {
                case SEED:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_PROCESSORS:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_FLOWS:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case N_TASKS:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case RANDOM_LENGTH:
                    systemResultsColumns.put(o.name(), "BIT");
                    break;
                case SINGLE_FLOWS:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case SCHED_POLICY:
                    systemResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case PERIOD_DISTRIBUTION:
                    systemResultsColumns.put(o.name(), "VARCHAR(11)");
                    break;
                case PERIOD_BASE:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case PERIOD_RATIO:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case DEADLINE:
                    systemResultsColumns.put(o.name(), "VARCHAR(6)");
                    break;
                case TASK_LOCALIZATION:
                    systemResultsColumns.put(o.name(), "VARCHAR(17)");
                    break;
                case UTILIZATION:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case UTILIZATION_BCET_FACTOR:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case UTILIZATION_WCET_METHOD:
                    systemResultsColumns.put(o.name(), "VARCHAR(8)");
                    break;
                case UTILIZATION_BALANCING:
                    systemResultsColumns.put(o.name(), "VARCHAR(12)");
                    break;
            }
        }

        for (MastToolConfigurableOptions o : MastToolConfigurableOptions.values()) {
            switch (o) {
                case NAME:
                    systemResultsColumns.put(o.name(), "VARCHAR(22)");
                    break;
                case WORK_PATH:
                    systemResultsColumns.put(o.name(), "VARCHAR(1)");
                    break; // for now not stored
                case MAST_PATH:
                    systemResultsColumns.put(o.name(), "VARCHAR(1)");
                    break; // for now not stored
                case ANALYSIS_TOOL:
                    systemResultsColumns.put(o.name(), "VARCHAR(14)");
                    break;
                case ASSIGNMENT_TOOL:
                    systemResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case SYNC:
                    systemResultsColumns.put(o.name(), "BIT");
                    break;
                case HOSPA_INIT:
                    systemResultsColumns.put(o.name(), "VARCHAR(5)");
                    break;
                case HOSPA_KA:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case HOSPA_KR:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case HOSPA_ITERATIONS:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case HOSPA_OVERITERATIONS:
                    systemResultsColumns.put(o.name(), "INTEGER");
                    break;
                case ANALYSIS_STOP_FACTOR:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case LC_EDF_GSD:
                    systemResultsColumns.put(o.name(), "BIT");
                    break;
                case LC_EDF_DS_FACTOR:
                    systemResultsColumns.put(o.name(), "REAL");
                    break;
                case CALCULATE_SLACK:
                    systemResultsColumns.put(o.name(), "BIT");
                    break;
                case JITTER_AVOIDANCE:
                    systemResultsColumns.put(o.name(), "BIT");
                    break;
                case ANALYSIS_TIMEOUT:
                    systemResultsColumns.put(o.name(), "BIGINT");
                    break;
            }
        }

        // Results columns
        systemResultsColumns.put("UTILIZATION_REAL", "INTEGER"); //calculated by internal method in LinearSystem
        systemResultsColumns.put("AVG_R", "REAL");
        systemResultsColumns.put("TIME", "BIGINT");
    }

    public synchronized void createsSystemTable() {
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet tables = meta.getTables(null, null, SYSTEM_RESULTS_TABLE_NAME, null);
            if (!tables.next()) { // Table doesn't exist, create table

                // Creates statement string with columns and type
                Statement stmt = con.createStatement();
                ArrayList<String> stmtStrList = new ArrayList<>();
                stmtStrList.add(String.format("CREATE TABLE %s (", SYSTEM_RESULTS_TABLE_NAME));

                Iterator<String> iter = systemResultsColumns.keySet().iterator();
                String k = iter.next();
                while (true) {
                    stmtStrList.add(String.format("%s %s%s", k, systemResultsColumns.get(k), iter.hasNext() ? "," : ")"));
                    if (iter.hasNext()) {
                        k = iter.next();
                    } else {
                        break;
                    }
                }

                // Execute statement
                stmt.executeUpdate(String.join("", stmtStrList));
                stmt.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
