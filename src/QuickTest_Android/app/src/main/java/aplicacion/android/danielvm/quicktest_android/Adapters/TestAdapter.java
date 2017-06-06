package aplicacion.android.danielvm.quicktest_android.Adapters;


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
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Activities.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.TestActivity;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Result;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 23/03/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<Test> tests;
    private int layout;
    private Context context;

    private OnItemClickListener listenerGreenWildCard;
    private OnItemClickListener listenerAmberWildCard;

    public static HashMap<Integer, Integer> respuestas = new HashMap();
    public static HashMap<Integer, Boolean> flags = new HashMap<>();
    public static HashMap<Integer, Result> postTest = new HashMap();

    public TestAdapter(List<Test> tests, int layout, OnItemClickListener listenerGreenWildCard, OnItemClickListener listenerAmberWildCard) {
        this.tests = tests;
        this.layout = layout;
        this.listenerGreenWildCard = listenerGreenWildCard;
        this.listenerAmberWildCard = listenerAmberWildCard;
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
        // Llamamos la metodo encargado de añadir los datos propios de cada cuestionario
        holder.dataBind(this.tests.get(position), position, this.listenerGreenWildCard, this.listenerAmberWildCard);
    }

    @Override
    public int getItemCount() {
        if (tests != null)
            return tests.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // Elementos que forman parte de la UI del CuestionarioFragment

        private TextView pregunta;
        private RadioGroup radioGroup;
        private ImageView imageViewGreenWildCard;
        private ImageView imageViewAmberWildCard;

        public ViewHolder(View view) {
            super(view);

            this.pregunta = (TextView) view.findViewById(R.id.textViewStatement);
            this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            this.imageViewGreenWildCard = (ImageView) view.findViewById(R.id.imageViewGreenWildCard);
            this.imageViewAmberWildCard = (ImageView) view.findViewById(R.id.imageViewAmberWildCard);

        }

        public void dataBind(final Test test, final int position, final OnItemClickListener listenerGreenWildCard, final OnItemClickListener listenerAmberWildCard) {

            radioGroup.removeAllViews();
            this.pregunta.setText((position + 1) + " - " + test.getPregunta());
            for (Respuesta r : test.getRespuestas()) {
                RadioButton nuevoRadio = crearRadioButton(r.getTitulo(), r.getIdRespuesta());
                radioGroup.addView(nuevoRadio);
            }

            hasWildCard(test);


            checkWildCard(radioGroup, test);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    respuestas.put(getAdapterPosition(), checkedId);
                    flags.put(getAdapterPosition(), true);
                    Log.d("Listener->Position : " + getAdapterPosition(), "Btn: " + checkedId);


                    int idPregunta = test.getIdPregunta();
                    int idRespuesta = test.getRespuestas().get(checkedId).getIdRespuesta();
                    String tipoComUsado = "";
                    String idAlumno = new TestActivity().CLAVE + ":" + new MainActivity().user.getId();

                    Result result = new Result(idPregunta, idRespuesta, tipoComUsado, idAlumno);
                    postTest.put(getAdapterPosition(), result);
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

        private void hasWildCard(Test test) {

            // Verde
            setVisibilityGreenWildCard(test);

            // Ambar

        }

        private void setVisibilityGreenWildCard(Test test) {
            List<WildCard> greenWildCard = new TestActivity().greenWildCard;
            Iterator<WildCard> iter = greenWildCard.iterator();
            boolean flag = false;
            while (iter.hasNext()) {
                if (iter.next().getPregunta_idPregunta() == test.getIdPregunta()) {
                    float valor = 1;
                    this.imageViewGreenWildCard.setAlpha(valor);
                    flag = true;
                    break;
                }

            }
            if (flag == false) {
                float valor = 0.3f;
                this.imageViewGreenWildCard.setAlpha(valor);
            }
        }

        private RadioButton crearRadioButton(String marca, int i) {

            RadioButton nuevoRadio = new RadioButton(context);
            LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            nuevoRadio.setLayoutParams(params);
            nuevoRadio.setText(marca);
            nuevoRadio.setTag(marca);
            nuevoRadio.setId(i);

            if (flags.containsKey(getAdapterPosition()) == true && respuestas.get(getAdapterPosition()) == i) {
                int id = respuestas.get(getAdapterPosition());
                Log.d("Position : " + i, "Btn: " + id);
                nuevoRadio.setChecked(true);
            }

            return nuevoRadio;
        }

        private void checkWildCard(RadioGroup radioGroup, Test test) {
            String type = test.getComodin();

            if (type != "") {

                WildCard wildCard = getRespuestaByIdPregunta(test);
                if (wildCard != null) {
                    int idRespuesta = wildCard.getIdRespuesta();
                    if (type == "verde") {
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            View view = radioGroup.getChildAt(i);
                            if (view.getId() == idRespuesta) {
                                view.setBackgroundResource(R.color.colorGreenWildCard);
                            }
                        }
                        //radioGroup.getChildAt(0).setBackgroundResource(R.color.colorGreenWildCard);
                    } else if (type == "ambar") {
                        radioGroup.getChildAt(0).setBackgroundResource(R.color.colorAmberWildCard);
                    }
                }

            }


        }

        private WildCard getRespuestaByIdPregunta(Test test) {
            WildCard retorno = null;
            // Obtenemos la informacion sobre que preguntas tiene comodin verde
            List<WildCard> greenWildCard = new TestActivity().greenWildCard;
            for (WildCard wildCard : greenWildCard) {
                if (wildCard.getPregunta_idPregunta() == test.getIdPregunta()) {
                    retorno = wildCard;
                    break;
                }
            }
            return retorno;
        }


    }

    public interface OnItemClickListener {
        void onItemClick(Test test, int position);
    }


    /***
     *
     *
     * for(int i = 0; i < greenWildCard.size(); i++){
     if(greenWildCard.get(i).getPregunta_idPregunta() == Integer.parseInt(test.getPregunta())){


     }
     }
     */
}
