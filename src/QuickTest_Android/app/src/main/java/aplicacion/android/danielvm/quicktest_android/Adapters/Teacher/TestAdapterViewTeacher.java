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

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Answer;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Clase TestAdapterViewTeacher encargada de tratar la logica del adaptor de los
 * cuestionarios.
 *
 * @author Daniel Puente Gabarri.
 */

public class TestAdapterViewTeacher extends RecyclerView.Adapter<TestAdapterViewTeacher.ViewHolder>{

    private List<Test> tests;
    private int layout;
    private Context context;

    /**
     * Constructor de la clase.
     * @param tests, tests.
     * @param layout, layout.
     */
    public TestAdapterViewTeacher(List<Test> tests, int layout) {
        this.tests = tests;
        this.layout = layout;
    }

    /**
     * Metodo encargado de inflar la vista.
     *
     * @param parent,   parent.
     * @param viewType, viewType.
     * @return ViewHolder, view.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista con nuestro Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        // Devolvemos la vista al View Holder
        return new ViewHolder(view);
    }

    /**
     * Metodo encargado de enlazar el contenido de cada vista.
     *
     * @param holder,   holder.
     * @param position, position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dataBind(position);
    }

    /**
     * Metodo encargado de proporcionar el numero de preguntas del cuestionario.
     *
     * @return int, tamaño.
     */
    @Override
    public int getItemCount() {
        return this.tests.size();
    }

    /**
     * Clase interna ViewHolder encarga de instanciar y de actualizar la
     * informacion de los elementos que forman la UI.
     *
     * @author Daniel Puente Gabarri.
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        // Elementos de la UI
        private TextView answer;
        private RadioGroup radioGroup;

        /**
         * Constructor de la clase.
         * @param view, view.
         */
        public ViewHolder(View view) {
            super(view);

            this.answer = (TextView) view.findViewById(R.id.textViewStatementViewTeacher);
            this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupViewTeacher);
        }

        /**
         * Metodo encargado de enlazar los elementos de la UI.
         * @param position, position.
         */
        public void dataBind(int position) {
            final Test test = tests.get(position);

            radioGroup.removeAllViews();
            this.answer.setText((position + 1) + " - " + test.getQuestion());
            for (Answer r : test.getAnswers()) {
                RadioButton newRadio = createRadioButton(r.getTitulo(), r.getIdRespuesta());
                radioGroup.addView(newRadio);
            }
        }

        /**
         * Metodo encargado de crear el radio Button.
         * @param title, title.
         * @param idButton, idButton
         * @return RadioButton, radioButton.
         */
        private RadioButton createRadioButton(String title, int idButton) {

            RadioButton newRadio = new RadioButton(context);
            LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            newRadio.setLayoutParams(params);
            newRadio.setText(title);
            newRadio.setTag(title);
            newRadio.setId(idButton);

            return newRadio;
        }
    }
}
