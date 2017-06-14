package aplicacion.android.danielvm.quicktest_android.Adapters.Student;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import aplicacion.android.danielvm.quicktest_android.Activities.Student.StudentActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.Student.TestActivity;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Answer;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Result;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Clase TestAdapter encargada de tratar la logica del adaptor de un cuestionario a resolver.
 *
 * @author Daniel Puente Gabarri.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<Test> tests;
    private int layout;
    private Context context;
    private OnItemClickListener listenerGreenWildCard;
    private OnItemClickListener listenerAmberWildCard;

    public static HashMap<Integer, Integer> answers = new HashMap();
    public static HashMap<Integer, Boolean> flags = new HashMap<>();
    public static HashMap<Integer, Result> postTest = new HashMap();

    /**
     * Constructor de la clase.
     *
     * @param tests,                 tests.
     * @param layout,                layout.
     * @param listenerGreenWildCard, listenerGreenWildCard.
     * @param listenerAmberWildCard, listenerAmberWildCard.
     */
    public TestAdapter(List<Test> tests, int layout, OnItemClickListener listenerGreenWildCard, OnItemClickListener listenerAmberWildCard) {
        this.tests = tests;
        this.layout = layout;
        this.listenerGreenWildCard = listenerGreenWildCard;
        this.listenerAmberWildCard = listenerAmberWildCard;
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
        // Llamamos la metodo encargado de añadir los datos propios de cada nameQuestionnaire
        holder.dataBind(this.tests.get(position), position, this.listenerGreenWildCard, this.listenerAmberWildCard);
    }

    /**
     * Metodo encargado de proporcionar el numero de preguntas del cuestionario.
     *
     * @return int, tamaño.
     */
    @Override
    public int getItemCount() {
        if (tests != null)
            return tests.size();
        return 0;
    }

    /**
     * Clase interna ViewHolder encarga de instanciar y de actualizar la
     * informacion de los elementos que forman la UI.
     *
     * @author Daniel Puente Gabarri.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        // Elementos de la UI
        private TextView answer;
        private RadioGroup radioGroup;
        private ImageView imageViewGreenWildCard;
        private ImageView imageViewAmberWildCard;

        // Atributos
        private TestActivity testActivity = new TestActivity();
        private StudentActivity studentActivity= new StudentActivity();

        /**
         * Constructor de la clase.
         *
         * @param view, view.
         */
        public ViewHolder(View view) {
            super(view);

            this.answer = (TextView) view.findViewById(R.id.textViewStatement);
            this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            this.imageViewGreenWildCard = (ImageView) view.findViewById(R.id.imageViewGreenWildCard);
            this.imageViewAmberWildCard = (ImageView) view.findViewById(R.id.imageViewAmberWildCard);

        }

        /**
         * Metodo encargado de instanciar los elementos de para cada vista.
         *
         * @param test
         * @param position
         * @param listenerGreenWildCard
         * @param listenerAmberWildCard
         */
        public void dataBind(final Test test, final int position, final OnItemClickListener listenerGreenWildCard, final OnItemClickListener listenerAmberWildCard) {

            radioGroup.removeAllViews();

            this.answer.setText((position + 1) + " - " + test.getQuestion());
            for (Answer r : test.getAnswers()) {
                RadioButton newRadio = createRadioButton(r.getTitulo(), r.getIdRespuesta());
                radioGroup.addView(newRadio);
            }

            // Si esa answer tiene comodin, lo activa en la vista
            hasWildCard(test);

            // Si el comodin ha sido usado.
            checkWildCard(radioGroup, test);

            // Evento al seleccionar una answer
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    answers.put(getAdapterPosition(), checkedId);
                    flags.put(getAdapterPosition(), true);

                    int idQuestion = test.getIdQuestion();
                    int idAnswer = checkedId;
                    String typeWildCard = "";
                    String idStudent = testActivity.key + ":" + studentActivity.user.getId();
                    Result result = new Result(idQuestion, idAnswer, typeWildCard, idStudent);

                    postTest.put(getAdapterPosition(), result);
                    Log.d("TestAdapter", "Listener->Position: " + getAdapterPosition() +  " - Btn: " + checkedId);
                }
            });

            // Evento para el comodin verde
            imageViewGreenWildCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getAlpha() == 1)
                        listenerGreenWildCard.onItemClick(test, getAdapterPosition());

                }
            });
            // Evento para el comodin ambar
            imageViewAmberWildCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getAlpha() == 1)
                        listenerAmberWildCard.onItemClick(test, getAdapterPosition());
                }
            });

        }

        /**
         * Metodo encargado de la visibilidad de los comodines.
         *
         * @param test, test.
         */
        private void hasWildCard(Test test) {
            // Si tiene comodin Verde
            setVisibilityGreenWildCard(test);
            // Si tiene comodin Ambar
            setVisibilityAmberWildCard(test);
        }

        /**
         * Metodo encargado de mostrar o ocultar el comodin verde.
         *
         * @param test, test.
         */
        private void setVisibilityGreenWildCard(Test test) {
            List<WildCard> greenWildCard = testActivity.greenWildCard;
            Iterator<WildCard> iter = greenWildCard.iterator();
            boolean flag = false;
            while (iter.hasNext()) {
                if (iter.next().getIdQuestion() == test.getIdQuestion()) {
                    flag = true;
                    this.imageViewGreenWildCard.setVisibility(View.VISIBLE);
                    break;
                }
            }
            if (flag == false) {
                this.imageViewGreenWildCard.setVisibility(View.GONE);
            }
        }

        /**
         * Metodo encargado de mostrar o ocultar el comodin ambar.
         *
         * @param test, test.
         */
        private void setVisibilityAmberWildCard(Test test) {
            Set<Integer> amberWildCard = testActivity.amberWildCard.keySet();
            Iterator<Integer> iter = amberWildCard.iterator();
            boolean flag = false;
            while (iter.hasNext()) {
                if (iter.next() == test.getIdQuestion()) {
                    flag = true;
                    this.imageViewAmberWildCard.setVisibility(View.VISIBLE);
                    break;
                }
            }
            if (flag == false) {
                this.imageViewAmberWildCard.setVisibility(View.GONE);
            }
        }

        /**
         * Metodo encargado de crear radio button.
         *
         * @param title,    title.
         * @param idButton, id.
         * @return RadioButton, newRadioButton.
         */
        private RadioButton createRadioButton(String title, int idButton) {

            RadioButton newRadioButton = new RadioButton(context);
            LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            newRadioButton.setLayoutParams(params);
            newRadioButton.setText(title);
            newRadioButton.setTag(title);
            newRadioButton.setId(idButton);

            if (flags.containsKey(getAdapterPosition()) == true && answers.get(getAdapterPosition()) == idButton) {
                int position = answers.get(getAdapterPosition());
                newRadioButton.setChecked(true);
                Log.d("Position : " + position, "Btn: " + idButton);
            }

            return newRadioButton;
        }

        /**
         * Metodo encargado de pintar aquella respuesta de una answer al usar el comodin verde.
         *
         * @param radioGroup, radioGroup.
         * @param test,       test.
         */
        private void checkWildCard(RadioGroup radioGroup, Test test) {
            String type = test.getWildCard();

            if (type != "") {
                if (type == "bg-success") {
                    WildCard wildCard = getAnswersByIdQuestion(test);
                    if (wildCard != null) {
                        int idAnswer = wildCard.getIdAnswer();
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            View view = radioGroup.getChildAt(i);
                            if (view.getId() == idAnswer) {
                                view.setBackgroundResource(R.color.colorGreenWildCard);
                            }
                        }
                    }
                } else if (type == "bg-warning") {
                    HashMap<Integer, HashSet<Integer>> amberWildCard = new TestActivity().amberWildCard;
                    int idAnswer = test.getIdQuestion();
                    if (amberWildCard.containsKey(idAnswer)) {
                        HashSet<Integer> amberRespuestas = amberWildCard.get(idAnswer);
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            View view = radioGroup.getChildAt(i);
                            if (amberRespuestas.contains(view.getId())) {
                                radioGroup.getChildAt(i).setBackgroundResource(R.color.colorAmberWildCard);
                            }
                        }
                    }
                }


            }
        }

        /**
         * Metodo que proporciona las answers de una answer que tienen comodion verde.
         *
         * @param test, test.
         * @return WildCard, wildCard.
         */
        private WildCard getAnswersByIdQuestion(Test test) {
            WildCard retorno = null;
            // Obtenemos la informacion sobre que preguntas tiene comodin verde
            List<WildCard> greenWildCard = testActivity.greenWildCard;
            for (WildCard wildCard : greenWildCard) {
                if (wildCard.getIdQuestion() == test.getIdQuestion()) {
                    retorno = wildCard;
                    break;
                }
            }
            return retorno;
        }


    }

    /**
     * Interfaz OnItemClickListener encargada de la logica al seleccionar un oomodin.
     *
     * @author Daniel Puente Gabarri.
     */
    public interface OnItemClickListener {
        void onItemClick(Test test, int position);
    }

}
