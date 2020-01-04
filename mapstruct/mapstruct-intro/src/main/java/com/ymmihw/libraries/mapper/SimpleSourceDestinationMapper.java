package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import com.ymmihw.libraries.model.SimpleDestination;
import com.ymmihw.libraries.model.SimpleSource;

@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {

  SimpleDestination sourceToDestination(SimpleSource source);

  SimpleSource destinationToSource(SimpleDestination destination);

}
