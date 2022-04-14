using System;
using System.Collections.Generic;

namespace SIMULACION_TP1_V1._0._1{
    public interface IGeneradorRandom{

        List<PseudoAleatorio> generarLineal(int g, int k, int c, int semilla, long n, int presicion);

        List<PseudoAleatorio> generarMultiplicativo(int g, int k, int semilla, long n, int presicion);

        List<PseudoAleatorio> generarDefault(long n, int presicion);

        bool primosRelativos(double m, double c);

        PseudoAleatorio siguienteLineal(int g, int k, int c, int semilla, int presicion);

        PseudoAleatorio siguienteMultiplicativo(int g, int k, int semilla, int presicion);

    }
}
