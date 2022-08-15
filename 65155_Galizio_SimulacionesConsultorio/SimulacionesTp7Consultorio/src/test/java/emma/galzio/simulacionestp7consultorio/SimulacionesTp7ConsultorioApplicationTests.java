package emma.galzio.simulacionestp7consultorio;

import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimulacionesTp7ConsultorioApplicationTests {

    @Test
    public void roundingTest(){

        double a = 55.3356654324;
        double b = 57874544.456447;
        double c = 7577.3267883678;

        a = CommonFunc.round(a,4);
        b = CommonFunc.round(b,4);
        c = CommonFunc.round(c,4);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

    }


}
