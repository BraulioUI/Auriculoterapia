using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Repository;
using System.Collections.Generic;

namespace Auriculoterapia.Api.Service.Implementation
{
    public class SolicitudTratamientoService: ISolicitudTratamientoService
    {

         private ISolicitudTratamientoRepository SolicitudTratamientoRepository;


        public SolicitudTratamientoService(ISolicitudTratamientoRepository SolicitudTratamientoRepository){
            this.SolicitudTratamientoRepository = SolicitudTratamientoRepository;
        }

        public IEnumerable<SolicitudTratamiento> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public SolicitudTratamiento findByPacienteId(int pacienteId){
            return SolicitudTratamientoRepository.findByPacienteId(pacienteId);
        }

        public void Save(SolicitudTratamiento entity)
        {
            throw new System.NotImplementedException();
        }
    }
}