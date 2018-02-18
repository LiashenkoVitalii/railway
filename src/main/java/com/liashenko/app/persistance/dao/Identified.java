package com.liashenko.app.persistance.dao;

import java.io.Serializable;

//The interface for objects which have identifier
public interface Identified<PK extends Serializable> {

    //Returns PK of the row (entity)
    PK getId();
}
