import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sala {
    public static final String JDBC_CONECTION = "jdbc:mysql://localhost:3306/examen_java1p";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static boolean corectAbonatiSignUp = false;
    public static boolean abonatcurent = false;
    public static boolean antrenorcurent = false;
    public static boolean corectAntrenoriSignUp = false;
    public static String emailAbonatCurent = null;
    public static String emailAntrenorCurent = null;
    static List<String> list1 = new ArrayList<String>();
    static List<String> listNewsletter = new ArrayList<String>();
    private static sala SINGLETON;


    private sala() throws FileNotFoundException {
    }

    public static final sala getInstance() throws FileNotFoundException {
        if (SINGLETON == null) {
            SINGLETON = new sala();
        }
        return SINGLETON;
    }


    public static void signUpAbonat(String email, String name, int progress, String password, String confirmation_password) throws SQLException {
        int caz = 0;

        abonati a = new abonati(email, name, progress, password, confirmation_password);

        if (!password.equals(confirmation_password)) {
            caz = 1;
        } else if (password.length() < 8) {
            caz = 2;
        }
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String selectAbonati = "SELECT * FROM abonati";
        ResultSet resultSet = statement.executeQuery(selectAbonati);
        while (resultSet.next()) {
            String eemail = resultSet.getString("email");
            if (email.equals(eemail)) {
                caz = 3;
            }
        }

        switch (caz) {
            case 0 -> {
                abonati b = new abonati(email, name, progress, password, confirmation_password);
                System.out.println("abonatul a fost adaugat cu succes");
                corectAbonatiSignUp = true;
            }
            case 2 -> {
                System.out.println("Parolă prea scurtă!");
                corectAbonatiSignUp = false;
            }
            case 1 -> {
                System.out.println("Parole diferite, nu se poate face adaugarea!");
                corectAbonatiSignUp = false;
            }
            case 3 -> {
                System.out.println("Adresa de email este deja utilizata!");
                corectAbonatiSignUp = false;
            }
        }


    }

    public static void signUpAntrenor(String email, String name, int max_abonati, String password, String confirmation_password) throws SQLException {
        int caz = 0;

        antrenori d = new antrenori(email, name, max_abonati, password, confirmation_password);

        if (!password.equals(confirmation_password)) {
            caz = 1;
        } else if (password.length() < 8) {
            caz = 2;
        }
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String selectAbonati = "SELECT * FROM antrenori";
        ResultSet resultSet = statement.executeQuery(selectAbonati);
        while (resultSet.next()) {
            String eemail = resultSet.getString("email");
            if (email.equals(eemail)) {
                caz = 3;
            }
        }

        switch (caz) {
            case 0 -> {
                abonati c = new abonati(email, name, max_abonati, password, confirmation_password);
                System.out.println("antrenorul a fost adaugat cu succes");
                corectAntrenoriSignUp = true;
            }
            case 2 -> {
                System.out.println("Parolă prea scurtă!");
                corectAntrenoriSignUp = false;
            }
            case 1 -> {
                System.out.println("Parole diferite, nu se poate face adaugarea!");
                corectAntrenoriSignUp = false;
            }
            case 3 -> {
                System.out.println("Adresa de email este deja utilizata!");
                corectAntrenoriSignUp = false;
            }
        }
    }

    public static void loginAbonat(String email, String password) throws SQLException {
        int caz = 0;
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String selectAbonati = "SELECT * FROM abonati";
        ResultSet resultSet = statement.executeQuery(selectAbonati);
        while (resultSet.next()) {
            String eemail = resultSet.getString(2);
            if (!eemail.equals(email)) {
                caz = 1;
            }
        }
        while (resultSet.next()) {
            String parolla = resultSet.getString("parola");
            if (!parolla.equals(password)) {
                caz = 2;
            }
        }
        if (abonatcurent == true) {
            caz = 3;
        }
        switch (caz) {
            case 0 -> {
                abonatcurent = true;
//                UPDATE abonati SET progres=7 where email="alexandra@yahoo.com";
                emailAbonatCurent = email;

                System.out.println("Ati fost conectat!");


            }
            case 2 -> {
                System.out.println("Parola incorecta!");
            }
            case 1 -> {
                System.out.println("Abonatul nu exista!");
            }

            case 3 -> {
                System.out.println("Alt abonat este deja conectat!");
            }
        }


    }

    public static void loginAntrenor(String email, String password) throws SQLException {
        int caz = 0;
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String selectAbonati = "SELECT * FROM antrenori";
        ResultSet resultSet = statement.executeQuery(selectAbonati);
        while (resultSet.next()) {
            String eemail = resultSet.getString("email");
            if (!eemail.equals(email)) {
                caz = 1;
            }
        }
        while (resultSet.next()) {
            String parolla = resultSet.getString("parola");
            if (!parolla.equals(password)) {
                caz = 2;
            }
        }
        if (antrenorcurent == true) {
            caz = 3;
        }
        switch (caz) {
            case 0 -> {
                antrenorcurent = true;
                emailAntrenorCurent = email;

                System.out.println("Ati fost conectat!");


            }
            case 2 -> {
                System.out.println("Parola incorecta!");
            }
            case 1 -> {
                System.out.println("Antrenorul nu exista!”");
            }

            case 3 -> {
                System.out.println("Alt Antrenor este deja conectat!");
            }
        }


    }

    public static void logoutAbonat(String email) throws SQLException {

        if (abonatcurent == false) {
            System.out.println("Abonatul nu era conectat!");
        } else if (abonatcurent == true) {
            System.out.println("Abonatul cu" + email + " a fost deconectat!");
            abonatcurent = false;
            emailAbonatCurent = null;
            //LOGOUT_ABONAT
        }


    }

    public static void logoutAntrenor(String email) throws SQLException {
        if (antrenorcurent == false) {
            System.out.println("Antrenorul nu era conectat!");
        } else if (antrenorcurent == true) {
            System.out.println("Antrenorul cu " + email + " a fost deconectat!");
            antrenorcurent = false;
            //LOGOUT_ANTRENOR
        }
    }

    public static void incrementProgress(int value) throws SQLException {
        if (abonatcurent == false) {
            System.out.println("Nu exista niciun abonat logat!");

        }
        int suma;
        if (abonatcurent == true) {

            Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String selectAbonati = "SELECT progres FROM abonati where email='" + emailAbonatCurent + "';";
            ResultSet resultSet = statement.executeQuery(selectAbonati);
            //System.out.println(resultSet);
            /* while (resultSet.next()) {
            String eemail = resultSet.getString("email");
            if (email.equals(eemail)) {
                caz = 3;
            }
        } */
            int valoare = 0;
            if (resultSet.next()) {

                valoare = resultSet.getInt(1);
            }

            // System.out.println(emailAbonatCurent);
            suma = valoare + value;
            if (suma <= 10) {

                String incrementareAbonati = "UPDATE abonati SET progres=" + suma + " where email='" + emailAbonatCurent + "'";
                statement.executeUpdate(incrementareAbonati);

            } else if (suma > 10) {
                System.out.println("Nu se poate face decrementarea . Progresul total ar fi " + suma);
            }

        }

        //UPDATE abonati SET progres=7 where email="alexandra@yahoo.com";
        //SELECT progres FROM abonati where email='alexandra@yahoo.com';

    }

    public static void decrementProgres(int value) throws SQLException {

        if (abonatcurent == false) {
            System.out.println("Nu exista niciun abonat logat!");

        }
        int suma;
        if (abonatcurent == true) {

            Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String selectAbonati = "SELECT progres FROM abonati where email='" + emailAbonatCurent + "';";
            ResultSet resultSet = statement.executeQuery(selectAbonati);
            //System.out.println(resultSet);
            /* while (resultSet.next()) {
            String eemail = resultSet.getString("email");
            if (email.equals(eemail)) {
                caz = 3;
            }
        } */
            int valoare = 0;
            if (resultSet.next()) {

                valoare = resultSet.getInt(1);
            }

            // System.out.println(emailAbonatCurent);
            suma = valoare - value;
            if (suma > 0) {

                String incrementareAbonati = "UPDATE abonati SET progres=" + suma + " where email='" + emailAbonatCurent + "'";
                statement.executeUpdate(incrementareAbonati);

            } else if (suma < 0) {
                System.out.println("Nu se poate face decrementarea . Progresul total ar fi " + suma);
            }


        }
        //⦁	DECREMENT_PROGRES value
    }

    public static void vizualizareAbonati() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String QUERY = "SELECT * FROM abonati";


        ResultSet resultSet = statement.executeQuery(QUERY + " ORDER BY 5 DESC");

        while (resultSet.next()) {
            System.out.println("id: " + resultSet.getInt("id") + " email: " + resultSet.getString("email") + " nume: " +
                    resultSet.getString("nume") + " progres: " + resultSet.getInt("progres"));
        }


    }

    public static void vizualizareAntrenori() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String QUERY = "SELECT * FROM antrenori";

        ResultSet resultSet = statement.executeQuery(QUERY + " ORDER BY 3 DESC");

        while (resultSet.next()) {
            System.out.println("id: " + resultSet.getInt("id") + " email: " + resultSet.getString("email") + " nume: " +
                    resultSet.getString("nume") + " nr abonati: " + resultSet.getInt("nr_abonati"));
        }

    }

    public static void adaugaAntrenor(String emailAntrenor) throws SQLException {
        //⦁	ADAUGA_ANTRENOR email
        if (abonatcurent == false) {
            System.out.println("abonatul nu este logat!");
        } else if (abonatcurent == true) {
            Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String selectAntrenori = "SELECT * FROM antrenori";
            ResultSet resultSet = statement.executeQuery(selectAntrenori);

            while (resultSet.next()) {
                String eemail = resultSet.getString("email");

                if (emailAntrenor.equals(eemail)) {
                    int nr_abonati = 0;
                    list1.add(emailAntrenor);
                    for (String emailAntrenori : list1) {

                        nr_abonati++;

                    }

                    Statement statement1 = connection.createStatement();
                    String selectnrAbonati = "UPDATE antrenori SET nr_abonati=" + nr_abonati + " where email='" + emailAntrenor + "';";
                   statement1.executeUpdate(selectnrAbonati);


                } else if (abonatcurent == false) {
                    System.out.println("Abonatul nu este logat!");
                } else if (!emailAntrenor.equals(eemail)) {
                    System.out.println("Nu exista antrenorul cu emailul: " + emailAntrenor);
                }
            }


        }
    }
    public static void subscribeAbonat(){
       // SUBSCRIBE_ABONAT

        if(abonatcurent == false){

            System.out.println("Nu există nici un abonat logat!");
        }else {
            boolean newsletterr = false;
                for(String news : listNewsletter){
                    if(news.equals(emailAbonatCurent)){
                        newsletterr = true;
                    }
                }
                if(newsletterr == false){
                    listNewsletter.add(emailAbonatCurent);
                    System.out.println("Abonatul cu adresa de email" + emailAbonatCurent +" a fost abonat la newsletter");
                }
        }


    }
    public static void subscribeAntrenor(){
        //SUBSCRIBE_ANTRENOR
        boolean newsletterr = false;
        if(antrenorcurent == false){
            System.out.println("nu exista nici un antrenor logat!");
        }else {

            for(String news : listNewsletter){
                if(news.equals(emailAntrenorCurent)){
                    newsletterr = true;
                }

            }if(newsletterr == false){
                listNewsletter.add(emailAntrenorCurent);
                System.out.println("Antrenorul cu adresa de email" + emailAntrenorCurent + " a fost abonat la newsletter");
            }

        }


    }

    public static void adaugaNews(String mesajul) {
        System.out.println("A fost adaugata stirea cu mesajul: " + mesajul);

        for (String news : listNewsletter){
        System.out.println("A fost notificat persoana cu adresa de email" + news + "  de stirea cu mesajul:" + mesajul);
        }
        //⦁	ADAUGA_NEWS mesaj
    }

    public static void persistaAbonati() throws SQLException {
        //⦁	PERSISTA_ABONATI
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String QUERY = "SELECT * FROM abonati";
        ResultSet resultSet = statement.executeQuery(QUERY);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String eemail = resultSet.getString("email");
            String nume = resultSet.getString("nume");
            String parola = resultSet.getString("parola");
            int progress = resultSet.getInt("progres");
            Statement statement1 = connection.createStatement();
            String Update = "UPDATE abonati SET id=" + id + ", email='" + eemail + "', nume='" + nume + "', parola='" + parola + "', progres=" + progress + ";";
            statement1.executeUpdate(Update);

        }
        System.out.println("Abonatii au fost salvati in baza de date!");


    }
    public static void persistaAntrenori() throws SQLException {
        //⦁	PERSISTA_ABONATI
        Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String QUERY = "SELECT * FROM antrenori";
        ResultSet resultSet = statement.executeQuery(QUERY);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String eemail = resultSet.getString("email");
            String nume = resultSet.getString("nume");
            String parola = resultSet.getString("parola");
            int nr_abonati = resultSet.getInt("nr_abonati");
            Statement statement1 = connection.createStatement();
            String Update = "UPDATE antrenori SET id=" + id + ", email='" + eemail + "', nume='" + nume + "', parola='" + parola + "', nr_abonati=" + nr_abonati + ";";
            statement1.executeUpdate(Update);

        }
        System.out.println("Antrenorii au fost salvati in baza de date!");


    }

}