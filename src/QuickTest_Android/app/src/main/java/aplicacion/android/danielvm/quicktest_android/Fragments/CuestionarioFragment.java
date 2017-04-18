package aplicacion.android.danielvm.quicktest_android.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Activities.LoginActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Adapters.CuestionarioAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Request.APIConstants;
import aplicacion.android.danielvm.quicktest_android.Request.RESTService;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.IResult;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.VolleyService;

public class CuestionarioFragment extends Fragment {

    // Elemento de la UI mediante RecyclerView

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Cuestionario> cuestionarios;
    private List<ExternalTool> externaltools;

    private Gson gson = new Gson();

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;
    private IResult mResultCallback2 = null;
    private VolleyService mVolleyService2;

    public ExternalTool ets;

    public int NUM_EXTERNAL_TOOLS;


    public CuestionarioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        /////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////
        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        //mAdapter = new CuestionarioAdapter(this.cuestionarios, getActivity(), R.layout.recycler_view_item);
        mAdapter = new CuestionarioAdapter(this.cuestionarios, getActivity(), R.layout.recycler_view_item);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }


    private Cuestionario parseExternalToolToQuestionary(JSONObject response) {
        Cuestionario cuestionario = null;

        Gson gson = new GsonBuilder().create();
        ExternalTool externalTool = gson.fromJson(response.toString(), ExternalTool.class);

        if (externalTool.getEndpoint().equals("http://localhost/_QuickTest_TFG/index.php")) {
            // Obtenemos la descripcion
            String description = externalTool.getParameters().get(10).getValue();
            // Obtenemos el Id cuestionario
            String idCuestionario = externalTool.getParameters().get(11).getValue();

            cuestionario = new Cuestionario(idCuestionario, description, R.mipmap.ic_icon_cuestionario);
        }

        return cuestionario;
    }

    private List<Cuestionario> getAndSetExternalTool() {
        final List<Cuestionario> resultado = new ArrayList<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                resultado.add(parseExternalToolToQuestionary(response));
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d("", "Error Volley" + error.getMessage());
            }
        };
        return resultado;
    }


    private int getNumberExternalTools(){
        return 4;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cuestionarios = getAndSetExternalTool();
        mVolleyService = new VolleyService(mResultCallback, getContext());
        mVolleyService.getAndAddCuestionarios(new MainActivity().token, 1, getNumberExternalTools());

    }


}
