using System.Collections.Generic;
using System.Linq;
using Auriculoterapia.Api.Domain;

using Auriculoterapia.Api.Repository.Context;
using Microsoft.EntityFrameworkCore;

namespace Auriculoterapia.Api.Repository.Implementation
{
    public class SolicitudTratamientoRepository : ISolicitudTratamientoRepository
    {

        private ApplicationDbContext context;

        public SolicitudTratamientoRepository(ApplicationDbContext context){
            this.context = context;
        }
        
        public IEnumerable<SolicitudTratamiento> FindAll()
        {
            var solicitud = new List<SolicitudTratamiento>();
                try{
                    solicitud = this.context.SolicitudTratamientos.ToList();
                }catch(System.Exception){
                        throw;
                }
                return solicitud;
        }

        public SolicitudTratamiento FindById(int id)
        {
            throw new System.NotImplementedException();
        }

        public void Save(SolicitudTratamiento entity)
        {
            try{
                this.context.Add(entity);
                this.context.SaveChanges();
            }catch(System.Exception){
                throw;
            }
        }

        public SolicitudTratamiento findByPacienteId(int pacienteId){
            var paciente = new SolicitudTratamiento();
            try{
                paciente = this.context.SolicitudTratamientos.Include(s => s.Paciente)
                .Include(s => s.Paciente.Usuario)
                .FirstOrDefault(s => s.Id == pacienteId);
               
            }catch(System.Exception){

            }
            return paciente;
        }
    }
}