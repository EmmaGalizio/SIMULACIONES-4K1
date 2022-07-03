package emma.galzio.simulacionestp7consultorio.modelo.cliente;

import lombok.Data;

import java.util.Objects;

@Data
public class PacienteEstudio extends Paciente {
    private float momentoLlegadaColaTecnico;
    private float momentoInicioAtencionTecnico;


    @Override
    public boolean tieneTurno() {
        return true;
    }

    @Override
    public Object clone(){
        PacienteEstudio nuevoPaciente = new PacienteEstudio();
        nuevoPaciente.setId(this.getId());
        nuevoPaciente.setEstado(this.getEstado());
        nuevoPaciente.setMomentoLlegada(this.getMomentoLlegada());
        nuevoPaciente.setMomentoInicioAtencionTecnico(this.momentoInicioAtencionTecnico);
        nuevoPaciente.setMomentoLlegadaColaTecnico(this.momentoLlegadaColaTecnico);
        return nuevoPaciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacienteEstudio that = (PacienteEstudio) o;
        return this.getId() != that.getId();
    }

}
