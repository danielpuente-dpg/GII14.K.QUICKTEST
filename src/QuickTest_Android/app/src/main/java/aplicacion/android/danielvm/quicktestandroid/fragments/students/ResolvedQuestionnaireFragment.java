package aplicacion.android.danielvm.quicktestandroid.fragments.students;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.activities.student.StudentActivity;
import aplicacion.android.danielvm.quicktestandroid.adapters.student.ResolvedExternalToolAdapter;
import aplicacion.android.danielvm.quicktestandroid.models.android.Questionnaire;
import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase ResolvedQuestionnaireFragment encargada de mostrar los cuestionarios resueltos.
 *
 * @author Daniel Puente Gabarri.
 */
public class ResolvedQuestionnaireFragment extends Fragment {


    // Elemento de la UI mediante RecyclerView

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Atributos
    private List<Questionnaire> resolvedQuestionnaires;

    public ResolvedQuestionnaireFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StudentActivity studentActivity = (StudentActivity) getActivity();
        resolvedQuestionnaires = studentActivity.getDataExternalToolsResolved();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resolved_questionnaire, container, false);


        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ResolvedExternalToolAdapter(this.resolvedQuestionnaires, getActivity(), R.layout.recycler_view_item);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
