
using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;

namespace Auriculoterapia.Api.Service.Implementation
{
    public class SolicitudTratamientoService : ISolicitudTratamientoService
    {
       
        private ISolicitudTratamientoRepository solicitudTratamientoRepository;
        public SolicitudTratamientoService(ISolicitudTratamientoRepository solicitudTratamientoRepository)
        {
            this.solicitudTratamientoRepository = solicitudTratamientoRepository;
        }

        public IEnumerable<SolicitudTratamiento> FindAll()
        {
             return solicitudTratamientoRepository.FindAll();
        }

        

        public SolicitudTratamiento findByPacienteId(int pacienteId){
            return solicitudTratamientoRepository.findByPacienteId(pacienteId);
        }

        public void Save(SolicitudTratamiento entity)
        {
            solicitudTratamientoRepository.Save(entity);

        }
    }
}