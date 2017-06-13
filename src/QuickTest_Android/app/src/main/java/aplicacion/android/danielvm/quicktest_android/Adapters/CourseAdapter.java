package aplicacion.android.danielvm.quicktest_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Clase CourseAdapter encargada de tratar la logica del adaptor de los cursos.
 * @author Daniel Puente Gabarri.
 */

public class CourseAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Course> courses;

    /**
     * Constructor de la clase.
     * @param context, context.
     * @param layout, layout.
     * @param courses, courses.
     */
    public CourseAdapter(Context context, int layout, List<Course> courses) {
        this.context = context;
        this.layout = layout;
        this.courses = courses;
    }

    /**
     * Metodo encargado de devolver el numero de cursos.
     * @return int, numero de cursos.
     */
    @Override
    public int getCount() {
        return this.courses.size();
    }

    /**
     * Metodo encargado de obtener un curso dado.
     * @param position, position
     * @return Course, course.
     */
    @Override
    public Course getItem(int position) {
        return this.courses.get(position);
    }

    /**
     * Metodo que proporciona el id.
     * @param id, id.
     * @return long, id.
     */
    @Override
    public long getItemId(int id) {
        return id;
    }

    /**
     * Metodo encargado de para cada vista de inflarla y añadir la informacion.
     * @param position, position.
     * @param convertView, convertView.
     * @param parent, parent.
     * @return View, convertView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            // Inflamos la vista con nuestro layout
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);

            // Referenciamos los elementos sobre los que aplicar nuestro layout
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewName);
            holder.textViewShortName = (TextView) convertView.findViewById(R.id.textViewShortName);

            // Actualizamos la vista
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtenemos la informacion sobre la vista actual
        final Course currentCourse = getItem(position);

        // Cambiamos la vista con los nuevos valores
        holder.textViewTitle.setText(currentCourse.getFullname());
        holder.textViewShortName.setText(currentCourse.getShortname());

        return convertView;
    }

    /**
     * Clase ViewHolder interna encarga de abstraer los elementos que forman la UI.
     * @author Daniel Puente Gabarri.
     */
    static class ViewHolder {
        private TextView textViewTitle;
        private TextView textViewShortName;
    }
}
