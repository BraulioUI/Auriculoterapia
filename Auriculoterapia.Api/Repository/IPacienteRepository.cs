using System;
using Auriculoterapia.Api.Domain;
namespace Auriculoterapia.Api.Repository
{
    public interface IPacienteRepository: IRepository<Paciente>
    {
       string ActualizarNumeroPaciente(string numero, Paciente paciente);
    }
}
