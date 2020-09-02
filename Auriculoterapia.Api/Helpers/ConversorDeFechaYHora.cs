using System;
using System.Globalization;

namespace Auriculoterapia.Api.Helpers
{
    public class ConversorDeFechaYHora
    {   
        
        public DateTime TransformarAFecha(string fecha){
            DateTime dt1 = DateTime.ParseExact(fecha, "yyyy-MM-dd", null);
            Console.WriteLine(dt1);
            return dt1;
        }

        public DateTime TransformarAHora(string hora, string fecha){
            string horaFecha = fecha + " " + hora;
            DateTime dt2 = DateTime.ParseExact(horaFecha, "yyyy-MM-dd HH:mm", null);
            return dt2;
        }

        public string TransformarHoraATexto(DateTime hora){
            return hora.ToString("HH:mm");
        }   

          /*public DateTime TransformarDiaEnEspanolAFecha(string fecha){
            //Formato de disponibilidad: "dddd, dd MMMM yyyy"

            // var input = "Dic 13, 2017";
            // var format = "MMM dd, yyyy";            
            // var dt = DateTime.ParseExact(input, format, esCulture);
            // var result = dt.ToString(format, new CultureInfo("en-US"));

       
            var cultureInfo = new CultureInfo("es-ES");
            var formatSpanish = "dddd, dd MMMM yyyy";
            var formatRequired = "yyyy-MM-dd";
            var dt = DateTime.ParseExact(fecha, formatSpanish, cultureInfo);
            var result = string.Format("{0:s}", dt); // 2020-08-31T00:58:12
            var dtResult = DateTime.ParseExact(result, formatRequired, null);
            return dtResult;
        }*/
    }
}