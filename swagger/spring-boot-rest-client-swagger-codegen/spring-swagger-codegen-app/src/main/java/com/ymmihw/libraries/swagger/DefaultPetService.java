package com.ymmihw.libraries.swagger;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ymmihw.petstore.client.api.PetApi;
import com.ymmihw.petstore.client.model.Pet;

@Service
public class DefaultPetService implements PetService {

  @Autowired
  private PetApi petApi;

  @Override
  public List<Pet> findAvailablePets() {
    return petApi.findPetsByStatus(Arrays.asList("available"));
  }

  @Override
  public List<Pet> findPendingPets() {
    return petApi.findPetsByStatus(Arrays.asList("pending"));
  }

  @Override
  public List<Pet> findSoldPets() {
    return petApi.findPetsByStatus(Arrays.asList("sold"));
  }

}
