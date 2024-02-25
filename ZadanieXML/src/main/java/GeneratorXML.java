import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratorXML {
	
    public static void main(String[] args) {
    	// zmień lokalizację plików names.txt i sunames.txt
        generateXMLData(500, "C:\\Users\\User\\Desktop\\zadanie\\names.txt", "C:\\Users\\User\\Desktop\\zadanie\\surnames.txt");
    }

    public static void generateXMLData(int numUsers, String namesFile, String surnamesFile) {
        // Funkcja do generowania pliku .xml z losowymi imionami, nazwiskami i loginami
    	Element rootElement = new Element("users");
        Document document = new Document(rootElement);

        List<String> names=new ArrayList<String>();
        List<String> surnames=new ArrayList<String>();
        List<String> logins=new ArrayList<String>();

        try {
        	names = Files.readAllLines(Paths.get(namesFile), StandardCharsets.UTF_8);
        	surnames = Files.readAllLines(Paths.get(surnamesFile), StandardCharsets.UTF_8);

            Driver sterownik = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zad?useUnicode=true&characterEncoding=utf-8", "root", "zadanie");
			Statement stmt = conn.createStatement();

			String login;
			String sql="select login from user";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				login=rs.getString(1);
				logins.add(login);
			}
				rs.close();
				stmt.close();
				
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (SQLException e) {
			e.printStackTrace();
		}

        Random random = new Random();

        for (int i = 1; i <= numUsers; i++) {
            Element userElement = new Element("user");

            // Losowe imię
            String randomName = names.get(random.nextInt(names.size()));
            Element nameElement = new Element("name");
            nameElement.setText(randomName);

            // Losowe nazwisko
            String randomSurname = surnames.get(random.nextInt(surnames.size()));
            Element surnameElement = new Element("surname");
            surnameElement.setText(randomSurname);

            // Losowy login
            String randomLogin="";
            Element loginElement;
            boolean isUniqueLogin;
            do {
            randomLogin = generateRandomLogin(randomName, randomSurname);
            loginElement = new Element("login");
            loginElement.setText(randomLogin);
            
            // sprawdzenie czy stworzony login jest unikalny
            isUniqueLogin = !logins.contains(randomLogin);
            if (!isUniqueLogin) {
                // Jeżeli login nie jest unikalny, zakończ tę iterację pętli
                continue;
            }
            logins.add(randomLogin);
            
            } while(!isUniqueLogin);
            
            userElement.addContent(nameElement);
            userElement.addContent(surnameElement);
            userElement.addContent(loginElement);

            rootElement.addContent(userElement);
            
        }

        // Zapis do pliku XML
        try {
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8"));
            xmlOutput.output(document, new FileWriter("generated_data.xml", StandardCharsets.UTF_8));
            System.out.println("Plik XML został pomyślnie wygenerowany.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomLogin(String name, String surname) {
        // Funkcja do tworzenia loginu składającego się z 3 pierwszych liter imienia i nazwiska i 3 losowych cyfr
    	Random random = new Random();
        String prefix = name.substring(0, 3).toLowerCase();
        String suffix = surname.substring(0, 3).toLowerCase();
        String numbers = String.format("%03d", random.nextInt(1000)); 

        return prefix + suffix + numbers;
    }
}

