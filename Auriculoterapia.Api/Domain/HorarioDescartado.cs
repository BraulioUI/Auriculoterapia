using System;
using Newtonsoft.Json;

namespace Auriculoterapia.Api.Domain
{
    public class HorarioDescartado
    {
        public int Id {get; set;}
        public DateTime HoraInicio {get; set;}
        public DateTime HoraFin {get; set;}


        public int DisponibilidadId {get; set;}

        [JsonIgnore]
        public Disponibilidad Disponibilidad {get; set;}
    }
}