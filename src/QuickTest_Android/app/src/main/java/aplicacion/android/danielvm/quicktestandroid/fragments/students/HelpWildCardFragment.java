package aplicacion.android.danielvm.quicktestandroid.fragments.students;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.R;
import aplicacion.android.danielvm.quicktestandroid.adapters.student.HelpFragmentAdapter;
import aplicacion.android.danielvm.quicktestandroid.adapters.student.ResolvedExternalToolAdapter;
import aplicacion.android.danielvm.quicktestandroid.models.android.Help;

/**
 * Clase HelpWildCardFragment encargada de mostrar una ayuda sobre el funcionamiento de los comodines
 */
public class HelpWildCardFragment extends Fragment {

    // Elementos de la UI mediante RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Atributos
    private List<Help> helps;


    public HelpWildCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Obtenemos la info
        this.helps = loadHelp();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHelpFragment);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new HelpFragmentAdapter(this.helps, getActivity(), R.layout.recycler_view_item_help_fragment);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    private List<Help> loadHelp() {
        return new ArrayList<Help>(){{
            add(new Help(R.mipmap.ic_green_wildcard, "Comodín verde",
                    "¿Dudas MUCHO de tu respuesta?,¿Prefieres asegurarte?, La penalización será elevada."));
            add(new Help(R.mipmap.ic_amber_wildcard, "Comodín ambar",
                    "¿CASI saber la respuesta?, Obtén una solución incierta y despeja dudas, A cambio, la penalización será leve."));
            add(new Help(R.mipmap.ic_no_wildcard, "Sin comodín",
                    "¿Estas SEGURO de tu respuesta?, Genial no uses comodín, Tendras la puntuación máxima."));
        }};
    }

}
