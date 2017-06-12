package aplicacion.android.danielvm.quicktest_android.Adapters.Teacher;

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

import aplicacion.android.danielvm.quicktest_android.Models.Android.Student;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 11/06/2017.
 */

public class StudentAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Student> students;

    public StudentAdapter(Context context, int layout, List<Student> students) {
        this.context = context;
        this.layout = layout;
        this.students = students;
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public Student getItem(int position) {
        return this.students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.d("StudentAdapter", position + "");
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

    static class ViewHolder {
        private TextView textViewFullName;
        private TextView textViewEmailName;
        private TextView textViewGrade;
        private ImageView imageView;

    }
}
