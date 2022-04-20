package k1.simulaciones.simulacionestp3.controller.bondadAjuste;

import k1.simulaciones.simulacionestp3.modelo.Intervalo;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjuste;

import java.util.ArrayList;
import java.util.List;

public interface IPruebaChiCuadrado {

    /*
    * Es necesario:
    * Generar un listado de randoms uniformes con el generador elegido
    * Realizar el cambio de distribución de la serie de números, de uniforme 0-1 a la elegida.
    * Generar una distribución de frecuencias inicial para la nueva serie de números (Con PO, PE, PoAc, PeAc.
    * Realizar la prueba de chi cuadrado sobre la distribución generada en el punto anterior
    * Para realizar la prueba de chi cuadrado se debe primero reagrupar la distribución de frecuencias
    * para asegurar que en cada intervalo la Fe sea de 5 o más, usando el algoritmo.
    *
    * */
    /*
    * La distribución de frecuencias inicial, la que se recibe como parámetro en este método, se debe
    * generar directamente en la clase controlador que coordina to-do el trabajo práctico
    * */
    ResultadoBondadAjuste generarPruebaChiCuadrado(Intervalo[] distFrecuencia, ParametrosCambioDistribucion parametros);

    default List<Intervalo> calcularIntervalosChiCuadrado(Intervalo[] intervalos){

        float espAcumI = 0, acumJ = 0;
        int li = 0, obsAcumI=0;
        List<Intervalo> intervalosChiCuadrado = new ArrayList<>();
        boolean creaIntervalo;
        for(int i = 0; i < intervalos.length; i++){
            espAcumI += intervalos[i].getFrecEsp();
            obsAcumI += intervalos[i].getFrecObs();
            if(espAcumI >= 5){
                int j;
                if(i+1!=intervalos.length){
                    creaIntervalo = false;
                    for(j=i+1; j< intervalos.length; j++){
                        acumJ+= intervalos[j].getFrecEsp();
                        if(acumJ >= 5){
                            creaIntervalo = true;
                            break;
                        }
                    }
                }else creaIntervalo=true;
                if(creaIntervalo){
                    Intervalo intervalo = new Intervalo();
                    intervalo.setLimInf(intervalos[li].getLimInf());
                    intervalo.setLimSup(intervalos[i].getLimSup());
                    intervalo.setFrecEsp(espAcumI);
                    intervalo.setFrecObs(obsAcumI);
                    intervalosChiCuadrado.add(intervalo);
                    espAcumI = 0;
                    obsAcumI = 0;
                    acumJ = 0;
                    li = i+1;
                }
            }

        }
        return intervalosChiCuadrado;
    }


}
