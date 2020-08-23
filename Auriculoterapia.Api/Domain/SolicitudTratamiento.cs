namespace Auriculoterapia.Api.Domain
{
    public class SolicitudTratamiento
    {
        public int Id {get; set;}
        
        public int Edad {get; set;}
        public float Peso {get; set;}
        public float Altura {get; set;}
        public string Sintomas {get; set;}
        public string ImagenAreaAfectada {get;set;}
        public string OtrosSintomas {get; set;}

        public int PacienteId {get; set;}
        public Paciente Paciente {get; set;}
    }
}