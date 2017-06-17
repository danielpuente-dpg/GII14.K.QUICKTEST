package aplicacion.android.danielvm.quicktestandroid.models.android;

/**
 * Created by Daniel on 17/06/2017.
 */

public class Help {
    private int img;
    private String type;
    private String text;

    public Help(int img, String type, String text) {
        this.img = img;
        this.type = type;
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
