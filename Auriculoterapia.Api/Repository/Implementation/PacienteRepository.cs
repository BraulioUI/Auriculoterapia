using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Linq;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class PacienteRepository: IPacienteRepository
    {
        private ApplicationDbContext context;

        private readonly IUsuarioRepository usuarioRepository;


        public PacienteRepository(ApplicationDbContext context,IUsuarioRepository usuarioRepository)
        {
            this.context = context;
            this.usuarioRepository = usuarioRepository;
        }

        public IEnumerable<Paciente> FindAll(){
            var pacientes = new List<Paciente>();
            try{
                pacientes = this.context.Pacientes.Include(p => p.Usuario).ToList();
            }catch(System.Exception){
                    throw;
            }
            return pacientes;
        }
        public void Save(Paciente entity){
            try{
                usuarioRepository.Save(entity.Usuario);
                context.Add(entity);
                context.SaveChanges();
                
            }catch{
                throw;
            }
            
        }

        
        public Paciente FindById(int Id){
            var paciente = new Paciente();
            try{
                paciente = this.context.Pacientes.Single(paciente => paciente.Id == Id);
            }catch(System.Exception){
                throw;
            }
            return paciente;
        }


        public string ActualizarNumeroPaciente(string numero, Paciente paciente){
            string actualizado = "Actualizado";
            try{
                paciente.Celular = numero;
                this.context.SaveChanges();
            } catch{

            }
            return actualizado;
        }
    }
}
