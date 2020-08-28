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
    public class UsuarioController : ControllerBase
    {
        private IUsuarioService usuarioService;

        public UsuarioController(IUsuarioService usuarioService){
            this.usuarioService = usuarioService;
        }


        [Authorize(Roles = "ESPECIALISTA")]
        [HttpGet]
        public ActionResult Get()
        {
            return Ok(
                usuarioService.FindAll()
            );
        }

        [AllowAnonymous]
        [HttpPost]
        public IActionResult Post([FromBody] Usuario usuario)
        {
            usuarioService.Save(usuario);
            return Ok(usuario); 
        }

        [AllowAnonymous]
        [HttpPost("autenticar")]
        public IActionResult Autenticar([FromBody] Usuario usuario)
        {
            var user = usuarioService.Autenticar(usuario.NombreUsuario,usuario.Contrasena);

            if (user == null){
                return BadRequest(new {message = "Nombre de usuario o contraseña incorrectos"});    
            }

            return Ok(user);
        }

        [AllowAnonymous]
        [HttpPost("actualizarContrasena")]
        public IActionResult actualizar_Contrasena([FromBody] Usuario usuario){
            var nuevaContrasena = usuarioService.actualizar_Contrasena(usuario.NombreUsuario,usuario.PalabraClave,usuario.Contrasena);

            if(nuevaContrasena == null){
                return BadRequest(new {message = "Nombre de usuario o palabra clave incorrectos"});
            }

            return Ok(nuevaContrasena);

        }


        [HttpGet("{id}")]
        public IActionResult FindById(int id)
        {
            var user = usuarioService.FinbyId(id);

            if(user == null){
                return NotFound();
            }else{

                return Ok(user);
            }
            //return Forbid();

            //var currentUserID = int.Parse(User.Identity.Name);
            

            //if (id != currentUserID && !User.IsInRole("paciente")) {
               // return Forbid();
            //}

            

        }
        
    }
}