using System;

using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
using Auriculoterapia.Api.Service;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;

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

        [HttpPost("especialista")]
        public ActionResult RegistrarCitaEspecialista([FromBody] FormularioCita entity, [FromQuery] int PacienteId){
            CitaService.RegistrarCita(entity, PacienteId);
            return StatusCode(Microsoft.AspNetCore.Http.StatusCodes.Status201Created);
        
        }

        [HttpGet("paciente")]
        [Authorize(Roles = "PACIENTE")]
        public ActionResult ListarCitasPorUsuarioId([FromQuery] int usuarioId){
            return Ok(this.CitaService.listarCitasPorUsuarioId(usuarioId));
        }

        [HttpPost("paciente")]
        public ActionResult RegistrarCitaPaciente([FromBody] FormularioCitaPaciente entity, [FromQuery] int PacienteId){
            CitaService.RegistrarCitaPaciente(entity, PacienteId);
            Console.WriteLine(StatusCode(Microsoft.AspNetCore.Http.StatusCodes.Status201Created));
            return StatusCode(Microsoft.AspNetCore.Http.StatusCodes.Status201Created);
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
