package p.grupo.simulacionestp4montecarlo;

import dnl.utils.text.table.TextTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import p.grupo.simulacionestp4montecarlo.controller.ControladorTp4MontecarloPuerto;
import p.grupo.simulacionestp4montecarlo.controller.utils.ConstantesGenerador;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosCambioDistribucion;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosGenerador;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosMontecarlo;
import p.grupo.simulacionestp4montecarlo.modelo.montecarlo.VectorEstadoMontecarloPuerto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestMontecarloUnMuelle {

    @Autowired
    private ControladorTp4MontecarloPuerto controladorTp4;

    @Test
    public void testMontecarlo(){

        ParametrosCambioDistribucion parametrosCostoDescarga = parametrosGeneradorCostoDescarga();

        ParametrosMontecarlo parametrosMontecarlo = new ParametrosMontecarlo();
        parametrosMontecarlo.setN(1000);
        parametrosMontecarlo.setCantFilasMostrar(50);
        parametrosMontecarlo.setMostrarVectorDesde(500);

        List<VectorEstadoMontecarloPuerto> simulacionPuerto =
                controladorTp4.generarSimulacionEstActual(parametrosCostoDescarga
                        ,parametrosGeneradorIngresos(),
                        parametrosGeneradorDescargas(),
                        parametrosGeneradorCostoDescUnif(),parametrosMontecarlo);

        assertThat(simulacionPuerto).isNotNull();
        assertThat(simulacionPuerto.size()).isEqualTo(parametrosMontecarlo.getCantFilasMostrar()+1);

        //System.out.println("Día\tRND\tIngresos\tRND\tDescargas\tDemora\tCosto Desc\tCosto Dem\tCosto Dia\tCosto Acum\tCosto Dem Acum\tDemora Acum");
        //simulacionPuerto.stream().forEach(System.out::println);
        String[] columnas = {"Día","RND","Ingr.","Act Muelle","RND","Desc","Demor",
                "Cost Desc","Cost Dem","Cost Día","Cost Ac","Cost Dem Ac","Dem Ac","Barc Dem Ac"};
        Object[][] data  = new Object[simulacionPuerto.size()][14];
        int i = 0;
        for(VectorEstadoMontecarloPuerto vector: simulacionPuerto){
            data[i][0] = vector.getDia();
            data[i][1] = vector.getRandomIngresos();
            data[i][2] = vector.getCantIngresos();
            data[i][3] = vector.getCantActualMuelle();
            data[i][4] = vector.getRandomDescargas();
            data[i][5] = vector.getCantDescargas();
            data[i][6] = vector.getCantDescargasPostergadas();
            data[i][7] = vector.getCostoDescarga();
            data[i][8] = vector.getCostoDemora();
            data[i][9] = vector.getCostoTotalDia();
            data[i][10] = vector.getCostoAcumulado();
            data[i][11] = vector.getCostoDemoraAcumulado();
            data[i][12] = vector.getDiasDemoraAcumulada();
            data[i][13] = vector.getBarcosPostergadosAcum();
            i++;
        }
        TextTable textTable = new TextTable(columnas,data);
        textTable.printTable();



    }

    private ParametrosGenerador parametrosGeneradorIngresos(){
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setC(7);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(13);
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(5);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        return parametrosGenerador;
    }

    private ParametrosGenerador parametrosGeneradorDescargas(){
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setC(11);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(13);
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(7);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        return parametrosGenerador;
    }
    private ParametrosGenerador parametrosGeneradorCostoDescUnif(){
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setC(5);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(11);
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(3);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        return parametrosGenerador;
    }

    private ParametrosCambioDistribucion parametrosGeneradorCostoDescarga(){

        ParametrosCambioDistribucion parametrosCambio = new ParametrosCambioDistribucion();
        parametrosCambio.setMedia(800);
        parametrosCambio.setDesvEst(120);
        parametrosCambio.setPresicion(4);
        return parametrosCambio;
    }
}
