package co.edu.unbosque.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.CargoCostoDTO;
import co.edu.unbosque.dto.ContratoDistribucionDTO;
import co.edu.unbosque.dto.EmpleadoSalarioDTO;
import co.edu.unbosque.repository.EstadisticasEmpleadoRepository;

@Service
@Transactional(readOnly = true)
public class EstadisticasEmpleadoService {

	@Autowired
	private EstadisticasEmpleadoRepository rep;

	public List<EmpleadoSalarioDTO> getTopEmpleadosPorSalario(int limite) {
		List<EmpleadoSalarioDTO> todos = rep.findTopEmpleadosPorSalario();
		if(todos.size() > limite) {
			return todos.subList(0, limite);
		}
		return todos;
	}

	public List<ContratoDistribucionDTO> getDistribucionContratos() {
		List<ContratoDistribucionDTO> distribucion =  rep.findDistribucionPorTipoContrato();
		long total = 0;
		for(ContratoDistribucionDTO d : distribucion) {
			total += d.getCantidad();
		}

		for(ContratoDistribucionDTO d : distribucion) {
			double pct = (d.getCantidad() * 100.0) / total;
		    d.setPorcentaje(Math.round(pct * 100.0) / 100.0);
		}

		return distribucion;
	}

	public List<CargoCostoDTO> getCostosPorCargo() {
		return rep.findCostoPorCargo();
	}
}
