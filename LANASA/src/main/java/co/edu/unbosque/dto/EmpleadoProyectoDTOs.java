package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public final class EmpleadoProyectoDTOs {
    private EmpleadoProyectoDTOs(){}

    public static record CrearEmpleadoProyectoRequest(
        @NotNull Integer idEmpleado,
        @NotNull Integer idProyecto,
        @NotNull Short idRol,          // PK de RolEmpleado
        @NotNull LocalDate fechaInicio,
        LocalDate fechaFin,            // opcional
        @DecimalMin("0.00") BigDecimal horasTrabajadas  // opcional
    ) {}

    public static record ActualizarEmpleadoProyectoRequest(
        Short idRol,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        @DecimalMin("0.00") BigDecimal horasTrabajadas,
        Boolean estado
    ) {}

    public static record EmpleadoProyectoDTO(
        Integer idEmpleadoProyecto,
        Integer idEmpleado,
        String nombreEmpleado,     // puedes armarlo en service (p.ej. "Nombre Apellido")
        Integer idProyecto,
        String codigoProyecto,
        String nombreProyecto,
        Short idRol,
        String rol,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal horasTrabajadas,
        Boolean estado
    ) {}
}
