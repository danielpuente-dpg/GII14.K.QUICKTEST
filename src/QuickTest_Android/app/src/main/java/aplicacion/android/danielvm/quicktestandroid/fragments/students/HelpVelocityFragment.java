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
import aplicacion.android.danielvm.quicktestandroid.models.android.Help;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpVelocityFragment extends Fragment {

    // Elementos de la UI mediante RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Atributos
    private List<Help> helps;


    public HelpVelocityFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Obtenemos la info
        this.helps = loadHelp();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help_velocity, container, false);

        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHelpVelocityFragment);
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
            add(new Help(R.mipmap.ic_number_one, "MUY RÁPIDO",
                    "Si estás en el grupo de los MÁS RÁPIDOS, a tu puntuación final, le sumaremos el 10% de la puntuación máxima"));
            add(new Help(R.mipmap.ic_number_two, "RÁPIDO",
                    "Si estás en el grupo de los RÁPIDOS, a tu puntuación final, le sumaremos el 5% de la puntuación máxima"));
            add(new Help(R.mipmap.ic_number_three, "LENTO",
                    "Si estás en el grupo mas LENTO, a tu puntuación final, le restaremos el -5% de la puntuación máxima"));
        }};
    }

}
