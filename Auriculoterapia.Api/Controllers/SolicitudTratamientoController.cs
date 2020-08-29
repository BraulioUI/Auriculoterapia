using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Service;
using System.Collections.Generic;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;

namespace Auriculoterapia.Api.Controllers
{
    
    [Authorize]
    [ApiController]
    [Route("api/[controller]")]
    public class SolicitudTratamientoController : ControllerBase
    {
       
        private ISolicitudTratamientoService solicitudTratamientoService;
        public SolicitudTratamientoController(ISolicitudTratamientoService solicitudTratamientoService)
        {
            this.solicitudTratamientoService = solicitudTratamientoService;
        }

        [Authorize(Roles = "PACIENTE")]
        [HttpGet("lista")]
        public IEnumerable<SolicitudTratamiento> FindAll(){
            return solicitudTratamientoService.FindAll();
        }



        [Authorize(Roles = "PACIENTE")]
        [HttpPost]
        public IActionResult Post([FromBody] SolicitudTratamiento solicitudTratamiento)
        {
            solicitudTratamientoService.Save(solicitudTratamiento);
            if(solicitudTratamiento.Id != 0)
                return Ok(solicitudTratamiento); 
            else
                return BadRequest(new {message = "Correo inválido"}); 
        }



    }
}