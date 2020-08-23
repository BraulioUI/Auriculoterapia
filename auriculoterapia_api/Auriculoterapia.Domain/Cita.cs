using System;

namespace Auriculoterapia.Domain
{
    public class Cita
    {
        public int Id {get; set;}
<<<<<<< HEAD
=======
        public Paciente Paciente {get; set;}
>>>>>>> master
        public DateTime Fecha {get; set;}
        public DateTime HoraInicioAtencion {get; set;}
        public DateTime HoraFinAtencion {get; set;}
        public string Estado {get; set;}

        public int PacienteId {get; set;}
        public Paciente Paciente {get; set;}
        
    }
}
