using System;

namespace SIMULACION_TP1_V1._0._1{
    public class PseudoAleatorio{

        private Single random;
        private int semilla;
        private int axi;

        public float Random{
            get => random;
            set => random = value;
        }

        public int Semilla{
            get => semilla;
            set => semilla = value;
        }

        public int Axi{
            get => axi;
            set => axi = value;
        }

        public override string ToString(){
            return $"{nameof(random)}: {random}, {nameof(semilla)}: {semilla}";
        }
    }
}
