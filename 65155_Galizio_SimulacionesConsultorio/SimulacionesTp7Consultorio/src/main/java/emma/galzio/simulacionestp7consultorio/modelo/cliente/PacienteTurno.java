package emma.galzio.simulacionestp7consultorio.modelo.cliente;

import lombok.Data;

@Data
public class PacienteTurno extends Paciente {

    public PacienteTurno(){
        this.tipoPaciente = "s/Turno";
    }

    @Override
    public boolean tieneTurno() {
        return false;
    }

    @Override
    public Object clone(){
        PacienteTurno nuevoPaciente = new PacienteTurno();
        nuevoPaciente.setId(this.getId());
        nuevoPaciente.setEstado(this.getEstado());
        nuevoPaciente.setMomentoLlegada(this.getMomentoLlegada());
        return nuevoPaciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacienteTurno that = (PacienteTurno) o;
        return this.getId() != that.getId();
    }
}
