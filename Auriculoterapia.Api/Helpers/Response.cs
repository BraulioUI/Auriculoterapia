namespace Auriculoterapia.Api.Helpers
{
    public class Response
    {
        public string Token {get; set;}
        public string Rol {get; set;}

        public Response(string Token, string Rol){
            this.Token = Token;
            this.Rol = Rol;
        }
    }
}