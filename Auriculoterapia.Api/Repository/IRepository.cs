using System;
using System.Collections.Generic;
namespace Auriculoterapia.Api.Repository
{
    public interface IRepository<T>
    {
        void Save(T entity);
        IEnumerable<T> FindAll();

    }
}
