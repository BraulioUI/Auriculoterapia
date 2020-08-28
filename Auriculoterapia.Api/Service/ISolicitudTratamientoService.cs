using Auriculoterapia.Api.Domain;


namespace Auriculoterapia.Api.Service
{
    public interface ISolicitudTratamientoService: IService<SolicitudTratamiento>
    {
        SolicitudTratamiento findByPacienteId(int pacienteId);
    }
}