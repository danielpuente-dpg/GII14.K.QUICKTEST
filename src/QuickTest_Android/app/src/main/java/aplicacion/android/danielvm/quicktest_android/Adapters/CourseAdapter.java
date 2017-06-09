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
 * Created by Daniel on 09/06/2017.
 */

public class CourseAdapter extends BaseAdapter {

    // Atributos
    private Context context;
    private int layout;
    private List<Course> courses;

    public CourseAdapter(Context context, int layout, List<Course> courses) {
        this.context = context;
        this.layout = layout;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return this.courses.size();
    }

    @Override
    public Course getItem(int position) {
        return this.courses.get(position);
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

    static class ViewHolder {
        private TextView textViewTitle;
        private TextView textViewShortName;
    }
}
