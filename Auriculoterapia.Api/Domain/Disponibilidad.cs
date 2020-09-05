using System;
using System.Collections.Generic;

namespace Auriculoterapia.Api.Domain
{
    public class Disponibilidad
    {
        public int Id {get; set;}
        public DateTime HoraInicio {get; set;}
        public DateTime HoraFin {get; set;}
        public DateTime Dia {get; set;}


        public int EspecialistaId {get; set;}
        public Especialista Especialista {get; set;}

        public ICollection<HorarioDescartado> HorariosDescartados {get; set;}

    }
}