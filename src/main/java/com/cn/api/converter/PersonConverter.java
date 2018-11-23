package com.cn.api.converter;

import com.cn.api.entity.Person;
import com.cn.api.manager.EntityManager;
import com.cn.api.resource.PersonDto;
import com.cn.api.util.ClassUtils;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter extends AbstractConverter<Person, PersonDto>{


    @Override
    public PersonDto map(EntityManager entityManager, Object var2) {
        PersonDto dto = new PersonDto();
        Person entity = (Person) entityManager.find(var2);
        ClassUtils.setValue(entity, dto);
        return dto;
    }

    @Override
    public Person unmap(PersonDto personDto) {

        return null;
    }

}
