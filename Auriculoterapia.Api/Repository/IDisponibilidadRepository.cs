
using Auriculoterapia.Api.Domain;

namespace Auriculoterapia.Api.Repository
{
    public interface IDisponibilidadRepository: IRepository<Disponibilidad>
    {
        Disponibilidad guardarDisponibilidad(Disponibilidad entity);
    }
}