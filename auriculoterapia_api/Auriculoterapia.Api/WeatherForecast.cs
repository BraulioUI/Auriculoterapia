using System;
using Microsoft.AspNetCore.Identity;

namespace Auriculoterapia.Api
{
    public class WeatherForecast: IdentityUser
    {
        public DateTime Date { get; set; }

        public int TemperatureC { get; set; }

        public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);

        public string Summary { get; set; }
    }
}
