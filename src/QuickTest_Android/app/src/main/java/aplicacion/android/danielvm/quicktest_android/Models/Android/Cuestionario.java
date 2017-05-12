package aplicacion.android.danielvm.quicktest_android.Models.Android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 23/03/2017.
 */



public class Cuestionario implements Parcelable {

    private int idCuestionario;
    private String descripcion;
    private int imgIcon;
    private String curso;
    private String claveCliente;

    public Cuestionario(int idCuestionario, String descripcion, int imgIcon, String curso, String claveCliente) {
        this.idCuestionario = idCuestionario;
        this.descripcion = descripcion;
        this.imgIcon = imgIcon;
        this.curso = curso;
        this.claveCliente = claveCliente;
    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    protected Cuestionario(Parcel in) {
        idCuestionario = in.readInt();
        descripcion = in.readString();
        imgIcon = in.readInt();
        curso = in.readString();
        claveCliente = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCuestionario);
        dest.writeString(descripcion);
        dest.writeInt(imgIcon);
        dest.writeString(curso);
        dest.writeString(claveCliente);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cuestionario> CREATOR = new Parcelable.Creator<Cuestionario>() {
        @Override
        public Cuestionario createFromParcel(Parcel in) {
            return new Cuestionario(in);
        }

        @Override
        public Cuestionario[] newArray(int size) {
            return new Cuestionario[size];
        }
    };
}