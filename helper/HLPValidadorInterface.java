package mx.ine.procprimerinsa.helper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOParametrosGenerales;
import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;
import mx.ine.procprimerinsa.dto.db.DTOCFechas;
import mx.ine.procprimerinsa.dto.db.DTOCParametros;

/**
 * Interfaz que define comportamiento de validaciones generales
 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
 * @version 1.0
 * @since 20/01/2017
 */
public interface HLPValidadorInterface extends Serializable {
    /**
     * Verifica si existen etiquetas de un determinado tipo (llave)
     * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
     * @version 1.0
     * @since 07/12/2016
     * @param mapa
     * @param etiqueta
     * @return List<DTOCEtiquetas>
     */
    public List<DTOCEtiquetas> verificarEtiquetas(Map<String, List<DTOCEtiquetas>> mapa,
        String etiqueta);

    /**
     * Verifica si existen fechas de un determinado tipo (llave)
     * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
     * @version 1.0
     * @since 07/12/2016
     * @param mapa
     * @param idFecha
     * @return List<DTOCFechas>
     */
    public List<DTOCFechas> verificarFechas(Map<Integer, List<DTOCFechas>> mapa,
        Integer idFecha);

    /**
     * Verifica si existen parámetros de un determinado tipo (llave)
     * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
     * @version 1.0
     * @since 07/12/2016
     * @param mapa
     * @param idParametro
     * @return List<DTOCParametros>
     */
    public List<DTOCParametros> verificarParametros(Map<Integer, List<DTOCParametros>> mapa,
        Integer idParametro);

    /**
     * Verifica si existen parámetros generales de un determinado tipo (llave)
     * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
     * @version 1.0
     * @since 07/12/2016
     * @param mapa
     * @param etiqueta
     * @return List<DTOParametrosGenerales>
     */
    public List<DTOParametrosGenerales> verificarParametrosGenerales(
        Map<Integer, List<DTOParametrosGenerales>> mapa, Integer etiqueta);
}
