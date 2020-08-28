namespace Auriculoterapia.Api.Helpers
{
    public class Response
    {
        public int Id {get; set;}
        public string Token {get; set;}
        public string Rol {get; set;}

        public Response(int Id, string Token, string Rol){
            this.Id = Id;
            this.Token = Token;
            this.Rol = Rol;
        }
    }
}