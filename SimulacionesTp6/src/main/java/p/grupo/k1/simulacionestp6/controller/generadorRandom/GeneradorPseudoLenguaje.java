package p.grupo.k1.simulacionestp6.controller.generadorRandom;

import org.springframework.stereotype.Component;
import p.grupo.k1.simulacionestp6.controller.utils.ConstantesGenerador;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;

import java.util.Random;

@Component(ConstantesGenerador.LENGUAJE)
public class GeneradorPseudoLenguaje implements IGeneradorRandom{

    private Random random;
    @Override
    public Pseudoaleatorio[] generar(ParametrosGenerador parametros) {

        if(parametros == null) parametros = new ParametrosGenerador(30,4);
        if(parametros.getN() <=0 || parametros.getN() > 10000) parametros.setN(30);
        Pseudoaleatorio[] pseudoaleatorios = new Pseudoaleatorio[parametros.getN()];
        int multiplicador = (int) Math.pow(10, parametros.getPresicion());
        if(random == null) {
            random = new Random();
        }
        for(int i = 0; i<pseudoaleatorios.length; i++){
            float frandom = random.nextFloat();
            int randAux = (int)(frandom*multiplicador);
            pseudoaleatorios[i] = new Pseudoaleatorio(i+1, ((float)randAux/multiplicador));
        }
        return pseudoaleatorios;
    }

    @Override
    public Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros) {
        if(random == null) {
            random = new Random();
        }
        int multiplicador = (int) Math.pow(10, parametros.getPresicion());
        float frandom = random.nextFloat();
        int randAux = (int)(frandom*multiplicador);
        return new Pseudoaleatorio(0, ((float)randAux/multiplicador));
    }

    @Override
    public Pseudoaleatorio siguientePseudoAleatoreo(ParametrosGenerador parametrosGenerador) {
        return siguientePseudoAleatoreo(null,parametrosGenerador);
    }
}
