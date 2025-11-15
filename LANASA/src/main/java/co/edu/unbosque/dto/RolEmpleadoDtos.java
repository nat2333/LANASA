package co.edu.unbosque.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class RolEmpleadoDtos {

    public static record CrearRolEmpleadoRequest(
        @NotBlank @Size(max=80) String rolEmpleado,
        @DecimalMin("0.00") @Digits(integer=8, fraction=2) BigDecimal tarifaHora
    ) {}

    public static record ActualizarRolEmpleadoRequest(
        @Size(max=80) String rolEmpleado,
        @DecimalMin("0.00") @Digits(integer=8, fraction=2) BigDecimal tarifaHora
    ) {}

    public static record RolEmpleadoDTO(
        Short idRolEmpleado,
        String rolEmpleado,
        BigDecimal tarifaHora,
        Boolean estado
    ) {}
}
