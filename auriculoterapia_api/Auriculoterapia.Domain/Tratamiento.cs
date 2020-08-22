using System;

namespace Auriculoterapia.Domain
{
    public class Tratamiento
    {
        public int Id {get; set;}
        public string TipoTratamiento {get; set;}
        public Date FechaInicio {get;set;}
        public Date FechaFin {get; set;}
        public int FrecuenciaAlDia {get; set;}
        public int TiempoPorTerapia {get; set;}
        

        public int SolicitudTratamientoId {get; set;}
        public SolicitudTratamiento SolicitudTratamiento {get; set;}
        
    }
}
