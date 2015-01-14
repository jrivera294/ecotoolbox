package com.ucab.ecotoolbox.ecotoolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Jose Gabriel on 18/12/2014.
 */
public class TotalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        View v = inflater.inflate(R.layout.calc_fragment_total, container,false);



        // CALCULAR TAB TRANSPORTE

          Double[][]  emisionCarro = {{0.15991,0.20018,0.28944},{0.14519,0.17438,0.22867},{0.11156,0.11901,0.19755}};
          Double[] emisionMoto = {0.08499,0.10316,0.13724};
          Double emisionTaxi = 0.17623;
          Double emisionBus = 0.10067;
          Double emisionMetro = 0.035645;

        EditText etCarro = (EditText)this.getActivity().findViewById(R.id.etCarro);
        Spinner spCombustible = (Spinner)this.getActivity().findViewById(R.id.spCombustible);
        Spinner spTamCarro= (Spinner)this.getActivity().findViewById(R.id.spTamCarro);
        EditText etMoto = (EditText)this.getActivity().findViewById(R.id.etMotos);
        Spinner spTamMoto =  (Spinner)this.getActivity().findViewById(R.id.spTamMoto);
        EditText etTaxi= (EditText)this.getActivity().findViewById(R.id.etTaxi);
        EditText  etBus = (EditText)this.getActivity().findViewById(R.id.etBus);
        EditText etMetro = (EditText)this.getActivity().findViewById(R.id.etMetro);
        TextView tvResultado = (TextView)this.getActivity().findViewById(R.id.tvResultado);


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

        Double valorTransporte =  valorCarro* emisionCarro[spCombustible.getSelectedItemPosition()][spTamCarro.getSelectedItemPosition()] +
                valorMoto * emisionMoto[spTamMoto.getSelectedItemPosition()] +
                valorBus * emisionBus +
                valorTaxi * emisionTaxi +
                valorMetro * emisionMetro;

       // tvResultado.setText(res.toString());

        // TODO Auto-generated method stub

        //CALCULAR EL VALOR DEL HOGAR
        EditText etConsumoHogar = (EditText)this.getActivity().findViewById(R.id.etConsumoElectrico);
        String StringHogar = etConsumoHogar.getText().toString();
        Double valorHogar;
        Double emisionHogar = 0.5;

        if (StringHogar.equals("")){
            valorHogar = 0.0;
        }
        else
            valorHogar = Double.parseDouble(StringHogar) * emisionHogar;

        TextView tvEmision = (TextView)v.findViewById(R.id.tvEmisionTotal);
       Double valorTotal = valorHogar + valorTransporte;
        DecimalFormat format = new DecimalFormat("0.000");

        tvEmision.setText("Su emision es de: "+format.format(valorTotal).toString()+"Kg de C02");
        return v;
    }


}