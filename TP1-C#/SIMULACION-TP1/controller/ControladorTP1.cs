using System;
using System.Collections.Generic;

namespace SIMULACION_TP1_V1._0._1{
    public class ControladorTP1{

        private IGeneradorRandom generadorRandom;

        public ControladorTP1(){
            generadorRandom = new GeneradorRandom();
        }

        public Resultado generarLineal(int g, int k, int c, int semilla, long n, int cantIntervalos, int presicion){

            if (n <= 0 || n > 1000) n=30;
            cantIntervalos = (cantIntervalos > 0) ? cantIntervalos : (int)Math.Sqrt(n);
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 4;
            List<PseudoAleatorio> randomsLineal = generadorRandom.generarLineal(g, k, c, semilla, n, presicion);
            if (randomsLineal == null) return null;
            List<Intervalo> distFrecuencia = inicializarDistribucion(cantIntervalos, n, presicion);
            generarDistribucionFrecuencia(randomsLineal,distFrecuencia, cantIntervalos, presicion);
            Resultado resultado = new Resultado();
            resultado.N = n;
            resultado.Intervalos = distFrecuencia;
            resultado.ValoresGenerados = randomsLineal;

            return resultado;

        }
        public Resultado generarMultiplicativo(int g, int k, int semilla, long n, int cantIntervalos, int presicion){
            
            if (n <= 0 || n > 1000) n = 30;
            cantIntervalos = (cantIntervalos > 0) ? cantIntervalos : (int)Math.Sqrt(n);
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 4;
            List<PseudoAleatorio> randomsMultiplicativos = generadorRandom.generarMultiplicativo(g, k, semilla, n,presicion);
            if (randomsMultiplicativos == null) return null;
            List<Intervalo> distFrecuencia = inicializarDistribucion(cantIntervalos, n, presicion);
            generarDistribucionFrecuencia(randomsMultiplicativos,distFrecuencia,cantIntervalos,presicion);
            Resultado resultado = new Resultado();
            resultado.N = n;
            resultado.Intervalos = distFrecuencia;
            resultado.ValoresGenerados = randomsMultiplicativos;
            return resultado;
        }
        public Resultado generarDefault(long n, int presicion, int cantIntervalos){
            n = (n > 0 && n <=1000) ? n : 30;
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 9;
            cantIntervalos = (cantIntervalos > 0 && cantIntervalos < n) ? cantIntervalos : (int) Math.Sqrt(n);
            
            List<PseudoAleatorio> randomsDefault = generadorRandom.generarDefault(n, presicion);
            List<Intervalo> distFrecuencia = inicializarDistribucion(cantIntervalos, n, presicion);
            generarDistribucionFrecuencia(randomsDefault,distFrecuencia, cantIntervalos, presicion);
            Resultado resultado = new Resultado();
            resultado.N = n;
            resultado.Intervalos = distFrecuencia;
            resultado.ValoresGenerados = randomsDefault;
            
            return resultado;
        }

        public Resultado generarPruebaChiCuadradoLineal(int g, int k,int c, int semilla, 
                                                                        long n, int cantIntervalos,int presicion){
            if (n <= 0 || n > 1000) n = 30;
            cantIntervalos = (cantIntervalos > 0) ? cantIntervalos : (int)Math.Sqrt(n);
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 4;
            
            Resultado resultado = generarLineal(g,k,c,semilla,n,cantIntervalos,presicion);
            //List<Single> randomsLineal = generadorRandom.generarLineal(g, k, c, semilla, n, presicion);
            //List<Intervalo> distFrecuencia = inicializarDistribucion(cantIntervalos, n, presicion);
            //List<Single> randomsDefault = generadorRandom.generarDefault(n,presicion);
            //generarDistribucionFrecuenciaChiCuadrado(resultado.Intervalos,randomsDefault,cantIntervalos,presicion);
            generarEstadisticoChiCuadrado(resultado, presicion);

            return resultado;
        }

        public Resultado generarPruebaChiCuadradoMultiplicativo(int g, int k, int semilla, long n,
            int cantIntervalos, int presicion){
            
            if (n <= 0 || n > 1000) n=30;
            cantIntervalos = (cantIntervalos > 0) ? cantIntervalos : (int)Math.Sqrt(n);
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 4;
            
            Resultado resultado = generarMultiplicativo(g,k,semilla,n,cantIntervalos,presicion);
            //List<Single> randomsDefault = generadorRandom.generarDefault(n,presicion);
            //generarDistribucionFrecuenciaChiCuadrado(resultado.Intervalos,randomsDefault,cantIntervalos,presicion);
            generarEstadisticoChiCuadrado(resultado, presicion);
            resultado.EstadisticoPruebaEsp = obtenerValorChiCuadrado(cantIntervalos);
            return resultado;
            
        }

        public Resultado generarPruebaChiCuadradoDefault(long n, int presicion, int cantIntervalos){
            n = (n > 0 && n <=1000) ? n : 30;
            presicion = (presicion > 0 && presicion <= 9) ? presicion : 9;
            cantIntervalos = (cantIntervalos > 0 && cantIntervalos < n) ? cantIntervalos : (int) Math.Sqrt(n);
            //nivelSignificancia = (nivelSignificancia > 0 && nivelSignificancia < 1) ? nivelSignificancia : 0.95f;
            Resultado resultado = generarDefault(n, presicion, cantIntervalos);
            generarEstadisticoChiCuadrado(resultado,presicion);
            
            return resultado;
        }
        
        private List<Intervalo> inicializarDistribucion(int cantIntevalos, long n, int presicion){

            int multiplicador = (int)Math.Pow(10, presicion);
            float amplitudIntervalo = (float)(Math.Truncate((float)1/cantIntevalos*multiplicador)/multiplicador);
            float frecuenciaEsperada = (float) Math.Truncate(((float)n / cantIntevalos) * multiplicador) / multiplicador;
            Console.WriteLine("Amplitud Intervalo: "+ amplitudIntervalo);

            List<Intervalo> intervalos = new List<Intervalo>();

            Single limInf = 0.0000f;
            for (int i = 0; i < cantIntevalos; i++){
                Intervalo intervalo = new Intervalo();
                intervalo.LimInferior = limInf;
                intervalo.LimSuperior = limInf + amplitudIntervalo;
                intervalo.FrecAbsObs = 0;
                intervalo.FrecAbsEsp = frecuenciaEsperada;
                intervalos.Add(intervalo);
                limInf = intervalo.LimSuperior;
            }
            return intervalos;
        }
        
        private void generarDistribucionFrecuencia(List<PseudoAleatorio> randoms, List<Intervalo> distFrecuencia, 
            int cantIntervalos, int presicion){
            
            int multiplicador = (int)Math.Pow(10, presicion);
            float amplitudIntervalo = (float)(Math.Truncate((float)1/cantIntervalos*multiplicador)/multiplicador);
            float limiteSuperiorDist = amplitudIntervalo * cantIntervalos;
            foreach (PseudoAleatorio random in randoms){
                //Están ordenados por intervalo, lo ideal es usar busqueda binaria pero ni ganas de impl

                foreach (Intervalo intervalo in distFrecuencia){
                    if ((random.Random >= intervalo.LimInferior && random.Random < intervalo.LimSuperior) ||
                        (intervalo.LimSuperior.Equals(limiteSuperiorDist) && random.Random.Equals(limiteSuperiorDist))){
                        //No está bien comparar números con coma flotante con una igualdad
                        //Advierte posible pérdida de presición pero están todos con la misma presición,
                        //no debería haber problema, aparentemente anda igual
                        intervalo.FrecAbsObs++;
                    }
                }
            }
        }

        /****
         * @Returns: valor del estadistico de prueba para la prueba de chi cuadrado
         */
        private void generarEstadisticoChiCuadrado(Resultado resultado, int presicion){
            float estAcum = 0.0f;
            int multiplicador = (int)Math.Pow(10, presicion);
            foreach (Intervalo intervalo in resultado.Intervalos){

                float numerador = (float)Math.Truncate(Math.Pow((intervalo.FrecAbsEsp - intervalo.FrecAbsObs), 2)*multiplicador)/multiplicador;

                intervalo.Estadistico = (float) Math
                                    .Truncate(numerador / intervalo.FrecAbsEsp * multiplicador) / multiplicador;
                estAcum += intervalo.Estadistico;
                intervalo.EstadisticoAcum = estAcum;
            }

            resultado.EstadisticoPruebaObs = estAcum;
            //nivelSignificancia = (nivelSignificancia > 0 && nivelSignificancia < 1) ? nivelSignificancia : 0.95f;
            //int v = resultado.Intervalos.Count - 1;
            resultado.EstadisticoPruebaEsp = obtenerValorChiCuadrado(resultado.Intervalos.Count);
        }

        public PseudoAleatorio siguienteLineal(int g, int k, int c, int semilla, int presicion){
            return generadorRandom.siguienteLineal(g, k, c, semilla, presicion);
        }

        public PseudoAleatorio siguienteMultiplicativo(int g, int k, int semilla, int presicion){
            return generadorRandom.siguienteMultiplicativo(g, k, semilla, presicion);
        }

        private float obtenerValorChiCuadrado(int intervalos){

            float[] chiCuadrado = new float[20];
            chiCuadrado[0] = 3.8f;
            chiCuadrado[1] = 6f;
            chiCuadrado[2] = 7.8f;
            chiCuadrado[3] = 9.5f;
            chiCuadrado[4] = 11.1f;
            chiCuadrado[5] = 12.6f;
            chiCuadrado[6] = 14.1f;
            chiCuadrado[7] = 15.5f;
            chiCuadrado[8] =16.9f;
            chiCuadrado[9] = 18.3f;
            chiCuadrado[10] = 19.7f;
            chiCuadrado[11] = 21f;
            chiCuadrado[12] = 22.4f;
            chiCuadrado[13] = 23.7f;
            chiCuadrado[14] = 25f;
            chiCuadrado[15] = 26.3f;
            chiCuadrado[16] = 27.6f;
            chiCuadrado[17] = 28.9f;
            chiCuadrado[18] = 30.1f;
            chiCuadrado[19] = 31.4f;

            return chiCuadrado[intervalos - 2];
        }


    }
}
