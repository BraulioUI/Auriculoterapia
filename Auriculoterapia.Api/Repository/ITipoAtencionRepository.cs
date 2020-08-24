using System;
using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Repository
{
    public interface ITipoAtencionRepository: IRepository<TipoAtencion>
    {
        TipoAtencion FindByDescription(string Description);
    }
}
