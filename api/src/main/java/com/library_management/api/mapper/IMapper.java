package com.library_management.api.mapper;

public interface IMapper<Entity, Req , Res> {
    Entity reqToEntity(Req req);
    Res entityToRes(Entity e);
}
