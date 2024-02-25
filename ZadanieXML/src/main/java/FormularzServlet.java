
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import encje.User;

@WebServlet("/formularz")
public class FormularzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		ArrayList<User> users = new ArrayList<>();
		
		HttpSession session = request.getSession();
	    ArrayList<User> successfulUsers = new ArrayList<>();
	    ArrayList<User> unsuccessfulUsers = new ArrayList<>();

		// odczytanie pliku .xml
		 for (Part part : request.getParts()) {
	            String fileName = getFileName(part);
	            InputStream fileContent = part.getInputStream();

	            out.println("Odczytano XML: fileName=" + fileName);
	            
	            try {        
	            SAXBuilder saxBuilder = new SAXBuilder();
	            Document document;
	            document = saxBuilder.build(fileContent);

	            Element rootElement = document.getRootElement();
	            List<Element> userElements = rootElement.getChildren("user");

	            // odczytanie danych z pliku .xml i zapisywanie użytkowników do arraylist
	            for (Element userElement : userElements) {
	                String name = userElement.getChildText("name");
	                String surname = userElement.getChildText("surname");
	                String login = userElement.getChildText("login");

	                User user=new User(name,surname,login);
                    users.add(user); 
	            } 
	            
	          
                    try {
        				Driver sterownik = new com.mysql.cj.jdbc.Driver();
        				DriverManager.registerDriver(sterownik);
        				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zad", "root", "zadanie");
        				Statement stmt = conn.createStatement();

        				// stworzenie listy wszystkich użytkowników
        				List<String> logins=new ArrayList<>();
        				String sql="select login from user";
        				ResultSet rs=stmt.executeQuery(sql);
        				while(rs.next()){
        					logins.add(rs.getString(1));
        				}
        					rs.close();
        					stmt.close();
        				
        				PreparedStatement pstmt = conn.prepareStatement("Insert into user values(null,?,?,?)");
	
        				for(User user:users) {
        					// sprawdzenie czy login nie występuje w arraylist
        					if(!logins.contains(user.getLogin())) {
        						pstmt.setString(1, user.getName());
        						pstmt.setString(2, user.getSurname());
        						pstmt.setString(3, user.getLogin());
        			
        					if (pstmt.executeUpdate() > 0) {
        						successfulUsers.add(new User(user.getName(),user.getSurname(),user.getLogin()));
        						logins.add(user.getLogin());
        					} else {
        						unsuccessfulUsers.add(new User(user.getName(),user.getSurname(),user.getLogin()));
        					}
        					} else {
        						unsuccessfulUsers.add(new User(user.getName(),user.getSurname(),user.getLogin()));
        					}
        				}
        				session.setAttribute("successfulUsers", successfulUsers);
        		        session.setAttribute("unsuccessfulUsers", unsuccessfulUsers);

        		        // Przekierowanie do rezultat.jsp
        		        RequestDispatcher dispatcher = request.getRequestDispatcher("/rezultat.jsp");
        		        dispatcher.forward(request, response);
        				
        				conn.close();
        			} catch (SQLException e) {
        				e.printStackTrace();
        			}
        			

		 } catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	            
	}
	private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
	
}
