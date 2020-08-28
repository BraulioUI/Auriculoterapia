using Auriculoterapia.Api.Service;
using Microsoft.AspNetCore.Mvc;
using Auriculoterapia.Api.Domain;
using System.Collections.Generic;
using Microsoft.AspNetCore.Authorization;

namespace Auriculoterapia.Api.Controllers
{

    [ApiController]
    [Route("api/[controller]")]
    public class SolicitudTratamientoController
    {
        private ISolicitudTratamientoService SolicitudTratamientoService;

        public SolicitudTratamientoController(ISolicitudTratamientoService solicitudTratamientoService){
            this.SolicitudTratamientoService = solicitudTratamientoService;
        }


        [HttpGet]
        public SolicitudTratamiento buscarPorPacienteId([FromQuery] int pacienteId){
            return SolicitudTratamientoService.findByPacienteId(pacienteId);
        }


    }
}