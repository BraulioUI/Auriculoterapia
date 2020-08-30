using Auriculoterapia.Api.Domain;

namespace Auriculoterapia.Api.Repository
{
    public interface ISolicitudTratamientoRepository: IRepository<SolicitudTratamiento>
    {
        SolicitudTratamiento findByPacienteId(int pacienteId);

        void saveByUserId(SolicitudTratamiento entity,int userId);
    }   

}