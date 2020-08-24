using System;

namespace Auriculoterapia.Api.Helpers
{
    public class FormularioCita
    {
        
        public DateTime Fecha {get; set;}
        public DateTime HoraInicioAtencion {get; set;}
        public DateTime HoraFinAtencion {get; set;}
        public string TipoAtencion {get; set;}

   
    }
}