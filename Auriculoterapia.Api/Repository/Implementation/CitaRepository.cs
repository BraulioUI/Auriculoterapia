using System;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository.Context;
using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class CitaRepository: ICitaRepository
    {
        private ApplicationDbContext context;

        public CitaRepository(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<Cita> FindAll(){
            var citas = new List<Cita>();
            try{
                citas = this.context.Citas.Include(c => c.Paciente).Include(c => c.Paciente.Usuario).Include(c => c.TipoAtencion).ToList();
            } catch(System.Exception){
                throw;
            }
            return citas;
        }
        public void Save(Cita entity){
            try{
                this.context.Add(entity);
                this.context.SaveChanges();
            }catch(System.Exception){
                throw;
            }
        }


        public IEnumerable<Cita> listarCitasPorUsuarioId(int usuarioId){
            var citas = new List<Cita>();
            try{
                citas = this.context.Citas.Include(c => c.Paciente)
                .Include(c => c.Paciente.Usuario)                
                .Where(c => c.Paciente.Usuario.Id == usuarioId).ToList();
            }catch(System.Exception){
                throw;
            }
            return citas;
        }


        public Cita FindById(int id){
            return new Cita();
        }
    }
}
