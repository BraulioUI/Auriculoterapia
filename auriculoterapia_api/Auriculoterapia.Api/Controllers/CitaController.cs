using System;

using Auriculoterapia.Domain;
using Auriculoterapia.Service;
using System.Collections.Generic;

using Microsoft.AspNetCore.Mvc;


namespace Auriculoterapia.Api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CitaController : ControllerBase
    {
        private ICitaService CitaService;

        public CitaController(ICitaService CitaService){
            this.CitaService = CitaService;
        }
        string[] Citas = new[]
        {
            "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
        };


        [HttpGet]
        public string[] Get()
        {
            return Citas;
        }
    }
}
