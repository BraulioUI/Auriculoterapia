using Auriculoterapia.Api.Service;
using Microsoft.AspNetCore.Mvc;
using Auriculoterapia.Api.Domain;
using System.Collections.Generic;
using Microsoft.AspNetCore.Authorization;

namespace Controllers
{
    //[Authorize]
    [ApiController]
    [Route("api/[controller]")]
    public class PacienteController: ControllerBase
    {
        private IPacienteService PacienteService;

        public PacienteController(IPacienteService PacienteService){
            this.PacienteService = PacienteService;
        }

        //[Authorize(Roles = "Paciente")]
        [HttpGet]
        public IEnumerable<Paciente> FindAll(){
            return PacienteService.FindAll();
        }       

       // [AllowAnonymous]
        [HttpPost]
        public IActionResult Post([FromBody] Paciente paciente)
        {
            PacienteService.Save(paciente);
            return Ok(paciente); 
        }
    }
}