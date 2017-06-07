package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Created by Daniel on 07/06/2017.
 */

public class FeedBack {
    private int cSin;
    private int cAmarillo;
    private int cVerde;
    private int cPreguntasRespondidas;
    private String puntAmarillo;
    private String puntVerde;
    private String puntSin;
    private int aciertoAmarillo;
    private int aciertoVerde;
    private int aciertoSin;
    private int fallosAmarillo;
    private int fallosVerde;
    private int fallosSin;
    private double puntTotal;
    private String puntMaxPosible;
    private int totalPreguntas;
    private int orden;
    private String mensaje;
    private String tipoPanel;
    private String premio;
    private String ordenRespuesta;
    private String tipoText;

    public FeedBack(int cSin, int cAmarillo, int cVerde, int cPreguntasRespondidas, String puntAmarillo, String puntVerde, String puntSin, int aciertoAmarillo, int aciertoVerde, int aciertoSin, int fallosAmarillo, int fallosVerde, int fallosSin, double puntTotal, String puntMaxPosible, int totalPreguntas, int orden, String mensaje, String tipoPanel, String premio, String ordenRespuesta, String tipoText) {
        this.cSin = cSin;
        this.cAmarillo = cAmarillo;
        this.cVerde = cVerde;
        this.cPreguntasRespondidas = cPreguntasRespondidas;
        this.puntAmarillo = puntAmarillo;
        this.puntVerde = puntVerde;
        this.puntSin = puntSin;
        this.aciertoAmarillo = aciertoAmarillo;
        this.aciertoVerde = aciertoVerde;
        this.aciertoSin = aciertoSin;
        this.fallosAmarillo = fallosAmarillo;
        this.fallosVerde = fallosVerde;
        this.fallosSin = fallosSin;
        this.puntTotal = puntTotal;
        this.puntMaxPosible = puntMaxPosible;
        this.totalPreguntas = totalPreguntas;
        this.orden = orden;
        this.mensaje = mensaje;
        this.tipoPanel = tipoPanel;
        this.premio = premio;
        this.ordenRespuesta = ordenRespuesta;
        this.tipoText = tipoText;
    }

    public int getcSin() {
        return cSin;
    }

    public void setcSin(int cSin) {
        this.cSin = cSin;
    }

    public int getcAmarillo() {
        return cAmarillo;
    }

    public void setcAmarillo(int cAmarillo) {
        this.cAmarillo = cAmarillo;
    }

    public int getcVerde() {
        return cVerde;
    }

    public void setcVerde(int cVerde) {
        this.cVerde = cVerde;
    }

    public int getcPreguntasRespondidas() {
        return cPreguntasRespondidas;
    }

    public void setcPreguntasRespondidas(int cPreguntasRespondidas) {
        this.cPreguntasRespondidas = cPreguntasRespondidas;
    }

    public String getPuntAmarillo() {
        return puntAmarillo;
    }

    public void setPuntAmarillo(String puntAmarillo) {
        this.puntAmarillo = puntAmarillo;
    }

    public String getPuntVerde() {
        return puntVerde;
    }

    public void setPuntVerde(String puntVerde) {
        this.puntVerde = puntVerde;
    }

    public String getPuntSin() {
        return puntSin;
    }

    public void setPuntSin(String puntSin) {
        this.puntSin = puntSin;
    }

    public int getAciertoAmarillo() {
        return aciertoAmarillo;
    }

    public void setAciertoAmarillo(int aciertoAmarillo) {
        this.aciertoAmarillo = aciertoAmarillo;
    }

    public int getAciertoVerde() {
        return aciertoVerde;
    }

    public void setAciertoVerde(int aciertoVerde) {
        this.aciertoVerde = aciertoVerde;
    }

    public int getAciertoSin() {
        return aciertoSin;
    }

    public void setAciertoSin(int aciertoSin) {
        this.aciertoSin = aciertoSin;
    }

    public int getFallosAmarillo() {
        return fallosAmarillo;
    }

    public void setFallosAmarillo(int fallosAmarillo) {
        this.fallosAmarillo = fallosAmarillo;
    }

    public int getFallosVerde() {
        return fallosVerde;
    }

    public void setFallosVerde(int fallosVerde) {
        this.fallosVerde = fallosVerde;
    }

    public int getFallosSin() {
        return fallosSin;
    }

    public void setFallosSin(int fallosSin) {
        this.fallosSin = fallosSin;
    }

    public double getPuntTotal() {
        return puntTotal;
    }

    public void setPuntTotal(double puntTotal) {
        this.puntTotal = puntTotal;
    }

    public String getPuntMaxPosible() {
        return puntMaxPosible;
    }

    public void setPuntMaxPosible(String puntMaxPosible) {
        this.puntMaxPosible = puntMaxPosible;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public void setTotalPreguntas(int totalPreguntas) {
        this.totalPreguntas = totalPreguntas;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoPanel() {
        return tipoPanel;
    }

    public void setTipoPanel(String tipoPanel) {
        this.tipoPanel = tipoPanel;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getOrdenRespuesta() {
        return ordenRespuesta;
    }

    public void setOrdenRespuesta(String ordenRespuesta) {
        this.ordenRespuesta = ordenRespuesta;
    }

    public String getTipoText() {
        return tipoText;
    }

    public void setTipoText(String tipoText) {
        this.tipoText = tipoText;
    }
}
