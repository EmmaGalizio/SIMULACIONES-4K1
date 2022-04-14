using System.Collections.Generic;

namespace SIMULACION_TP1_V1._0._1{
    public class Intervalo{

        private float limInferior;
        private float limSuperior;
        //Cantidad de randoms del método elegido que caen dentro del intervalo
        private int frecAbsObs;
        //Cantidad de randoms del metodo del lenguaje que caen dentro del intervalo
        private float frecAbsEsp;
        //El valor del estadistico de chi cuadrado para el intervalo actual C = (fe-fo)²/fe
        private float estadistico;
        //Acumulado hasta el intervalo actual del estadistico. C(AC) en la tabla de ejemplo del PDF
        private float estadisticoAcum;

        public float LimInferior{
            get => limInferior;
            set => limInferior = value;
        }

        public float LimSuperior{
            get => limSuperior;
            set => limSuperior = value;
        }

        public int FrecAbsObs{
            get => frecAbsObs;
            set => frecAbsObs = value;
        }

        public float FrecAbsEsp{
            get => frecAbsEsp;
            set => frecAbsEsp = value;
        }

        public float Estadistico{
            get => estadistico;
            set => estadistico = value;
        }

        public float EstadisticoAcum{
            get => estadisticoAcum;
            set => estadisticoAcum = value;
        }


        protected bool Equals(Intervalo other){
            return limInferior.Equals(other.limInferior) && limSuperior.Equals(other.limSuperior);
        }

        public override bool Equals(object obj){
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Intervalo) obj);
        }

        public override int GetHashCode(){
            unchecked{
                return (limInferior.GetHashCode() * 397) ^ limSuperior.GetHashCode();
            }
        }

        private sealed class LimInferiorLimSuperiorEqualityComparer : IEqualityComparer<Intervalo>{
            public bool Equals(Intervalo x, Intervalo y){
                if (ReferenceEquals(x, y)) return true;
                if (ReferenceEquals(x, null)) return false;
                if (ReferenceEquals(y, null)) return false;
                if (x.GetType() != y.GetType()) return false;
                return x.limInferior.Equals(y.limInferior) && x.limSuperior.Equals(y.limSuperior);
            }

            public int GetHashCode(Intervalo obj){
                unchecked{
                    return (obj.limInferior.GetHashCode() * 397) ^ obj.limSuperior.GetHashCode();
                }
            }
        }

        public static IEqualityComparer<Intervalo> LimInferiorLimSuperiorComparer{ get; } = new LimInferiorLimSuperiorEqualityComparer();
    }
    
    
}
