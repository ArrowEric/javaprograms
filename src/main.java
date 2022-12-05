import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
    public static final String JDBC_CONECTION = "jdbc:mysql://localhost:3306/examen_java1p";
    public static final String USER = "root";
    public static final String PASSWORD = "";


    public static long countLineBufferedReader(String fileName) {

        long lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;

    }


    public static void main(String[] args) throws FileNotFoundException, SQLException {

        FileInputStream fin = new FileInputStream("src/in.txt");
        InputStreamReader isr = new InputStreamReader(fin);
        BufferedReader bur = new BufferedReader(isr);
        boolean merge = true;


        try (fin; isr; bur) {
            for (int i = 0; i < countLineBufferedReader("src/in.txt"); i++) {
                String s = bur.readLine();
                String[] cmd = s.split(" ");

                switch (cmd[0]) {
                    case "SIGNUP_ABONAT":
                        //⦁	SIGNUP_ABONAT email name progress password confirmation_password

                        String email = cmd[1];
                        String name = cmd[2];
                        int progress = Integer.parseInt(cmd[3]);
                        String password = cmd[4];
                        String confirmation_password = cmd[5];
                        sala.signUpAbonat(email, name, progress, password, confirmation_password);
                        if (sala.corectAbonatiSignUp == true) {
                            Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
                            Statement statement = connection.createStatement();
                            String progres = Integer.toString(progress);
                            /*INSERT INTO abonati VALUES (null, 'stanciu_eric@yahoo.com','Eric','parolamea', 10)*/
                            String SignupAbonat = "INSERT INTO abonati VALUES (null," + " '" + email + "','" + name + "','" + password + "', " + progres + ")";
                            statement.executeUpdate(SignupAbonat);
                            System.out.println("este foarte bine la abonati");
                        }
                        sala.corectAbonatiSignUp = false;
                        break;

                    case "SIGNUP_ANTRENOR":
                        /*⦁	SIGNUP_ANTRENOR email name max_abonati password confirmation_password*/
                        email = cmd[1];
                        name = cmd[2];
                        int max_abonati = Integer.parseInt(cmd[3]);
                        password = cmd[4];
                        confirmation_password = cmd[5];

                        sala.signUpAntrenor(email, name, max_abonati, password, confirmation_password);
                        if (sala.corectAntrenoriSignUp == true) {
                            Connection connection = DriverManager.getConnection(JDBC_CONECTION, USER, PASSWORD);
                            Statement statement = connection.createStatement();
                            String max_abonatii = Integer.toString(max_abonati);
                            /*INSERT INTO antrenori VALUES (null, 'stanciu_eric@yahoo.com','Eric','parolamea', 10)*/
                            String SignupAbonat = "INSERT INTO antrenori VALUES (null," + " '" + email + "','" + name + "','" + password + "', " + max_abonatii + ")";
                            statement.executeUpdate(SignupAbonat);
                            System.out.println("este foarte bine la antrenori");
                        }
                        sala.corectAntrenoriSignUp = false;
                        break;

                    case "LOGIN_ABONAT":
                        //  ⦁	LOGIN_ABONAT email password
                        email = cmd[1];
                        password = cmd[2];
                        sala.loginAbonat(email, password);

                        break;

                    case "LOGIN_ANTRENOR":
                        //  ⦁	LOGIN_ANTRENOR email password
                        email = cmd[1];
                        password = cmd[2];
                        sala.loginAntrenor(email, password);
                        break;

                    case "LOGOUT_ABONAT":
                        // ⦁	LOGOUT_ABONAT email
                        email = cmd[1];
                        sala.logoutAbonat(email);
                        merge = false;

                        break;
                    case "LOGOUT_ANTRENOR":
                        //  ⦁	LOGOUT_ANTRENOR email
                        email = cmd[1];
                        sala.logoutAntrenor(email);
                        break;

                    case "INCREMENT_PROGRES":
                        //⦁	INCREMENT_PROGRES value
                        int value = Integer.parseInt(cmd[1]);
                        sala.incrementProgress(value);
                        break;

                    case "DECREMENT_PROGRES":
                        //⦁	DECREMENT_PROGRES value
                        value = Integer.parseInt(cmd[1]);
                        sala.decrementProgres(value);

                        break;

                    case "VIZUALIZARE_ABONATI":
                        //⦁	VIZUALIZARE_ABONATI
                        sala.vizualizareAbonati();

                        break;
                    case "VIZUALIZARE_ANTRENORI":
                        //⦁	VIZUALIZARE_ANTRENORI
                        sala.vizualizareAntrenori();

                        break;

                    case "ADAUGA_ANTRENOR":
                        //⦁	ADAUGA_ANTRENOR email
                        String emailAndrenor = cmd[1];
                        sala.adaugaAntrenor(emailAndrenor);
                        break;

                    case "SUBSCRIBE_ABONAT":
                        //⦁	SUBSCRIBE_ABONAT
                        sala.subscribeAbonat();
                        break;

                    case "SUBSCRIBE_ANTRENOR":
                        //⦁	SUBSCRIBE_ANTRENOR
                        sala.subscribeAntrenor();
                        break;

                    case "ADAUGA_NEWS":
                        //⦁	ADAUGA_NEWS mesaj


                        String mesaj = cmd[1];
                        sala.adaugaNews(mesaj);

                        break;

                    case "PERSISTA_ABONATI":
                        sala.persistaAbonati();
                        //⦁	PERSISTA_ABONATI

                        break;
                    case "PERSISTA_ANTRENORI":
                        sala.persistaAntrenori();
                        //⦁	PERSISTA_ANTRENORI

                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        FileOutputStream fout = new FileOutputStream("out.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fout);
        BufferedWriter buw = new BufferedWriter(osw);

        try (fout; osw; buw) {

            buw.write("s-a rulat programul fara erori!");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}





