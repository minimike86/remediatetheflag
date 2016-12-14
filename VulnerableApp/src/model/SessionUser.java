package model;

public class SessionUser implements java.io.Serializable {

	private static final long serialVersionUID = 4108122314560840903L;
	
	private Integer idUser;
	private String username;
	private String email;

	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer IdUser) {
		this.idUser = IdUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}