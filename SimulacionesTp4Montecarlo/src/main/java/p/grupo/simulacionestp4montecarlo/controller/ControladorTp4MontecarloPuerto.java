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

    //CAMBIAR TODOS LOS FLOATS DE LOS COSTOS Y ACUMULADORES POR DOUBLE
    public List<VectorEstadoMontecarloPuerto> generarSimulacionEstActual(
            ParametrosCambioDistribucion parametrosCostoDesc,
            ParametrosGenerador parametrosGeneradorArribos,
            ParametrosGenerador parametrosGeneradorDescargas,
            ParametrosGenerador parametrosGeneradorCostoDesc, ParametrosMontecarlo parametrosMontecarlo){

        if(parametrosCostoDesc == null)
            throw new IllegalArgumentException("Debe indicar los parametros para optener los costos de descarga");

        if(parametrosCostoDesc.getDesvEst() <= 0) parametrosCostoDesc.setDesvEst(120);
        if(parametrosCostoDesc.getMedia() < 0) parametrosCostoDesc.setMedia(800);

        float[] nroLlegadas = {0.13f,0.3f,0.45f,0.7f,0.9f,1 };
        float[] nroDescargas = {0.05f, 0.2f,0.7f,0.9f,1};
        //Obtiene los generadores random uniformes 0-1 para ingresos, descargas y costo de descarga, cada uno necesita
        //Una serie independiente
        IGeneradorRandom generadorRandomArribos = generadorRandomMap
                .get(parametrosGeneradorArribos.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomDescargas = generadorRandomMap
                .get(parametrosGeneradorDescargas.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomCostoDesc = generadorRandomMap
                .get(parametrosGeneradorCostoDesc.getMetodoGeneradorRandom());
        //Obtiene el generador random normal por método de convolución para calcular el costo de descarga
        ICambioDistribucion generadorCostoDescargaNormal = cambioDistribucionMap
                .get(ConstantesCambioDistribucion.NORMAL_CONVOLUCION);
        //Randoms para los primeros arribos y descargas
        Pseudoaleatorio randomArribos = generadorRandomArribos
                .siguientePseudoAleatoreo(parametrosGeneradorArribos);
        Pseudoaleatorio randomDescargas = generadorRandomDescargas
                .siguientePseudoAleatoreo(parametrosGeneradorDescargas);
        Pseudoaleatorio randomCostoDescarga = generadorRandomCostoDesc
                .siguientePseudoAleatoreo(parametrosGeneradorCostoDesc);
        //Primer costo de descarga
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

            estadoMontecarloPuerto.calcularDescargasDirectas();
            estadoMontecarloPuerto.acumularDescargasDirectas();

            for(int j = 1 ; j<= cantBarcosDescargados;j++){
                //Usa el random generado por adelantado
                costoDescargas+=costoDescargaNormalRnd.getRandomGenerado();
                randomCostoDescarga = costoDescargaNormalRnd.getSiguienteRandomBase();
                //Actualiza el random generado para utilizarlo como siguiente costo
                costoDescargaNormalRnd = generadorCostoDescargaNormal.siguienteRandom(parametrosCostoDesc,
                        parametrosGeneradorCostoDesc,randomCostoDescarga);
            }
            estadoMontecarloPuerto.setCostoDescarga(costoDescargas);
            acumularCamposEstadoActualUnMuelle(estadoMontecarloPuerto);

            //Agrega fila actual a la tabla si corresponde
            //Agrega las filas del rango solicitado y la última fila para sacar las conclusiones
            if((i >= parametrosMontecarlo.getMostrarVectorDesde() &&
                    i < filaMaximaObservar) || i == parametrosMontecarlo.getN()){
                calcularResultadosParciales(estadoMontecarloPuerto);
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
    public List<VectorEstadoMontecarloPuerto> generarSimulacionDosPuertos(
            ParametrosCambioDistribucion parametrosCambioDistribuciones,
            ParametrosGenerador parametrosGeneradorArribos,
            ParametrosGenerador parametrosGeneradorDescargas,
            ParametrosGenerador parametrosGeneradorCostoDesc, ParametrosMontecarlo parametrosMontecarlo){

        if(parametrosCambioDistribuciones == null)
            throw new IllegalArgumentException("Debe indicar los parametros para optener los costos de descarga");

        if(parametrosCambioDistribuciones.getDesvEst() <= 0) parametrosCambioDistribuciones.setDesvEst(120);
        if(parametrosCambioDistribuciones.getMedia() < 0) parametrosCambioDistribuciones.setMedia(800);

        //comprobacion de a y b
        if(parametrosCambioDistribuciones.getUnifA() < 0 || parametrosCambioDistribuciones.getUnifB() < 0){
            parametrosCambioDistribuciones.setUnifA(0);
            parametrosCambioDistribuciones.setUnifB(9);
        }
        else{
            if(parametrosCambioDistribuciones.getUnifB() <= parametrosCambioDistribuciones.getUnifA()){
                parametrosCambioDistribuciones.setUnifB(parametrosCambioDistribuciones.getUnifA() + 10);
            }
        }

        //comprobacion de Lambda
        if(parametrosCambioDistribuciones.getLambda() <= 0 ) parametrosCambioDistribuciones.setLambda(2);


        //Obtiene los generadores random uniformes 0-1 para ingresos, descargas y costo de descarga, cada uno necesita
        //Una serie independiente
        IGeneradorRandom generadorRandomArribos = generadorRandomMap
                .get(parametrosGeneradorArribos.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomDescargas = generadorRandomMap
                .get(parametrosGeneradorDescargas.getMetodoGeneradorRandom());
        IGeneradorRandom generadorRandomCostoDesc = generadorRandomMap
                .get(parametrosGeneradorCostoDesc.getMetodoGeneradorRandom());
        //Obtiene el generador random normal por método de convolución para calcular el costo de descarga
        ICambioDistribucion generadorCostoDescargaNormal = cambioDistribucionMap
                .get(ConstantesCambioDistribucion.NORMAL_CONVOLUCION);
        //Obtiene el generador random uniforme A-B para calcular la cantidad de descargas
        ICambioDistribucion generadorLlegadasUniformeAB = cambioDistribucionMap
                .get(ConstantesCambioDistribucion.UNIFORME);
        //Obtiene el generador random Poisson para calcular la cantidad de llegadas
        ICambioDistribucion generadorDescargasPoisson = cambioDistribucionMap
                .get(ConstantesCambioDistribucion.POISSON);

        //Randoms para los primeros arribos y descargas
        Pseudoaleatorio randomArribos = generadorRandomArribos
                .siguientePseudoAleatoreo(parametrosGeneradorArribos);
        Pseudoaleatorio randomDescargas = generadorRandomDescargas
                .siguientePseudoAleatoreo(parametrosGeneradorDescargas);
        Pseudoaleatorio randomCostoDescarga = generadorRandomCostoDesc
                .siguientePseudoAleatoreo(parametrosGeneradorCostoDesc);
        //Primer costo de descarga
        VaribaleAleatoria costoDescargaNormalRnd = generadorCostoDescargaNormal
                .siguienteRandom(parametrosCambioDistribuciones,parametrosGeneradorCostoDesc,randomCostoDescarga);
        //Primer cantidad de llegadas
        VaribaleAleatoria llegadaUniformeABRnd = generadorLlegadasUniformeAB
                .siguienteRandom(parametrosCambioDistribuciones, parametrosGeneradorArribos, randomArribos);
        //Primer cantidad de descargas
        VaribaleAleatoria descargaPoisson = generadorDescargasPoisson
                .siguienteRandom(parametrosCambioDistribuciones, parametrosGeneradorDescargas, randomDescargas);

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
            //Obtiene el random con cambio de dist. a Uniforme A-B para las llegadas y setea.
            int cantLlegadas = (int) llegadaUniformeABRnd.getRandomGenerado();
            estadoMontecarloPuerto.setCantIngresos(cantLlegadas);
            estadoMontecarloPuerto
                    .setCantActualMuelle(estadoAnterior.getCantDescargasPostergadas()
                            +estadoMontecarloPuerto.getCantIngresos());
            //Renueva los randoms de llegada para la proxima iteracion
            randomArribos = llegadaUniformeABRnd.getSiguienteRandomBase();
            llegadaUniformeABRnd = generadorLlegadasUniformeAB.siguienteRandom(parametrosCambioDistribuciones,
                    parametrosGeneradorArribos, randomArribos);

            //Obtiene el random con cambio de dist. a Poisson para las descargas y setea.
            int cantBarcosDescargados = (int) descargaPoisson.getRandomGenerado();
            //Estará bien este planteo con probabilidades independientes entre llegadas y descargas?
            //Haciendo que si la cantidad de descargas es mayor a la cantidad de barcos en el mulle
            //entonces la cantidad de descargas es igual a la cantidad de barcos??
            cantBarcosDescargados = Math
                    .min(cantBarcosDescargados, estadoMontecarloPuerto.getCantActualMuelle());
            estadoMontecarloPuerto.setCantDescargas(cantBarcosDescargados);
            estadoMontecarloPuerto
                    .setCantDescargasPostergadas(estadoMontecarloPuerto.getCantActualMuelle()-cantBarcosDescargados);
            //Renueva los randoms de llegada para la proxima iteracion
            randomDescargas = descargaPoisson.getSiguienteRandomBase();
            descargaPoisson = generadorDescargasPoisson.siguienteRandom(parametrosCambioDistribuciones,
                    parametrosGeneradorDescargas, randomDescargas);

            float costoDescargas = 0;

            estadoMontecarloPuerto.calcularDescargasDirectas();
            estadoMontecarloPuerto.acumularDescargasDirectas();

            for(int j = 1 ; j<= cantBarcosDescargados;j++){
                //Usa el random generado por adelantado
                costoDescargas+=costoDescargaNormalRnd.getRandomGenerado();
                randomCostoDescarga = costoDescargaNormalRnd.getSiguienteRandomBase();
                //Actualiza el random generado para utilizarlo como siguiente costo
                costoDescargaNormalRnd = generadorCostoDescargaNormal.siguienteRandom(parametrosCambioDistribuciones,
                        parametrosGeneradorCostoDesc,randomCostoDescarga);
            }
            estadoMontecarloPuerto.setCostoDescarga(costoDescargas);
            acumularCamposEstadoActualUnMuelle(estadoMontecarloPuerto);

            //Agrega fila actual a la tabla si corresponde
            //Agrega las filas del rango solicitado y la última fila para sacar las conclusiones
            if((i >= parametrosMontecarlo.getMostrarVectorDesde() &&
                    i < filaMaximaObservar) || i == parametrosMontecarlo.getN()){
                calcularResultadosParciales(estadoMontecarloPuerto);
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

    private void calcularResultadosParciales(VectorEstadoMontecarloPuerto estadoMontecarloPuerto) {
        estadoMontecarloPuerto.calcularPorcentajeOcupacion();
        estadoMontecarloPuerto.calcularCostoPromedioDemora();
        estadoMontecarloPuerto.calcularPromedioRetrasos();
        estadoMontecarloPuerto.calcularCostoPromedioDia();
        estadoMontecarloPuerto.calcularPromedioDescargaDirecta();
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
