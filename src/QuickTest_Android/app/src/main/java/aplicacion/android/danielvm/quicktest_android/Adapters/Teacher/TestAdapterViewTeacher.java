package aplicacion.android.danielvm.quicktest_android.Adapters.Teacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 12/06/2017.
 */

public class TestAdapterViewTeacher extends RecyclerView.Adapter<TestAdapterViewTeacher.ViewHolder>{

    private List<Test> tests;
    private int layout;
    private Context context;

    public TestAdapterViewTeacher(List<Test> tests, int layout) {
        this.tests = tests;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista con nuestro Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        // Devolvemos la vista al View Holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dataBind(position);
    }

    @Override
    public int getItemCount() {
        return this.tests.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        // Elementos de la UI
        private TextView pregunta;
        private RadioGroup radioGroup;

        public ViewHolder(View view) {
            super(view);

            this.pregunta = (TextView) view.findViewById(R.id.textViewStatementViewTeacher);
            this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupViewTeacher);
        }

        public void dataBind(int position) {
            final Test test = tests.get(position);

            radioGroup.removeAllViews();
            this.pregunta.setText((position + 1) + " - " + test.getPregunta());
            for (Respuesta r : test.getRespuestas()) {
                RadioButton nuevoRadio = createRadioButton(r.getTitulo(), r.getIdRespuesta());
                radioGroup.addView(nuevoRadio);
            }
        }

        private RadioButton createRadioButton(String marca, int i) {

            RadioButton nuevoRadio = new RadioButton(context);
            LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            nuevoRadio.setLayoutParams(params);
            nuevoRadio.setText(marca);
            nuevoRadio.setTag(marca);
            nuevoRadio.setId(i);

            return nuevoRadio;
        }
    }
}
