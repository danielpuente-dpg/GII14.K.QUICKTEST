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
 * Created by Daniel on 09/06/2017.
 */

public class QuestionaryAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Questionnaire> questionaries;

    public QuestionaryAdapter(Context context, int layout, List<Questionnaire> questionaries) {
        this.context = context;
        this.layout = layout;
        this.questionaries = questionaries;
    }

    @Override
    public int getCount() {
        return this.questionaries.size();
    }

    @Override
    public Questionnaire getItem(int position) {
        return this.questionaries.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

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
        holder.textViewTeacherName.setText(currenQuestionnaire.getDescripcion());
        holder.textViewTeacherNameCourse.setText(currenQuestionnaire.getCurso());

        return convertView;
    }

    static class ViewHolder{
        private TextView textViewTeacherName;
        private TextView textViewTeacherNameCourse;
    }
}
