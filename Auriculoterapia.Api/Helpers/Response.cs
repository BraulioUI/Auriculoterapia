namespace Auriculoterapia.Api.Helpers
{
    public class Response
    {
        
        public int Id {get;set;}

        public string nombreUsuario{get;set;}
        public string Token {get; set;}
        public string Rol {get; set;}

        public Response(int Id, string nombreUsuario,string Token, string Rol){
            this.Id = Id;
            this.nombreUsuario = nombreUsuario;
            this.Token = Token;
            this.Rol = Rol;
        }
    }
}