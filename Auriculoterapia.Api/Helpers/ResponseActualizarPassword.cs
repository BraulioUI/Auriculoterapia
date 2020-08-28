namespace Auriculoterapia.Api.Helpers
{
    public class ResponseActualizarPassword
    {


        public string nombreUsuario{get;set;}
        public string PalabraClave {get; set;}
        public string Contrasena {get; set;}

        public ResponseActualizarPassword(string nombreUsuario,string PalabraClave, string Contrasena){
            this.nombreUsuario = nombreUsuario;
            this.PalabraClave = PalabraClave;
            this.Contrasena = Contrasena;
        }
    }
}