using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Service;
using Microsoft.AspNetCore.Mvc;

namespace Auriculoterapia.Api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TipoAtencionController: ControllerBase
    {
        private ITipoAtencionService TipoAtencionService;

        public TipoAtencionController(ITipoAtencionService TipoAtencionService){    
            this.TipoAtencionService = TipoAtencionService;
        }

        public IEnumerable<TipoAtencion> listarTiposAtencion(){
            return TipoAtencionService.FindAll();
        }
    }
}