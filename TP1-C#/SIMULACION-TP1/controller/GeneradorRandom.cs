using System;
using System.Collections.Generic;

namespace SIMULACION_TP1_V1._0._1{
    public class GeneradorRandom: IGeneradorRandom{
        public List<PseudoAleatorio> generarLineal(int g, int k, int c, int semilla, long n, int presicion){

            if (g <= 0 || c <= 0 || k <= 0 || semilla <= 0) return null;
            
            Single m = (Single)Math.Pow(2, g);

            if (!primosRelativos(m, c)) return null;
            int a = 1 + (4 * k);
            
            List<PseudoAleatorio> resultados = new List<PseudoAleatorio>();
            Single xi = semilla;
            
            Single multiplicador = (Single)Math.Pow(10, presicion);
            for(long i = 0; i < n; i++){

                PseudoAleatorio pseudoAleatorio = new PseudoAleatorio();

                xi = (a * xi) + c;
                pseudoAleatorio.Axi = (int)xi; //es siempre entero
                xi = xi % m;
                xi = (Single)(Math.Truncate(xi*multiplicador)/multiplicador);
                pseudoAleatorio.Semilla = (int)xi;
                //El tp especifica que sea hasta 0.9999, por eso hay que dividir sobre m en vez de m-1
                pseudoAleatorio.Random = (Single)Math.Truncate(xi / (m)*multiplicador)/multiplicador;
                resultados.Add(pseudoAleatorio);
            }

            return resultados;
        }

        public List<PseudoAleatorio> generarMultiplicativo(int g, int k, int semilla, long n, int presicion){
            
            if(g <= 0 || k <= 0 || semilla <= 0) return null;
            if(semilla % 2 == 0) return null;
            float m = (float)Math.Pow(2, g);
            int a = 3 + (8 * k);
            
            
            List<PseudoAleatorio> resultados = new List<PseudoAleatorio>();
            float xi = semilla;
            float multiplicador = (Single)Math.Pow(10, presicion);
            for(long i = 0; i < n; i++){
                PseudoAleatorio pseudoAleatorio = new PseudoAleatorio();
                xi = (a * xi);
                pseudoAleatorio.Axi = (int) xi;
                xi = xi % m;
                xi = (Single)(Math.Truncate(xi*multiplicador)/multiplicador); //En teoria es un entero
                pseudoAleatorio.Semilla = (int)xi;
                //El tp especifica que sea hasta 0.9999, por eso hay que dividir sobre m en vez de m-1
                pseudoAleatorio.Random = (float)Math.Truncate(xi / (m)*multiplicador)/multiplicador;
                resultados.Add(pseudoAleatorio);
            }
            return resultados;
            
        }

        public List<PseudoAleatorio> generarDefault(long n, int presicion){
            if (n <= 0) return null;

            Random random = new Random();
            List<PseudoAleatorio> resultados = new List<PseudoAleatorio>();

            Single multiplicador = (Single)Math.Pow(10, presicion);
            for(long i = 0; i < n; i++){
                float x = (Single) random.NextDouble();
                x = (Single)(Math.Truncate(x*multiplicador)/multiplicador);
                PseudoAleatorio pseudoAleatorio = new PseudoAleatorio();
                pseudoAleatorio.Random = x;
                resultados.Add(pseudoAleatorio);
            }

            return resultados;
        }

        public bool primosRelativos(double m, double c){
            //Dos numeros son primos relativos si no tienen otro divisor
            //común más que 1 y -1 por tanto lo que se debe hacer es calcular
            //el maximo divisor común entre dos numeros dados, y luego si el
            //resultado es igual a 1 o a -1, entonces los numeros son primos relativos, de lo contrario, no lo son.           

            //calcular el maximo divisor común
            double resto;
            while (c != 0){
                resto = m % c;
                m = c;
                c = resto;
            }
            if (m == 1 || m == -1){
                return true;
            }

            return false;
        }

        public PseudoAleatorio siguienteLineal(int g, int k, int c, int semilla, int presicion){

            g = (g > 0) ? g : 3;
            k = (k > 0) ? k : 3;
            semilla = (semilla >= 0) ? semilla : 6;
            c = (c > 0) ? c : 7;

            int m = (int) Math.Pow(2, g);
            if (!primosRelativos(m, c)) return null;
            int a = 1 + (4 * k);
            Single multiplicador = (Single)Math.Pow(10, presicion);
            
            int axipc = (a * semilla) + c;
            int xip1 =  axipc % m;
            PseudoAleatorio pseudoAleatorio = new PseudoAleatorio();
            pseudoAleatorio.Semilla = xip1;
            float random = (float) Math.Truncate((float) xip1 / (m) * multiplicador) / multiplicador;
            pseudoAleatorio.Random = random;

            return pseudoAleatorio;
        }

        public PseudoAleatorio siguienteMultiplicativo(int g, int k, int semilla, int presicion){
            g = (g > 0) ? g : 3;
            k = (k > 0) ? k : 3;
            semilla = (semilla >= 0) ? semilla : 6;
            int m = (int) Math.Pow(2, g);
            
            int a = 3 + (8 * k);
            Single multiplicador = (Single)Math.Pow(10, presicion);
            
            int axi = a * semilla;
            int xip1 =  axi % m;
            PseudoAleatorio pseudoAleatorio = new PseudoAleatorio();
            pseudoAleatorio.Semilla = xip1;
            float random = (float) Math.Truncate((float) xip1 / (m) * multiplicador) / multiplicador;
            pseudoAleatorio.Random = random;
            return pseudoAleatorio;
            
        }
    }
}
