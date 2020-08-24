using System;

using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
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

        [HttpPost]
        public ActionResult RegistrarCita([FromBody] FormularioCita entity, [FromQuery] int PacienteId){
            CitaService.RegistrarCita(entity, PacienteId);
            return Ok();
        
        }

        [HttpGet]
        public ActionResult ListarCitas(){
            return Ok(CitaService.FindAll());
        }

        [HttpGet("hello_world")]
        public string Get()
        {
            return "Hello World!";
        }
    }
}
