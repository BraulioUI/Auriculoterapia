using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;
namespace Auriculoterapia.Api.Service
{
    public interface ICitaService: IService<Cita>
    {
        void RegistrarCita(FormularioCita entity, int PacienteId);
    }
}