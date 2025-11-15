package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public final class ProyectoDtos {
    private ProyectoDtos(){}

    public static record CrearProyectoRequest(
        @NotNull Short idTipoProyecto,       
        @NotNull Integer idCliente,
        @NotNull Integer idDepartamento,
        @NotBlank @Size(max=150) String nombre,
        String descripcion,
        @NotNull LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        BigDecimal presupuestoAprobado,
        BigDecimal presupuestoUtilizado
    ) {}

    public static record ActualizarProyectoRequest(
        LocalDate fechaFinReal,
        BigDecimal presupuestoAprobado,
        BigDecimal presupuestoUtilizado
    ) {}

    public static record ProyectoDTO(
        Integer idProyecto,
        String codigo,
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        LocalDate fechaFinReal,
        BigDecimal presupuestoAprobado,
        BigDecimal presupuestoUtilizado,
        Boolean estado,
        Integer idCliente,
        String correoCliente,        
        Integer idDepartamento,
        String nombreDepartamento,
        Short idTipoProyecto,        
        String nombreTipoProyecto
    ) {}
}
