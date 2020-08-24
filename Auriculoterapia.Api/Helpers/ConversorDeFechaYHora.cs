using System;

namespace Auriculoterapia.Api.Helpers
{
    public class ConversorDeFechaYHora
    {
        public DateTime TransformarAFecha(string fecha){
            DateTime dt = DateTime.Parse(fecha);
            return dt;
        }

        public DateTime TransformarAHora(string hora){
            
            DateTime dt = DateTime.ParseExact(hora, "HH:mm:ss", System.Globalization.CultureInfo.InvariantCulture);
            return dt;
        }
    }
}