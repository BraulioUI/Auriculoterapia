using System.Collections.Generic;

namespace Auriculoterapia.Api.Helpers
{
    public class FormularioDisponibilidad
    {

        public string horaInicio {get; set;}
        public string horaFin {get; set;}
        public string dia {get; set;}
        public List<FormularioHorarioDescartado> horariosDescartados {get; set;}


    }
}