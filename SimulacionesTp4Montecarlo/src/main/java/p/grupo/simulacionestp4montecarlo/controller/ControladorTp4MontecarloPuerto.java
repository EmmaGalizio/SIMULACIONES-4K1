package p.grupo.simulacionestp4montecarlo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import p.grupo.simulacionestp4montecarlo.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.simulacionestp4montecarlo.controller.generadorRandom.IGeneradorRandom;
import p.grupo.simulacionestp4montecarlo.controller.utils.ConstantesCambioDistribucion;
import p.grupo.simulacionestp4montecarlo.modelo.*;
import p.grupo.simulacionestp4montecarlo.modelo.montecarlo.VectorEstadoMontecarloPuerto;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ControladorTp4MontecarloPuerto {

    private final Map<String, IGeneradorRandom> generadorRandomMap;
    private final Map<String, ICambioDistribucion> cambioDistribucionMap;


    public List<VectorEstadoMontecarloPuerto> generarSimulacionEstActual(
            ParametrosCambioDistribucion parametrosCostoDesc,
            ParametrosGenerador[] parametrosGenerador, ParametrosMontecarlo parametrosMontecarlo){

        if(parametrosGenerador == null || parametrosGenerador.length != 3)
            throw new IllegalArgumentException("Se deben utilizar 3 generadores random uniformes 0-1" +
                    " para la simulación");
        if(parametrosCostoDesc == null)
            throw new IllegalArgumentException("Debe indicar los parametros para optener los costos de descarga");

        if(parametrosCostoDesc.getDesvEst() <= 0) parametrosCostoDesc.setDesvEst(120);
        if(parametrosCostoDesc.getMedia() < 0) parametrosCostoDesc.setMedia(800);

        float[] nroLlegadas = {0.13f,0.3f,0.45f,0.7f,0.9f,1 };
        float[] nroDescargas = {0.05f, 0.2f,0.7f,0.9f,1};

        ParametrosGenerador parametrosGeneradorArribos = parametrosGenerador[0];
        ParametrosGenerador parametrosGeneradorDescargas = parametrosGenerador[1];
        ParametrosGenerador parametrosGeneradorCostoDesc = parametrosGenerador[2];

        IGeneradorRandom generadorRandomArribos = generadorRandomMap
                .get(parametrosGeneradorArribos.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomDescargas = generadorRandomMap
                .get(parametrosGeneradorDescargas.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomCostoDesc = generadorRandomMap
                .get(parametrosGeneradorCostoDesc.getMetodoGeneradorRandom());
        ICambioDistribucion generadorCostoDescargaNormal = cambioDistribucionMap
                .get(ConstantesCambioDistribucion.NORMAL_CONVOLUCION);

        Pseudoaleatorio randomArribos = generadorRandomArribos
                .siguientePseudoAleatoreo(parametrosGeneradorArribos);
        Pseudoaleatorio randomDescargas = generadorRandomDescargas
                .siguientePseudoAleatoreo(parametrosGeneradorDescargas);
        Pseudoaleatorio randomCostoDescarga = generadorRandomCostoDesc
                            .siguientePseudoAleatoreo(parametrosGeneradorCostoDesc);
        VaribaleAleatoria costoDescargaNormalRnd = generadorCostoDescargaNormal
                .siguienteRandom(parametrosCostoDesc,parametrosGeneradorCostoDesc,randomCostoDescarga);
        //Representa la fila anterior de la tabla de montecarlo, en un primer instante
        //representa el día 0
        VectorEstadoMontecarloPuerto estadoAnterior = new VectorEstadoMontecarloPuerto();
        VectorEstadoMontecarloPuerto estadoMontecarloPuerto;

        long filaMaximaObservar = parametrosMontecarlo.getCantFilasMostrar()
                                    +parametrosMontecarlo.getMostrarVectorDesde();
        List<VectorEstadoMontecarloPuerto> tablaMontecarlo = new LinkedList<>();
        for(long i = 1; i <= parametrosMontecarlo.getN(); i++){
             estadoMontecarloPuerto = new VectorEstadoMontecarloPuerto();
             //Copia los campos acumulados del vector anterior para poder acumularlos al final
             copiarAcumuladosAnterioresUnMuelle(estadoMontecarloPuerto,estadoAnterior);
             //Empieza a cargar y calcular los campos del día actual
             estadoMontecarloPuerto.setDia(i);
             estadoMontecarloPuerto.setRandomIngresos(randomArribos.getRandom());
             estadoMontecarloPuerto.setRandomDescargas(randomDescargas.getRandom());
             estadoMontecarloPuerto.setCantIngresos(obtenerNroLlegadasActual(nroLlegadas,randomArribos));
             estadoMontecarloPuerto
                     .setCantActualMuelle(estadoAnterior.getCantDescargasPostergadas()
                             +estadoMontecarloPuerto.getCantIngresos());
             int cantBarcosDescargados = obtenerNroDescargasActual(nroDescargas,randomDescargas);
             //Estará bien este planteo con probabilidades independientes entre llegadas y descargas?
            //Haciendo que si la cantidad de descargas es mayor a la cantidad de barcos en el mulle
            //entonces la cantidad de descargas es igual a la cantidad de barcos??
             cantBarcosDescargados = Math
                     .min(cantBarcosDescargados, estadoMontecarloPuerto.getCantActualMuelle());
             estadoMontecarloPuerto.setCantDescargas(cantBarcosDescargados);
             estadoMontecarloPuerto
                     .setCantDescargasPostergadas(estadoMontecarloPuerto.getCantActualMuelle()-cantBarcosDescargados);
             float costoDescargas = 0;

             for(int j = 1 ; j<= cantBarcosDescargados;j++){
                 //Usa el random generado por adelantado
                 costoDescargas+=costoDescargaNormalRnd.getRandomGenerado();
                 randomCostoDescarga = costoDescargaNormalRnd.getSiguienteRandomBase();
                 //Actualiza el random generado
                 costoDescargaNormalRnd = generadorCostoDescargaNormal.siguienteRandom(parametrosCostoDesc,
                         parametrosGeneradorCostoDesc,randomCostoDescarga);
             }
             estadoMontecarloPuerto.setCostoDescarga(costoDescargas);
             acumularCamposEstadoActualUnMuelle(estadoMontecarloPuerto);

             //Agrega fila actual a la tabla si corresponde
            //Agrega las filas del rango solicitado y la última fila para sacar las conclusiones
            if((i >= parametrosMontecarlo.getMostrarVectorDesde() &&
                i < filaMaximaObservar) || i == parametrosMontecarlo.getN()){
                tablaMontecarlo.add(estadoMontecarloPuerto);
            }
            //Mantenimiento para comenzar la siguiente iteracion;
            //No es necesario actualizar randoms costo desc porque se actualiza en el loop
            estadoAnterior = estadoMontecarloPuerto;
            randomArribos = generadorRandomArribos.siguientePseudoAleatoreo(randomArribos,parametrosGeneradorArribos);
            randomDescargas = generadorRandomDescargas.siguientePseudoAleatoreo(randomDescargas,parametrosGeneradorDescargas);
        }

        return tablaMontecarlo;
    }
    private void copiarAcumuladosAnterioresUnMuelle(VectorEstadoMontecarloPuerto estadoMontecarloPuerto,
                                                    VectorEstadoMontecarloPuerto estadoAnterior){
        estadoMontecarloPuerto.setCostoAcumulado(estadoAnterior.getCostoAcumulado());
        estadoMontecarloPuerto.setCostoDemoraAcumulado(estadoAnterior.getCostoDemoraAcumulado());
        estadoMontecarloPuerto.setDiasDemoraAcumulada(estadoAnterior.getDiasDemoraAcumulada());
        estadoMontecarloPuerto.setBarcosPostergadosAcum(estadoAnterior.getBarcosPostergadosAcum());
    }
    private void acumularCamposEstadoActualUnMuelle(VectorEstadoMontecarloPuerto estadoMontecarloPuerto){
        estadoMontecarloPuerto.acumularBarcosPostergados();
        estadoMontecarloPuerto.calcularCostoDemora();
        estadoMontecarloPuerto.calcularCostoTotalDia();
        estadoMontecarloPuerto.acumularCostoTotalDiario();
        estadoMontecarloPuerto.acumularCostoDemora();
        estadoMontecarloPuerto.acumularDiasDemora();
    }

    private int obtenerNroLlegadasActual(float[] nroLlegadas, Pseudoaleatorio randomLlegadas){
        for(int i = 0; i < nroLlegadas.length; i++){
            if(randomLlegadas.getRandom() < nroLlegadas[i])
                return i;
        }
        return -1;
    }
    private int obtenerNroDescargasActual(float[] nroDescargas, Pseudoaleatorio randomDescargas){
        for(int i = 0; i < nroDescargas.length; i++){
            if(randomDescargas.getRandom() < nroDescargas[i])
                return i+1;
        }
        return -1;

    }

}
