package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.OrderDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class UserMapperTest {

    private UserMapper mapper;
    private ModelMapper modelMapper;

    @Autowired
    public UserMapperTest(UserMapper mapper, ModelMapper modelMapper) {
        this.mapper = mapper;
        this.modelMapper = modelMapper;
    }

    UserDTO dto;
    User user;

    @Test
    void deveCopiarOsDadosDoDtoParaAEntitySemAlterarOID() {

        dto = new UserDTO(UUID.randomUUID().toString(), "Astolfo", "astolfo@gmail.com", "123", null, null, null);

        user = new User();
        user.setName("Ipsum");
        user.setEmail("ipsum@hotmail;.com");
        user.setPassword("1111");
        user.setPhoneNumber("987654321");

        mapper.copyData(dto, user);


        Assertions.assertNull(user.getId());
        Assertions.assertEquals(user.getName(), dto.getName());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getPassword(), dto.getPassword());
        Assertions.assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    void deveCriarUmNovoDTOComAEntiduadePassada() {
        user = new User();
        user.setName("Ipsum");
        user.setEmail("ipsum@hotmail.com");
        user.setPhoneNumber("987654321");

        UserDTO dto = mapper.toDto(user);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getName(), dto.getName());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    void deveIgnorarAPropriedadeSenhaQuandoMapeaDeUserParaUserDTO() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Ipsum");
        user.setEmail("ipsum@hotmail.com");
        user.setPhoneNumber("987654321");
        UserDTO dto = mapper.toDto(user);
        Assertions.assertNull(dto.getPassword());
    }

    @Test
    void deveCriarUmNovoUserCopiandoDto() {

        dto = new UserDTO(UUID.randomUUID().toString(), "Astolfo", "astolfo@gmail.com", "123", null, null, null);
        user = mapper.toEntity(dto);

        Assertions.assertNull(user.getId());
        Assertions.assertEquals(user.getName(), dto.getName());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());

    }

    @Test
    void mapperDeeveTransformarListasDeUserEmSuasVersoesDTO() {

        User user1 = new User();
        User user2 = new User();

        Job job1 = new Job();
        Job job2 = new Job();

        Order order1 = new Order();

        user1.setName("user1");
        user2.setName("user2");

        user1.setEmail("user1@email.com");
        user2.setEmail("user2@email.com");

        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());

        job1.setId(UUID.randomUUID());
        job2.setId(UUID.randomUUID());

        order1.setId(UUID.randomUUID());

        job1.setProvider(user1);
        job2.setProvider(user1);

        job1.setTitle("Isso é um teste");
        job2.setTitle("Esse é outro teste");

        order1.setCustomer(user2);
        order1.setJob(job1);

        UserDTO dto = mapper.toDto(user1);
        Assertions.assertEquals(dto.getJobs().get(0).getTitle(), user1.getJobs().get(0).getTitle());
        Assertions.assertEquals(dto.getJobs().get(0).getProvider(), user1.getId().toString());
        Assertions.assertEquals(dto.getJobs().get(0).getOrders().size(), 1);
        OrderDTO orderDTO = dto.getJobs().get(0).getOrders().get(0);
        Assertions.assertEquals(orderDTO.getId(), order1.getId().toString());
        Assertions.assertEquals(orderDTO.getId(), user2.getOrders().get(0).getId().toString());


    }


}