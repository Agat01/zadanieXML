package encje;

public class User {

	private Long id;
	private String name;
	private String surname;
	private String login;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public User(Long id, String name, String surname, String login) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
	}
	
	public User(String name, String surname, String login) {
		super();
		this.name = name;
		this.surname = surname;
		this.login = login;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", login=" + login + "]";
	}

}
