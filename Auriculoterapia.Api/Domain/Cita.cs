using System;

namespace Auriculoterapia.Api.Domain
{
    public class Cita
    {
        public int Id {get; set;}
        public Paciente Paciente {get; set;}
        public DateTime Fecha {get; set;}
        public DateTime HoraInicioAtencion {get; set;}
        public DateTime HoraFinAtencion {get; set;}
        public string Estado {get; set;}
    }
}