package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase FeedBackTest encargada de realizar las pruebas unitarias sobre la clase FeedBack.
 *
 * @author Daniel Puente Gabarri.
 */

public class FeedBackTest {

    @Test
    public void feedBackTest(){

        int cSin = 3;
        int cAmarillo = 2;
        int cVerde = 1;
        int cPreguntasRespondidas = 6;
        String puntAmarillo = "1.21";
        String puntVerde = "22";
        String puntSin = "12";
        int aciertoAmarillo = 1;
        int aciertoVerde = 1;
        int aciertoSin = 2;
        int fallosAmarillo = 1;
        int fallosVerde = 1;
        int fallosSin = 1;
        double puntTotal = 6;
        String puntMaxPosible = "12";
        int totalPreguntas = 12;
        int orden = 2;
        String mensaje = "mensaje";
        String tipoPanel = "verde";
        String premio = "4.3";
        String ordenRespuesta = "2";
        String tipoText = "rapido";

        FeedBack feedBack = new FeedBack(cSin, cAmarillo, cVerde, cPreguntasRespondidas,
                puntAmarillo, puntVerde, puntSin, aciertoAmarillo, aciertoVerde, aciertoSin,
                fallosAmarillo, fallosVerde, fallosSin, puntTotal, puntMaxPosible, totalPreguntas,
                orden, mensaje, tipoPanel, premio, ordenRespuesta, tipoText);

        // Comprobamos que la informacion sea almacenada correctamente

        assertEquals(cSin, feedBack.getcSin());
        assertEquals(cAmarillo, feedBack.getcAmarillo());
        assertEquals(cVerde, feedBack.getcVerde());
        assertEquals(cPreguntasRespondidas, feedBack.getcPreguntasRespondidas());
        assertEquals(puntAmarillo, feedBack.getPuntAmarillo());
        assertEquals(puntVerde, feedBack.getPuntVerde());
        assertEquals(puntSin, feedBack.getPuntSin());
        assertEquals(aciertoAmarillo, feedBack.getAciertoAmarillo());
        assertEquals(aciertoVerde, feedBack.getAciertoVerde());
        assertEquals(aciertoSin, feedBack.getAciertoSin());
        assertEquals(fallosAmarillo, feedBack.getFallosAmarillo());
        assertEquals(fallosVerde, feedBack.getFallosVerde());
        assertEquals(fallosSin, feedBack.getFallosSin());
        assertEquals(puntTotal, feedBack.getPuntTotal(), 0);
        assertEquals(puntMaxPosible, feedBack.getPuntMaxPosible());
        assertEquals(totalPreguntas, feedBack.getTotalPreguntas());
        assertEquals(orden, feedBack.getOrden());
        assertEquals(mensaje, feedBack.getMensaje());
        assertEquals(tipoPanel, feedBack.getTipoPanel());
        assertEquals(premio, feedBack.getPremio());
        assertEquals(ordenRespuesta, feedBack.getOrdenRespuesta());
        assertEquals(tipoText, feedBack.getTipoText());

        // Modificamos el contenido

        int cSin2 = 1;
        int cAmarillo2 = 1;
        int cVerde2 = 2;
        int cPreguntasRespondidas2 = 16;
        String puntAmarillo2 = "2.21";
        String puntVerde2 = "222";
        String puntSin2 = "112";
        int aciertoAmarillo2 = 21;
        int aciertoVerde2 = 21;
        int aciertoSin2 = 22;
        int fallosAmarillo2 = 11;
        int fallosVerde2 = 14;
        int fallosSin2 = 51;
        double puntTotal2 = 116;
        String puntMaxPosible2 = "211";
        int totalPreguntas2 = 1432;
        int orden2 = 1;
        String mensaje2 = "mensaje2";
        String tipoPanel2 = "ambar";
        String premio2 = "12.3";
        String ordenRespuesta2 = "1";
        String tipoText2 = "lento";

        feedBack.setcSin(cSin2);
        feedBack.setcAmarillo(cAmarillo2);
        feedBack.setcVerde(cVerde2);
        feedBack.setcPreguntasRespondidas(cPreguntasRespondidas2);
        feedBack.setPuntAmarillo(puntAmarillo2);
        feedBack.setPuntVerde(puntVerde2);
        feedBack.setPuntSin(puntSin2);
        feedBack.setAciertoAmarillo(aciertoAmarillo2);
        feedBack.setAciertoVerde(aciertoVerde2);
        feedBack.setAciertoSin(aciertoSin2);
        feedBack.setFallosAmarillo(fallosAmarillo2);
        feedBack.setFallosVerde(fallosVerde2);
        feedBack.setFallosSin(fallosSin2);
        feedBack.setPuntTotal(puntTotal2);
        feedBack.setPuntMaxPosible(puntMaxPosible2);
        feedBack.setTotalPreguntas(totalPreguntas2);
        feedBack.setOrden(orden2);
        feedBack.setPuntMaxPosible(puntMaxPosible2);
        feedBack.setMensaje(mensaje2);
        feedBack.setTipoPanel(tipoPanel2);
        feedBack.setPremio(premio2);
        feedBack.setOrdenRespuesta(ordenRespuesta2);
        feedBack.setTipoText(tipoText2);

        // Comprobamos que la nueva informacion

        assertNotEquals(cSin, feedBack.getcSin());
        assertNotEquals(cAmarillo, feedBack.getcAmarillo());
        assertNotEquals(cVerde, feedBack.getcVerde());
        assertNotEquals(cPreguntasRespondidas, feedBack.getcPreguntasRespondidas());
        assertNotEquals(puntAmarillo, feedBack.getPuntAmarillo());
        assertNotEquals(puntVerde, feedBack.getPuntVerde());
        assertNotEquals(puntSin, feedBack.getPuntSin());
        assertNotEquals(aciertoAmarillo, feedBack.getAciertoAmarillo());
        assertNotEquals(aciertoVerde, feedBack.getAciertoVerde());
        assertNotEquals(aciertoSin, feedBack.getAciertoSin());
        assertNotEquals(fallosAmarillo, feedBack.getFallosAmarillo());
        assertNotEquals(fallosVerde, feedBack.getFallosVerde());
        assertNotEquals(fallosSin, feedBack.getFallosSin());
        assertNotEquals(puntTotal, feedBack.getPuntTotal(), 0);
        assertNotEquals(puntMaxPosible, feedBack.getPuntMaxPosible());
        assertNotEquals(totalPreguntas, feedBack.getTotalPreguntas());
        assertNotEquals(orden, feedBack.getOrden());
        assertNotEquals(mensaje, feedBack.getMensaje());
        assertNotEquals(tipoPanel, feedBack.getTipoPanel());
        assertNotEquals(premio, feedBack.getPremio());
        assertNotEquals(ordenRespuesta, feedBack.getOrdenRespuesta());
        assertNotEquals(tipoText, feedBack.getTipoText());


        assertEquals(cSin2, feedBack.getcSin());
        assertEquals(cAmarillo2, feedBack.getcAmarillo());
        assertEquals(cVerde2, feedBack.getcVerde());
        assertEquals(cPreguntasRespondidas2, feedBack.getcPreguntasRespondidas());
        assertEquals(puntAmarillo2, feedBack.getPuntAmarillo());
        assertEquals(puntVerde2, feedBack.getPuntVerde());
        assertEquals(puntSin2, feedBack.getPuntSin());
        assertEquals(aciertoAmarillo2, feedBack.getAciertoAmarillo());
        assertEquals(aciertoVerde2, feedBack.getAciertoVerde());
        assertEquals(aciertoSin2, feedBack.getAciertoSin());
        assertEquals(fallosAmarillo2, feedBack.getFallosAmarillo());
        assertEquals(fallosVerde2, feedBack.getFallosVerde());
        assertEquals(fallosSin2, feedBack.getFallosSin());
        assertEquals(puntTotal2, feedBack.getPuntTotal(), 0);
        assertEquals(puntMaxPosible2, feedBack.getPuntMaxPosible());
        assertEquals(totalPreguntas2, feedBack.getTotalPreguntas());
        assertEquals(orden2, feedBack.getOrden());
        assertEquals(mensaje2, feedBack.getMensaje());
        assertEquals(tipoPanel2, feedBack.getTipoPanel());
        assertEquals(premio2, feedBack.getPremio());
        assertEquals(ordenRespuesta2, feedBack.getOrdenRespuesta());
        assertEquals(tipoText2, feedBack.getTipoText());

    }
}
