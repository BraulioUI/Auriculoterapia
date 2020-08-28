using System.Collections.Generic;
using System.Linq;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using Auriculoterapia.Api.Repository.Context;
using Microsoft.EntityFrameworkCore;
namespace Auriculoterapia.Api.Repository.Implementation
{
    public class SolicitudTratamientoRepository : ISolicitudTratamientoRepository
    {

        private ApplicationDbContext context;

         public SolicitudTratamientoRepository(ApplicationDbContext context)
        {
            this.context = context;
        }


        public IEnumerable<SolicitudTratamiento> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public SolicitudTratamiento FindById(int id)
        {
            throw new System.NotImplementedException();
        }

        public void Save(SolicitudTratamiento entity)
        {
            throw new System.NotImplementedException();
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