package mx.ine.procprimerinsa.helper.impl;

/**
 * 
 * Fecha de creación: 20/01/2017, 12:36:02
 *
 * Copyright (c) 2017 Instituto Nacional Electoral. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Instituto Federal Electoral. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine el propio Instituto.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOParametrosGenerales;
import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;
import mx.ine.procprimerinsa.dto.db.DTOCFechas;
import mx.ine.procprimerinsa.dto.db.DTOCParametros;
import mx.ine.procprimerinsa.helper.HLPValidadorInterface;
import mx.ine.procprimerinsa.util.Utilidades;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Clase para validaciones generales
 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
 * @version 1.0
 * @since 20/01/2017
 */
@Component("hlpValidador")
@Scope("prototype")
public class HLPValidador extends HLPTransformadorMensajes implements HLPValidadorInterface {
	/**
	 * Serializado con java 11 25/09/2020
	 */
	private static final long serialVersionUID = -3911319443012053073L;

	/**
     * Devuelve una lista de etiquetas en caso de que esta se contenga
     * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
     * @version 1.0
     * @since 10/10/2016
     * @param mapa
     * @param etiqueta
     * @return List<DTOCEtiquetas>
     */
    @Override
    public List<DTOCEtiquetas> verificarEtiquetas(Map<String, List<DTOCEtiquetas>> mapa,
        String etiqueta) {
        List<DTOCEtiquetas> lista = new ArrayList<DTOCEtiquetas>();
        if (mapa != null && mapa.size() > 0) {
            lista = mapa.get(etiqueta);
            return lista;
        }
        this.agregarMensajePersonalizado(TipoMensajes.ERROR, null,
            Utilidades.mensajeProperties("validacion_mensajes_generales_formularioIncomp"));
        return null;
    }

    @Override
    public List<DTOCFechas> verificarFechas(Map<Integer, List<DTOCFechas>> mapa,
        Integer idFecha) {
        List<DTOCFechas> lista = new ArrayList<DTOCFechas>();
        if (mapa != null && mapa.size() > 0) {
            lista = mapa.get(idFecha);
            return lista;
        }
        this.agregarMensajePersonalizado(TipoMensajes.ERROR, null,
            Utilidades.mensajeProperties("validacion_mensajes_generales_formularioIncomp"));
        return null;
    }

    @Override
    public List<DTOCParametros> verificarParametros(Map<Integer, List<DTOCParametros>> mapa,
        Integer idParametro) {
        List<DTOCParametros> lista = new ArrayList<DTOCParametros>();
        if (mapa != null && mapa.size() > 0) {
            lista = mapa.get(idParametro);
            return lista;
        }
        this.agregarMensajePersonalizado(TipoMensajes.ERROR, null,
            Utilidades.mensajeProperties("validacion_mensajes_generales_formularioIncomp"));
        return null;
    }

    @Override
    public List<DTOParametrosGenerales> verificarParametrosGenerales(
        Map<Integer, List<DTOParametrosGenerales>> mapa, Integer etiqueta) {
        List<DTOParametrosGenerales> lista = new ArrayList<DTOParametrosGenerales>();
        if (mapa != null && mapa.size() > 0) {
            lista = mapa.get(etiqueta);
            return lista;
        }
        this.agregarMensajePersonalizado(TipoMensajes.ERROR, null,
            Utilidades.mensajeProperties("validacion_mensajes_generales_formularioIncomp"));
        return null;
    }
}
