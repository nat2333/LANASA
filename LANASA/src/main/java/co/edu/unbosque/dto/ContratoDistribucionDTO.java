package co.edu.unbosque.dto;

public class ContratoDistribucionDTO {

	private String tipoContrato;
    private Long cantidad;
    private Double porcentaje;
    
    public ContratoDistribucionDTO(String tipoContrato, Long cantidad) {
        this.tipoContrato = tipoContrato;
        this.cantidad = cantidad;
    }

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

    
}
