using System;

using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Service;
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

        [HttpPost("paciente/{Id}")]
        public ActionResult RegistrarCita([FromBody] FormularioCita entity, int Id){
            CitaService.RegistrarCita(entity, Id);
            return Ok();
        
        }

        [HttpGet]
        public IEnumerable<Cita> ListarCitas(){
            return CitaService.FindAll();
        }

        [HttpGet("hello_world")]
        public string Get()
        {
            return "Hello World!";
        }
    }
}
