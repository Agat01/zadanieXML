
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import encje.User;

@WebServlet("/dane")
public class DaneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
		String name="";
		String surname="";
		String login="";
		String hashSurname="";

		List<User> users = new ArrayList<>();
        
		try {
			
			Driver sterownik = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zad?useUnicode=true&characterEncoding=utf-8", "root", "zadanie");

			// wyświetlanie wszystkich użytkowników z bazy danych
			String sql = "SELECT name, surname, login FROM user";
			Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					name=rs.getString(1);
					surname=rs.getString(2);
					login=rs.getString(3);
					// haszowanie nazwiska
					hashSurname=surname+"_"+getMd5(name);
					users.add(new User(name, hashSurname, login));
				}
		// konwertowanie listy użytkowników na format JSON i przesłanie do klienta
		Gson gson = new Gson();
        String json = gson.toJson(users);
        
        out.print(json);
        out.flush();
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public static String getMd5(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
