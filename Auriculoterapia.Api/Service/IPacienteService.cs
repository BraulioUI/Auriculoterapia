using System;
using Auriculoterapia.Api.Domain;
using System.Collections.Generic;
namespace Auriculoterapia.Api.Service
{
    public interface IPacienteService: IService<Paciente>
    {
        IEnumerable<Paciente> buscarPorPalabra(string palabra);
    }
}