package co.edu.unbosque.dto;

public class AutenticacionDTOs {

	public record LoginRequest(
			String username,
		    String password
			) {}
}
