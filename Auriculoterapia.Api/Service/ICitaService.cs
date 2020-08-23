using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Service
{
    public interface ICitaService: IService<Cita>
    {
        void RegistrarCita(FormularioCita entity, int Id);
    }
}