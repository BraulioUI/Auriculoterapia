using System.Collections.Generic;

using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Repository
{
    public interface ICitaRepository: IRepository<Cita>
    {
        IEnumerable<Cita> listarCitasPorUsuarioId(int usuarioId);
       
    }
}
