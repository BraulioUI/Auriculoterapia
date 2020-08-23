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

        /*
            {
                "Fecha": "2020-08-23",
                "HoraInicioAtencion": "0000-00-00 09:30:00",
                "HoraFinAtencion": "0000-00-00 10:00:00",
                "Estado": "En Proceso"
            }
        
        
        */
    }
}