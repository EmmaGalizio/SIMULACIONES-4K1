package k1.grupo.p.simulacionestp5colas.controller.generadorRandom;



import k1.grupo.p.simulacionestp5colas.controller.utils.ConstantesGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import org.springframework.stereotype.Component;


import java.util.Random;

@Component(ConstantesGenerador.LENGUAJE)
public class GeneradorPseudoLenguaje implements IGeneradorRandom{
    @Override
    public Pseudoaleatorio[] generar(ParametrosGenerador parametros) {

        if(parametros == null) parametros = new ParametrosGenerador(30,4);
        if(parametros.getN() <=0 || parametros.getN() > 10000) parametros.setN(30);
        Pseudoaleatorio[] pseudoaleatorios = new Pseudoaleatorio[parametros.getN()];
        Random random = new Random();
        for(int i = 0; i<pseudoaleatorios.length; i++){
            pseudoaleatorios[i] = new Pseudoaleatorio(i+1, random.nextFloat());
        }
        return pseudoaleatorios;
    }

    @Override
    public Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros) {
        Random random = new Random();
        return new Pseudoaleatorio(0, random.nextFloat());
    }

    @Override
    public Pseudoaleatorio siguientePseudoAleatoreo(ParametrosGenerador parametrosGenerador) {
        return siguientePseudoAleatoreo(null,parametrosGenerador);
    }
}
