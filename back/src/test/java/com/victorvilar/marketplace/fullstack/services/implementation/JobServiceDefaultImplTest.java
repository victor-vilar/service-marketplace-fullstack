package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.domain.*;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;
import com.victorvilar.marketplace.fullstack.mappers.JobMapper;
import com.victorvilar.marketplace.fullstack.repositories.JobRepository;
import com.victorvilar.marketplace.fullstack.services.interfaces.JobService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceDefaultImplTest {

    @InjectMocks
    @Spy
    private JobServiceDefaultImpl service;

    @Mock
    private JobRepository repository;

    @Mock
    private JobMapper mapper;

    User user1;
    User user2;
    User user3;

    Job job1;
    Job job2;
    Job job3;

    Order order1;
    Order order2;

    @BeforeEach
    public void SetUp() {
        setUsers();
        setJobs();
        setOrders();
    }

    @Test
    public void getAllDeveTrazerTodosOsJobs() {

        job1.setActive(true);
        job2.setActive(false);
        job3.setActive(false);

        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findAll()).thenReturn(List.of(job1, job2, job3));
        when(mapper.toDto(job1)).thenReturn(dto1);
        when(mapper.toDto(job2)).thenReturn(dto2);
        when(mapper.toDto(job3)).thenReturn(dto3);

        List<JobDTO> jobs = service.getAll();

        Assertions.assertEquals(jobs.size(), 3);
        Assertions.assertTrue(jobs.get(0).isActive());
        Assertions.assertFalse(jobs.get(1).isActive());
        Assertions.assertFalse(jobs.get(2).isActive());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getByActiveDeveTrazerDeAcordoComBoleanoPassado(){

        job1.setActive(true);
        job2.setActive(false);
        job3.setActive(false);

        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findByActive(true)).thenReturn(List.of(job1));
        when(repository.findByActive(false)).thenReturn(List.of(job2,job3));
        when(mapper.toDto(job1)).thenReturn(dto1);
        when(mapper.toDto(job2)).thenReturn(dto2);
        when(mapper.toDto(job3)).thenReturn(dto3);

        List<JobDTO> actives = service.getByActive(true);
        List<JobDTO> inactives = service.getByActive(false);

        Assertions.assertNotNull(actives);
        Assertions.assertNotNull(inactives);

        Assertions.assertEquals(actives.size(),1);
        Assertions.assertEquals(inactives.size(),2);


    }

    @Test
    public void deveTrazerOJobDeAcordoComOIdDoProvider(){
        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findByProviderId(user1.getId())).thenReturn(List.of(job1,job2,job3));
        when(mapper.toDto(job1)).thenReturn(dto1);
        when(mapper.toDto(job2)).thenReturn(dto2);
        when(mapper.toDto(job3)).thenReturn(dto3);

        List<JobDTO> joba = service.getByProvider(user1.getId());
        Assertions.assertEquals(joba.size(),3);

        job2.setProvider(user2);
        job3.setProvider(user3);

        when(repository.findByProviderId(user2.getId())).thenReturn(List.of(job2));
        List<JobDTO> jobb = service.getByProvider(user2.getId());

        when(repository.findByProviderId(user3.getId())).thenReturn(List.of(job3));
        List<JobDTO> jobc= service.getByProvider(user3.getId());

        Assertions.assertEquals(jobb.size(),1);
        Assertions.assertEquals(jobc.size(),1);


    }

    @Test
    public void getByCategory(){
        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");

        cat1.setId(UUID.randomUUID());
        cat2.setId(UUID.randomUUID());

        job1.setCategory(cat1);
        job2.setCategory(cat1);
        job3.setCategory(cat2);

        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findByCategoryName("cat1")).thenReturn(List.of(job1,job2));
        when(repository.findByCategoryName("cat2")).thenReturn(List.of(job3));
        when(mapper.toDto(job1)).thenReturn(dto1);
        when(mapper.toDto(job2)).thenReturn(dto2);
        when(mapper.toDto(job3)).thenReturn(dto3);

        List<JobDTO> joba = service.getByCategory("cat1");
        Assertions.assertEquals(joba.size(),2);

        List<JobDTO> jobb = service.getByCategory("cat2");
        Assertions.assertEquals(jobb.size(),1);
    }

    @Test
    public void getById(){

        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");

        cat1.setId(UUID.randomUUID());
        cat2.setId(UUID.randomUUID());

        job1.setCategory(cat1);
        job2.setCategory(cat1);
        job3.setCategory(cat2);

        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findById(job1.getId())).thenReturn(Optional.of(job1));
        when(repository.findById(job2.getId())).thenReturn(Optional.of(job2));
        when(repository.findById(job3.getId())).thenReturn(Optional.of(job3));
        when(mapper.toDto(job1)).thenReturn(dto1);
        when(mapper.toDto(job2)).thenReturn(dto2);
        when(mapper.toDto(job3)).thenReturn(dto3);

        JobDTO resdto1 = service.getById(job1.getId());
        JobDTO resdto2 = service.getById(job2.getId());
        JobDTO resdto3 = service.getById(job3.getId());

        Assertions.assertEquals(resdto1.getId(),dto1.getId());
        Assertions.assertEquals(resdto2.getId(),dto2.getId());
        Assertions.assertEquals(resdto3.getId(),dto3.getId());

    }

    @Test
    public void updateAtualizaOsDados(){
        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");

        cat1.setId(UUID.randomUUID());
        cat2.setId(UUID.randomUUID());

        job1.setCategory(cat1);
        job2.setCategory(cat1);
        job3.setCategory(cat2);

        JobDTO dto1 = createDTO(job1);
        JobDTO dto2= createDTO(job2);
        JobDTO dto3= createDTO(job3);

        when(repository.findById(UUID.fromString(dto1.getId()))).thenReturn(Optional.of(job1));
        when(repository.save(any(Job.class))).thenReturn(job1);
        when(mapper.toDto(job1)).thenReturn(dto1);

        JobDTO dtoa = service.update(dto1);
        Assertions.assertEquals(dto1.getId(),dtoa.getId());
    }

    @Test
    public void deleteDeveDesativarUmJob(){
        Category cat1 = new Category("cat1");
        cat1.setId(UUID.randomUUID());
        job1.setCategory(cat1);
        JobDTO dto1 = createDTO(job1);

        when(repository.findById(UUID.fromString(dto1.getId()))).thenReturn(Optional.of(job1));
        when(repository.save(any(Job.class))).thenReturn(job1);

        service.delete(job1.getId());

        verify(repository,times(1)).save(any(Job.class));

    }


    public void setUsers() {
        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setId(UUID.randomUUID());
        user1.setEnabled(true);
        user1.setName("Lorem");
        user1.setPassword("sdff");
        user1.setEmail("lorem1@gmail.com");

        user2.setId(UUID.randomUUID());
        user2.setEnabled(true);
        user2.setName("Ipsum");
        user2.setPassword("sdff");
        user2.setEmail("ipsum@gmail.com");

        user3.setId(UUID.randomUUID());
        user3.setEnabled(true);
        user3.setName("LorIp");
        user3.setPassword("sdff");
        user3.setEmail("lorem3@gmail.com");
    }

    public void setJobs() {

        job1 = new Job();
        job2 = new Job();
        job3 = new Job();

        job1.setId(UUID.randomUUID());
        job2.setId(UUID.randomUUID());
        job3.setId(UUID.randomUUID());

        job1.setTitle("Detetização");
        job2.setTitle("Lavagem Automotiva");
        job3.setTitle("Diarista");

        job1.setDescription("detetização de pragas e vetores");
        job2.setDescription("lavagem de carros");
        job3.setDescription("diarista");

        job1.setPrice(BigDecimal.valueOf(100));
        job2.setPrice(BigDecimal.valueOf(100));
        job3.setPrice(BigDecimal.valueOf(1000));

        job1.setProvider(user1);
        job2.setProvider(user1);
        job3.setProvider(user1);

    }

    void setOrders() {
        order1 = new Order();
        order2 = new Order();


        order1.setOrderStatus(OrderStatus.ABERTA);
        order2.setOrderStatus(OrderStatus.CONCLUIDO);

        order1.setTotalAmount(BigDecimal.valueOf(1000));
        order2.setTotalAmount(BigDecimal.valueOf(1000));

        Payment p1 = new Payment();
        Payment p2 = new Payment();

        p1.setPaymentStatus(PaymentStatus.ABERTA);
        p2.setPaymentStatus(PaymentStatus.CONCLUIDO);

        p1.setPaymentMethod(PaymentMethod.BOLETO);
        p2.setPaymentMethod(PaymentMethod.PIX);

        order1.setPayment(p1);
        order2.setPayment(p2);


    }

    private JobDTO createDTO(Job job) {
        return JobDTO
                .builder()
                .id(job.getId().toString())
                .title(job.getTitle())
                .description(job.getDescription())
                .price(job.getPrice())
                .active(job.isActive())
                .provider(job.getProvider().getId().toString())
                .category(job.getCategory().getId().toString())
                .build();

    }
}