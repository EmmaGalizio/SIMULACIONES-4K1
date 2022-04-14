using System.Collections.Generic;
using System.Collections.ObjectModel;

/*
 * Wrapper para representar todos los resultados del tp1
 */
namespace SIMULACION_TP1_V1._0._1{
    public class Resultado{

        //Representa la distribución de frecuencias completa de los valores random generados mediante el
        //metodo elegido, para el histograma
        private List<Intervalo> intervalos;
        //Una lista con TODOS los randoms generados, para la grilla
        private List<PseudoAleatorio> valoresGenerados;
        //Cantidad de randoms generados, aunque ya está en el length de la lista
        private long n;
        //Estadistico de prueba chi cuadrado a partir de los valores observados. Sería el mayor C(AC)
        //En la tabla que tenemos como ejemplo en el PDF. 
        private float estadisticoPruebaObs;
        //Valor crítico de la distribución de chi cuadrado, contra el que se compara el anterior para saber si
        //se rechaza o no la hipótesis nula. Se genera a partir de los grados de libertad y el nivel de confianza.
        private float estadisticoPruebaEsp;

        public List<Intervalo> Intervalos{
            get => intervalos;
            set => intervalos = value;
        }

        public List<PseudoAleatorio> ValoresGenerados{
            get => valoresGenerados;
            set => valoresGenerados = value;
        }

        public long N{
            get => n;
            set => n = value;
        }

        public float EstadisticoPruebaObs{
            get => estadisticoPruebaObs;
            set => estadisticoPruebaObs = value;
        }

        public float EstadisticoPruebaEsp{
            get => estadisticoPruebaEsp;
            set => estadisticoPruebaEsp = value;
        }


        public override string ToString(){

            if (n > 10) return "Demasiados valores para mostrar por consola";

            string toString = "Valores generados:\n[";
            foreach (PseudoAleatorio random in valoresGenerados){
                toString += random.Random+", ";
            }

            toString.Remove(toString.Length - 1);
            toString += "]\nDistribucion de frecuencias\n";

            foreach (Intervalo intervalo in intervalos){
                toString += intervalo.LimInferior + " - " + intervalo.LimSuperior + ": FA = " + intervalo.FrecAbsObs+"\n";
            }

            toString += "Estadistico de prueba valores generados: " + estadisticoPruebaObs+"\n";
            toString += "Valor crítico chi cuadrado: " + estadisticoPruebaEsp + "\n";

            return toString;
        }
    }
}
