package aplicacion.android.danielvm.quicktest_android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Adapters.CuestionarioAdapter;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.IResult;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.VolleyService;
import aplicacion.android.danielvm.quicktest_android.Models.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.R;

public class CuestionarioFragment extends Fragment {

    // Elemento de la UI mediante RecyclerView

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Cuestionario> cuestionarios;
    private Gson gson = new Gson();

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;


    public CuestionarioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        cuestionarios = initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());
        mVolleyService.getDataVolley();

        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CuestionarioAdapter(this.cuestionarios, getActivity(), R.layout.recycler_view_item);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    private List<Cuestionario> initVolleyCallback() {
        final List<Cuestionario> resultado = new ArrayList<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                Cuestionario[] cuest = procesarRespuesta(response);
                for (Cuestionario c : cuest)
                    resultado.add(new Cuestionario(c.getIdCuestionario(), c.getNombreCuestionario(), R.mipmap.ic_icon_cuestionario));
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d("", "Error Volley" + error.getMessage());
            }
        };
        return resultado;
    }

    private Cuestionario[] procesarRespuesta(JSONObject response) {
        Cuestionario[] retorno = null;
        try {
            String estado = response.getString("estado");
            switch (estado) {
                case "1": //EXITO
                    JSONArray mensaje = response.getJSONArray("mensaje");
                    retorno = gson.fromJson(mensaje.toString(), Cuestionario[].class);
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retorno;
    }


}
