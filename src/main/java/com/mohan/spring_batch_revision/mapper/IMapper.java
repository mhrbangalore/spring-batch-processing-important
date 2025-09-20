package com.mohan.spring_batch_revision.mapper;

public interface IMapper<A, B> {

    A mapFrom(B b);

    B mapTo(A a);

}
