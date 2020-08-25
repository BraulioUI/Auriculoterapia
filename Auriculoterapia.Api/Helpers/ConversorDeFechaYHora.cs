using System;

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
    }
}