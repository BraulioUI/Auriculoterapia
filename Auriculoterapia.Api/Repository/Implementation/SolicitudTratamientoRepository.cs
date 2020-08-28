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
    }
}