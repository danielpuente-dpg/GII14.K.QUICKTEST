package aplicacion.android.danielvm.quicktestandroid.adapters.Teacher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.models.android.Student;
import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase StudentAdapter encargada de tratar la logica del adaptor de los
 * cuestionarios sin resolver.
 *
 * @author Daniel Puente Gabarri.
 */

public class StudentAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Student> students;

    /**
     * Contructor de la clase.
     * @param context, context.
     * @param layout, layout.
     * @param students, students.
     */
    public StudentAdapter(Context context, int layout, List<Student> students) {
        this.context = context;
        this.layout = layout;
        this.students = students;
    }

    /**
     * Metodo encargado de devolver el numero de alumnos.
     * @return int, numero de cursos.
     */
    @Override
    public int getCount() {
        return this.students.size();
    }

    /**
     * Metodo encargado de obtener un alumno dado.
     * @param position, position
     * @return Student, student.
     */
    @Override
    public Student getItem(int position) {
        return this.students.get(position);
    }

    /**
     * Metodo que proporciona el id.
     * @param position, position.
     * @return long, position.
     */
    @Override
    public long getItemId(int position) {
        return position;
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
            holder.textViewFullName = (TextView) convertView.findViewById(R.id.textViewFullName);
            holder.textViewEmailName = (TextView) convertView.findViewById(R.id.textViewEmailName);
            holder.textViewGrade = (TextView) convertView.findViewById(R.id.textViewMoodleStudentGrade);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewMoodleStudentGrade);

            // Actualizamos la vista
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtenemos la informacion sobre la vista actual
        final Student currentStudent = getItem(position);

        // Cambiamos la vista con los nuevos valores
        holder.textViewFullName.setText(currentStudent.getFullname());
        holder.textViewEmailName.setText(currentStudent.getEmail());

        if(currentStudent.getGrade() == -1){
            holder.imageView.setVisibility(View.VISIBLE);
            holder.textViewGrade.setVisibility(View.INVISIBLE);
        }else{
            DecimalFormat df = new DecimalFormat("##.##");
            String grade = df.format(currentStudent.getGrade() * 10);
            holder.textViewGrade.setText(grade + "/10");
            holder.textViewGrade.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        Log.d("StudentAdapter", position + "");
        return convertView;
    }

    /**
     * Clase ViewHolder interna encarga de abstraer los elementos que forman la UI.
     * @author Daniel Puente Gabarri.
     */
    static class ViewHolder {
        private TextView textViewFullName;
        private TextView textViewEmailName;
        private TextView textViewGrade;
        private ImageView imageView;

    }
}
