
using System.Collections.Generic;
using Auriculoterapia.Api.Domain;
using Auriculoterapia.Api.Helpers;

namespace Auriculoterapia.Api.Repository
{
    public interface IDisponibilidadRepository: IRepository<Disponibilidad>
    {
        Disponibilidad guardarDisponibilidad(Disponibilidad entity);

        Disponibilidad listarPorFecha(string fecha);

        AvailabilityTimeRange obtenerListaHorasDisponibles(string fecha);
    }
}