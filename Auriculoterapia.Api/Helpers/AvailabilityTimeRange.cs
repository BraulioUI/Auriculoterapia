using System;
using System.Collections.Generic;
using Auriculoterapia.Api.Domain;

namespace Auriculoterapia.Api.Helpers
{
    public class AvailabilityTimeRange
    {
        public List<string> hours {get; set;}

        public AvailabilityTimeRange(){
            this.hours = new List<string>();
        }
        public void setList(DateTime minHour, DateTime maxHour, List<HorarioDescartado> horariosDescartados){
            var conversor = new ConversorDeFechaYHora();
            DateTime currentHour = minHour;
            int c;
            while(currentHour <= maxHour){
                c = 0;
                if(horariosDescartados != null){
                    foreach(var h in horariosDescartados){
                    if(currentHour >= h.HoraInicio && currentHour < h.HoraFin){
                        c = 1;
                    }
                }
                }
                
                if (c == 1){
                    currentHour += new TimeSpan(0, 30, 0);
                    continue;
                } else {
                    var itemHora = conversor.TransformarHoraATexto(currentHour);
                    hours.Add(itemHora);
                    currentHour += new TimeSpan(0, 30, 0);
                }
                
            }
        }
    }
}