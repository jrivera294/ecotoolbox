package com.ucab.ecotoolbox.ecotoolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Jose Gabriel on 18/12/2014.
 */
public class TotalFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        View v = inflater.inflate(R.layout.calc_fragment_total, container,false);
        //setRetainInstance(true);
        // CALCULAR TAB TRANSPORTE
//       String value =this.getActivity().getIntent().getStringExtra("key");
//        Toast.makeText(this.getActivity().getApplicationContext(), "el valor del carro es INTENT : " + value,
//                Toast.LENGTH_LONG).show();

        return v;
    }

    public void calcular (int spTipoCalculoIndex) {
        int multiplicadorDias;

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

        switch (spTipoCalculoIndex){
            case 0:
                multiplicadorDias = 1;
                break;
            case 1:
                multiplicadorDias = 7;
                break;
            case 2:
                multiplicadorDias = 30;
                break;
            case 3:
                multiplicadorDias = 365;
                break;
            default:
                multiplicadorDias = 1;
        }

        TextView tvEmision = (TextView)this.getView().findViewById(R.id.tvEmisionTotal);
        Double valorTotal = valorHogar + valorTransporte;
        DecimalFormat format = new DecimalFormat("0.000");

        tvEmision.setText("Su emision es de: "+format.format(valorTotal).toString()+"Kg de C02");

        ImageView ivHuella = (ImageView)this.getView().findViewById(R.id.ivHuella);
        TextView tvRecomendacion = (TextView)this.getView().findViewById(R.id.tvRecomendacion);

        Spinner spTipoCalculo = (Spinner)this.getActivity().findViewById(R.id.spTipoCalculo);
        spTipoCalculo.setOnItemSelectedListener(this);
        spTipoCalculo.setSelection(spTipoCalculoIndex);

        Log.d("valorTotal:",""+valorTotal);
        Log.d("multiplicadorDias:",""+multiplicadorDias);
        if(valorTotal<(6*multiplicadorDias)){
            ivHuella.setImageResource(R.drawable.huella_verde);
            tvRecomendacion.setText("Felicidades, tus niveles de emisión de co2 son muy buenos. Sigue así.");
        }else if(valorTotal<(14*multiplicadorDias)){
            ivHuella.setImageResource(R.drawable.huella_amarilla);
            tvRecomendacion.setText("No te asustes, el color amarillo no es tan malo, tus niveles de emisión de co2 se encuentran por debajo del promedio mundial, pero aún puedes mejorar, ");
        }else if(valorTotal<(22*multiplicadorDias)){
            ivHuella.setImageResource(R.drawable.huella_naranja);
            tvRecomendacion.setText("Tus niveles de emisión de co2 se encuentran dentro del promedio mundial, nuestro planeta necesita que bajemos ese promedio. Trata de utilizar medios de transporte más ecológicos y disminuir tu consumo energético en el hogar.");
        }else if(valorTotal<(30*multiplicadorDias)){
            ivHuella.setImageResource(R.drawable.huella_roja);
            tvRecomendacion.setText("Tus niveles de emisión de co2 se encuentran muy por encima del promedio, te te recomendamos que disminuyas tus niveles de consumo energético en el hogar o trata de utilizar más frecuentemente medios de transporte más ecológicos.");
        }else if(valorTotal>(30*multiplicadorDias)){
            ivHuella.setImageResource(R.drawable.huella_negra);
            tvRecomendacion.setText("Vaya! Tus niveles de emisión de co2 están muy elevados, estás seguro que ingresaste los datos correctamente? De no ser así, te recomendamos que disminuyas tus niveles de consumo energético en el hogar o trata de utilizar más frecuentemente medios de transporte más ecológicos.");
        }else{
            ivHuella.setImageResource(R.drawable.huella_negra);
            tvRecomendacion.setText("Estás seguro de que estás emitiendo valores negativos de co2? Eso es muy extraño.");
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        this.calcular(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}