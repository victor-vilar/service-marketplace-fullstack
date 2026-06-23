package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.domain.Category;
import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.RegisterDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import com.victorvilar.marketplace.fullstack.exceptions.UserNotFoundException;
import com.victorvilar.marketplace.fullstack.mappers.UserMapper;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceDefaultImplTest {

    @InjectMocks
    @Spy
    private UserServiceDefaultImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    User user1;
    User user2;
    UserDTO userDto1;
    UserDTO userDto2;

    @BeforeEach
    public void setUp(){

        user1 = new User();
        user2 = new User();

        user1.setName("user1");
        user2.setName("user2");

        user1.setEmail("user1@email.com");
        user2.setEmail("user2@email.com");

        userDto1 = UserDTO.builder()
                .name(user1.getName())
                .email(user1.getEmail())
                .build();


        userDto2 = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .name(user2.getName())
                .email(user2.getEmail())
                .build();


    }

    @Test
    void getAllDeveTrazerTodosOsUsuariosConvertidosEmDto() {

        when(repository.findAll()).thenReturn(List.of(user1,user2));
        when(mapper.toDto(any(User.class))).thenReturn(userDto1,userDto2);
        List<UserDTO> dtos = service.getAll();

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(dtos.size(),2);
        Assertions.assertEquals(dtos,List.of(userDto1,userDto2));
        verify(repository,times(1)).findAll();

    }

    @Test
    void getByIdDeveRetornarDtoDoObjetoBuscadoComSucesso() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        when(mapper.toDto(any(User.class))).thenReturn(userDto1);
        UserDTO userDto = service.getById(UUID.randomUUID());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto,userDto1);
        verify(repository,times(1)).findById(any(UUID.class));
    }

    @Test
    void getByIdDeveLançarUserNotFoundExceptionQuandoNaoEncontrarEntidade(){

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,() -> service.getById(UUID.randomUUID()));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado !");

    }


    @Test
    void saveDeveSalvarUmNovoUsuarioComSucessoCasoEleNaoTenhaUmId() {

        when(repository.save(any(User.class))).thenReturn(user1);
        when(mapper.toEntity(any(UserDTO.class))).thenReturn(user1);
        when(mapper.toDto((any(User.class)))).thenReturn(userDto1);

        UserDTO userDto = service.save(userDto1);

        verify(repository,times(1)).save(any(User.class));
        verify(service,times(0)).update(any(UserDTO.class));
        Assertions.assertEquals(userDto.getName(),userDto1.getName());
        Assertions.assertEquals(userDto.getEmail(),userDto1.getEmail());
    }

    @Test
    void saveDeveChamarOMetodoUpdateCasoOObjetoPassadoJaPossuaUmID(){

        UserDTO dto = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .name("usuario")
                .email("email")
                .phoneNumber("telefone")
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        when(repository.save(any(User.class))).thenReturn(user1);
        when(mapper.toDto((any(User.class)))).thenReturn(userDto1);

        UserDTO userDto = service.save(dto);

        verify(repository,times(1)).save(any(User.class));
        verify(service,times(1)).update(any(UserDTO.class));
        Assertions.assertEquals(userDto.getName(),userDto1.getName());
        Assertions.assertEquals(userDto.getEmail(),userDto1.getEmail());

    }

    @Test
    void saveDeUmRegisterDTODeveRetornarUmUsuarioSalvo(){
        RegisterDTO register = new RegisterDTO("teste","teste@gmail.com","123456");

        UserDTO dto = UserDTO.builder()
                .name("usuario")
                .email("email")
                .phoneNumber("telefone")
                .build();

        when(encoder.encode(any())).thenReturn("password-encoded");
        when(repository.save(any())).thenReturn(user1);
        when(mapper.toDto(any())).thenReturn(dto);
        UserDTO user = service.save(register);

        Assertions.assertEquals(user.getId(),user1.getId());

    }


    @Test
    void updateDeveAtualizarOsCamposComSucesso() {

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        when(repository.save(any(User.class))).thenReturn(user1);
        when(mapper.toDto((any(User.class)))).thenReturn(userDto1);

        UserDTO userDto = service.update(userDto2);
        verify(repository,times(1)).save(any(User.class));
        Assertions.assertEquals(userDto.getName(),userDto1.getName());
        Assertions.assertEquals(userDto.getEmail(),userDto1.getEmail());
    }

    @Test
    void updateDeveLancarErroQuandoNaoEncontrarOUsuario(){

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,() -> service.update(userDto2));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado !");

    }

    @Test
    void deleteDeveLancarErroQuandoNaoEncontrarOUsuario(){

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,() -> service.delete(UUID.randomUUID()));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado !");

    }


    @Test
    void deleteDeveDesativarAContaDoUsuario(){

        user1.setEnabled(true);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        service.delete(UUID.randomUUID());
        Assertions.assertFalse(user1.isEnabled());
        verify(repository,times(1)).save(any(User.class));
    }



    @Test
    void getAllActiveDeveRetornarTodosOsUsuariosAtivos() {
        when(repository.findAllActive()).thenReturn(List.of(user1,user2));
        when(mapper.toDto(any(User.class))).thenReturn(userDto1,userDto2);
        List<UserDTO> dtos = service.getAllActive();

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(dtos.size(),2);
        Assertions.assertEquals(dtos,List.of(userDto1,userDto2));
        verify(repository,times(1)).findAllActive();

    }

    @Test
    void getByIdWithJobs_deveFuncionarComSucessoTrazendoUsuarioComJobs() {

        when(repository.findByIdWithJobs(any(UUID.class))).thenReturn(Optional.of(user1));
        when(mapper.toDto(any(User.class))).thenReturn(userDto1);
        UserDTO dto = service.getByIdWithJobs(UUID.randomUUID()) ;

        Assertions.assertNotNull(dto);
        verify(repository,times(1)).findByIdWithJobs(any(UUID.class));
        verify(mapper,times(1)).toDto(user1);

    }

    @Test
    void getByIdWithJobsDeveLançarExceptionQuandoUserNaoForEncontrado(){
        when(repository.findByIdWithJobs(any(UUID.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,() -> service.getByIdWithJobs(UUID.randomUUID()));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado !");
    }

    @Test
    void getByIdWithOrdersDeveRetornarObjetoComSucesso() {

        when(repository.findByIdWithOrders(any(UUID.class))).thenReturn(Optional.of(user1));
        when(mapper.toDto(any(User.class))).thenReturn(userDto1);
        UserDTO dto = service.getByIdWithOrders(UUID.randomUUID()) ;

        Assertions.assertNotNull(dto);
        verify(repository,times(1)).findByIdWithOrders(any(UUID.class));
        verify(mapper,times(1)).toDto(user1);

    }

    @Test
    void getByIdWithOrdersDeveLançarExceptionQuandoUserNaoForEncontrado(){
        when(repository.findByIdWithOrders(any(UUID.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,() -> service.getByIdWithOrders(UUID.randomUUID()));
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado !");
    }

    @Test

    void addRole_deveAdicionarUmNovoRoleParaUmUsuario(){
        UserDTO dto = UserDTO.builder()
                .name("usuario")
                .email("email")
                .phoneNumber("telefone")
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        when(repository.save(any(User.class))).thenReturn(user1);
        when(mapper.toDto(any(User.class))).thenReturn(dto);
        dto = service.addRole(UUID.randomUUID(), TipoUsuario.ADMINISTRADOR);

        Assertions.assertNotNull(dto);
        Assertions.assertTrue(user1.getAuthorities().contains(new SimpleGrantedAuthority(TipoUsuario.ADMINISTRADOR.toString())));
        verify(repository,times(1)).save(user1);
        verify(mapper, times(1)).toDto(user1);
    }

    @Test
    void removeRole_deveRemoverUmRoleDoUsuario(){
        UserDTO dto = UserDTO.builder()
                .name("usuario")
                .email("email")
                .phoneNumber("telefone")
                .build();

        user1.addRole(TipoUsuario.ADMINISTRADOR);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user1));
        when(repository.save(any(User.class))).thenReturn(user1);
        when(mapper.toDto(any(User.class))).thenReturn(dto);
        dto = service.removeRole(UUID.randomUUID(), TipoUsuario.USUARIO);

        Assertions.assertNotNull(dto);
        Assertions.assertTrue(user1.getAuthorities().contains(new SimpleGrantedAuthority(TipoUsuario.ADMINISTRADOR.toString())));
        Assertions.assertFalse(user1.getAuthorities().contains(new SimpleGrantedAuthority(TipoUsuario.USUARIO.toString())));
        verify(repository,times(1)).save(user1);
        verify(mapper, times(1)).toDto(user1);
    }
}