using System;
using System.Linq;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using System.Security.Claims;
using Microsoft.Extensions.Configuration;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;



using System.Collections.Generic;
using System.Text;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class UsuarioRepository: IUsuarioRepository
    {
        private ApplicationDbContext context;
        private readonly IConfiguration _config;
        private readonly IRol_UsuarioRepository rol_UsuarioRepository;

        public UsuarioRepository(ApplicationDbContext context, IConfiguration config,IRol_UsuarioRepository rol_UsuarioRepository)
        {
            this.context = context;
            this._config = config;
            this.rol_UsuarioRepository = rol_UsuarioRepository;
        }

        public IEnumerable<Usuario> FindAll(){
            var users = new List<Usuario>();
            try{
                users = context.Usuarios.Include(u => u.Paciente).ToList();
            }
            catch(System.Exception)
            {
                throw;
            }
            return users;
        }
        /*public void Save(Usuario entity){
            try{
                context.Usuarios.Add(entity);
                context.SaveChanges();
            }
            catch{
                throw;
            }

        }*/

        public void Save(Usuario entity){
            
            try{
                context.Usuarios.Add(entity);
                //context.Pacientes.Add(entity.Paciente);
                context.SaveChanges();
                rol_UsuarioRepository.Asignar_Usuario_Rol(entity);

                //context.Rol_Usuarios.Add();
                //Asignar_Paciente(entity);
                
            }
            catch{
                throw;
            }

        }

        public  Usuario Autenticar(string nombreUsuario, string password){
            var user = context.Usuarios.FirstOrDefault(x =>x.NombreUsuario == nombreUsuario
            && x.Contrasena == password);

            var rol = context.Rol_Usuarios.Include(x =>x.Rol).FirstOrDefault(x =>x.UsuarioId == user.Id);

            if(user == null)
                return null;

            var tokenHelper = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(this._config.GetSection("AppSettings:Token").Value);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.Id.ToString()),
                    new Claim(ClaimTypes.Role, rol.Rol.Descripcion)
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key),
                    SecurityAlgorithms.HmacSha256Signature)
            };
            var token = tokenHelper.CreateToken(tokenDescriptor);
            user.Token = tokenHelper.WriteToken(token);
            context.SaveChanges();

            user.Contrasena = null;

            return user;
        }


        public void Asignar_Paciente(Usuario usuario){
            try{
                context.Pacientes.Add(usuario.Paciente);
                context.SaveChanges();
                
            }
            catch{
                throw;
            }

        }


        public Usuario FindById(int id){
            var usuario = context.Usuarios.FirstOrDefault(x => x.Id == id);
            var rol = context.Rol_Usuarios.Include(x =>x.Rol).FirstOrDefault(x =>x.UsuarioId == usuario.Id);

            
            if(rol.Rol.Descripcion == "paciente"){
                if(usuario != null)
                usuario.Contrasena = null;

                return usuario;
            }
            //return Console.Error("ff");
            throw new Exception("$No pertenece al rol paciente");
                        
        }

    }
}
