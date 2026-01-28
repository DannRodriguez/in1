package mx.ine.procprimerinsa.dao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;

public interface DAOFirmasCartasInterface extends DAOGenericInterface<DTOFirmasCartas, Serializable> {

	public void guardarFirma(DTOFirmasCartas firma);

	public DTOFirmasCartas obtenerFirma(Integer idDetalle, Integer idPartipacion);

}
