package lojaLN.operarios;

import java.io.Serializable;

public abstract class Operario implements Serializable {

	private final String id;
	private final String password;

	protected Operario(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

}