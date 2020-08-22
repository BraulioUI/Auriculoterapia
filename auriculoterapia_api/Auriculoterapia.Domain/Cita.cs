using System;

namespace Auriculoterapia.Domain
{
    public class Cita
    {
        public int Id {get; set;}
        public Paciente Paciente {get; set;}
        public Date Fecha {get; set;}
        public Time HoraInicioAtencion {get; set;}
        public Time HoraFinAtencion {get; set;}
        public string Estado {get; set;}
        
    }
}
