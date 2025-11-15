package co.edu.unbosque.dto;

import jakarta.validation.constraints.*;

public class TipoProyectoDtos {

    public static record CrearTipoProyectoRequest(
        @NotBlank @Size(max=80) String tipoProyecto
    ) {}

    public static record TipoProyectoDTO(
        short idTipoProyecto,
        String tipoProyecto,
        Boolean estado
    ) {}
}
