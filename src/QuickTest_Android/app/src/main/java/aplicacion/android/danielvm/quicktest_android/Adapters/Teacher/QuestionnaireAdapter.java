package aplicacion.android.danielvm.quicktest_android.Adapters.Teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Clase QuestionnaireAdapter encargada de tratar la logica del adaptor de los
 * alumnos.
 *
 * @author Daniel Puente Gabarri.
 */

public class QuestionnaireAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Questionnaire> questionnaires;

    /**
     * Construtor de la clase.
     * @param context, context.
     * @param layout, layout.
     * @param questionnaires, questionnaires.
     */
    public QuestionnaireAdapter(Context context, int layout, List<Questionnaire> questionnaires) {
        this.context = context;
        this.layout = layout;
        this.questionnaires = questionnaires;
    }

    /**
     * Metodo encargado de devolver el numero de cuestionarios.
     * @return int, numero de cursos.
     */
    @Override
    public int getCount() {
        return this.questionnaires.size();
    }

    /**
     * Metodo encargado de obtener un cuestionario dado.
     * @param position, position
     * @return Questionnaire, questionnaire.
     */
    @Override
    public Questionnaire getItem(int position) {
        return this.questionnaires.get(position);
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
            holder.textViewTeacherName = (TextView) convertView.findViewById(R.id.textViewTeacherName);
            holder.textViewTeacherNameCourse = (TextView) convertView.findViewById(R.id.textViewTeacherNameCourse);

            // Actualizamos la vista
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtenemos la informacion sobre la vista actual
        final Questionnaire currenQuestionnaire = getItem(position);

        // Cambiamos la vista con los nuevos valores
        holder.textViewTeacherName.setText(currenQuestionnaire.getDescription());
        holder.textViewTeacherNameCourse.setText(currenQuestionnaire.getCourseName());

        return convertView;
    }

    /**
     * Clase ViewHolder interna encarga de abstraer los elementos que forman la UI.
     * @author Daniel Puente Gabarri.
     */
    static class ViewHolder{
        private TextView textViewTeacherName;
        private TextView textViewTeacherNameCourse;
    }
}
