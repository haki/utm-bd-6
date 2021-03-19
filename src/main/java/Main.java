import service.ConnectionService;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionService.connection("sa", "1234");
        try {
            treiB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void treiB() throws SQLException {
        String selectProfesori = "SELECT * FROM studenti_reusita WHERE Id_Grupa = 1 GROUP BY Id_Disciplina, Id_Grupa, Id_Profesor, Id_Student, Tip_Evaluare, Nota, Data_Evaluare ORDER BY COUNT(Id_Profesor) DESC";

    }

    public static void treiA() throws SQLException {
        String selectGrupe = "SELECT * FROM grupe;";

        Statement statement = ConnectionService.getConn().createStatement();
        ResultSet result = statement.executeQuery(selectGrupe);

        while (result.next()) {
            String idGrupa = result.getString("Id_Grupa");
            String sefGrupa = result.getString("Sef_grupa");

            String selectStudentiReusita = "SELECT * FROM studenti_reusita WHERE Id_Grupa = "+ idGrupa +" GROUP BY Id_Student, Id_Disciplina, Id_Profesor, Id_Grupa, "
                    + "Tip_Evaluare, Nota, Data_Evaluare ORDER BY AVG(Nota) DESC;";

            Statement statement1 = ConnectionService.getConn().createStatement();
            ResultSet result1 = statement1.executeQuery(selectStudentiReusita);

            while (result1.next()) {
                String idStudent = result1.getString("Id_Student");
                String idGrupaSR = result1.getString("Id_Grupa");

                boolean state = true;

                if (!idGrupa.equals(idGrupaSR)) {
                    state = false;
                }

                if (idStudent.equals(sefGrupa)) {
                    state = false;
                }

                if (state) {
                    String updateSefGrupa = "UPDATE grupe SET Sef_grupa=? WHERE Id_Grupa=?";

                    PreparedStatement statement2 = ConnectionService.getConn().prepareStatement(updateSefGrupa);
                    statement2.setInt(1, Integer.parseInt(sefGrupa));
                    statement2.setInt(2, Integer.parseInt(idGrupa));
                    int rowsUpdated = statement2.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("An existing sef was updated successfully!");
                    }
                }
            }
        }
    }
}
