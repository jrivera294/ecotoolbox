package com.ucab.ecotoolbox.ecotoolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransporteFragment extends Fragment {

     private static Double[][]  emisionCarro = {{0.15991,0.20018,0.28944},{0.14519,0.17438,0.22867},{0.11156,0.11901,0.19755}};
     private static Double[] emisionMoto = {0.08499,0.10316,0.13724};
     private static Double emisionTaxi = 0.17623;
     private static Double emisionBus = 0.10067;
    private static Double emisionMetro = 0.035645;
    private Button bc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.calc_fragment_transporte, container,false);
        this.getActivity().getIntent().putExtra("key","value");
        //setRetainInstance(true);
//       bc = (Button)view.findViewById(R.id.bCalcular);
//        bc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calcularTransporte(view);
//            }
//        });
//        calcularTransporte(view);
        return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        EditText etCarro = (EditText)this.getView().findViewById(R.id.etCarro);
//        String StringCarro = etCarro.getText().toString();
//        Toast.makeText(this.getActivity().getApplicationContext(), "es onResume y el valor del carro es: "+StringCarro,
//                Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        EditText etCarro = (EditText)this.getView().findViewById(R.id.etCarro);
//        String StringCarro = etCarro.getText().toString();
//        Toast.makeText(this.getActivity().getApplicationContext(), "es onPAUSE y el valor del carro es: "+StringCarro,
//                Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        EditText etCarro = (EditText)this.getView().findViewById(R.id.etCarro);
//        String StringCarro = etCarro.getText().toString();
//        Toast.makeText(this.getActivity().getApplicationContext(), "es onSTOP y el valor del carro es: "+StringCarro,
//                Toast.LENGTH_LONG).show();
//    }
    public Double calcularTransporte() {
        EditText etCarro = (EditText)this.getView().findViewById(R.id.etCarro);
        Spinner spCombustible = (Spinner)this.getView().findViewById(R.id.spCombustible);
        Spinner spTamCarro= (Spinner)this.getView().findViewById(R.id.spTamCarro);
        EditText etMoto = (EditText)this.getView().findViewById(R.id.etMotos);
        Spinner spTamMoto =  (Spinner)this.getView().findViewById(R.id.spTamMoto);
        EditText etTaxi= (EditText)this.getView().findViewById(R.id.etTaxi);
        EditText  etBus = (EditText)this.getView().findViewById(R.id.etBus);
        EditText etMetro = (EditText)this.getView().findViewById(R.id.etMetro);
        TextView tvResultado = (TextView)this.getView().findViewById(R.id.tvResultado);


        String StringCarro = etCarro.getText().toString();
        String StringMoto = etMoto.getText().toString();
        String StringBus = etBus.getText().toString();
        String StringTaxi= etTaxi.getText().toString();
        String StringMetro = etMetro.getText().toString();
        Double valorCarro;
        Double valorMoto;
        Double valorBus;
        Double valorTaxi;
        Double valorMetro;
        if (StringCarro.equals(""))
             valorCarro = 0.0;
        else
              valorCarro = Double.parseDouble(StringCarro);

        if (StringMoto.equals(""))
            valorMoto = 0.0;
        else
            valorMoto = Double.parseDouble(StringMoto);

        if (StringTaxi.equals(""))
            valorTaxi = 0.0;
        else
            valorTaxi = Double.parseDouble(StringTaxi);

        if (StringBus.equals(""))
            valorBus = 0.0;
        else
            valorBus = Double.parseDouble(StringBus);

        if (StringMetro.equals(""))
            valorMetro = 0.0;
        else
            valorMetro = Double.parseDouble(StringMetro);

       Double res =  valorCarro* emisionCarro[spCombustible.getSelectedItemPosition()][spTamCarro.getSelectedItemPosition()] +
                     valorMoto * emisionMoto[spTamMoto.getSelectedItemPosition()] +
                     valorBus * emisionBus +
                     valorTaxi * emisionTaxi +
                     valorMetro * emisionMetro;


        return res;
//        Toast.makeText(getActivity().getApplicationContext(), "EPALEEE: "+res.toString(),
//                Toast.LENGTH_LONG).show();

        //   this.getParentFragment().getId()
       // tvResultado.setText(res.toString());
       // etBus.setText("1");
       // etCarro.setText("2");
    }
}
