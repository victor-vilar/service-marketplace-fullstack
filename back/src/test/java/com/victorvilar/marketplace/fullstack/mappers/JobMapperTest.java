package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.Category;
import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobMapperTest {

    private JobMapper mapper;

    @Autowired
    public JobMapperTest(JobMapper mapper){
        this.mapper = mapper;
    }

    @Test
    void toEntityDeveCriarUmaNovaEntidadeComOsDados() {
        JobDTO dto = JobDTO
                .builder()
                .id(UUID.randomUUID().toString())
                .title("Isso é um teste")
                .description("Isso é um teste")
                .category("Essa é a categoria")
                .provider("Esse é o provedor")
                .build();

        Job job = mapper.toEntity(dto);

        Assertions.assertNull(job.getId());
        Assertions.assertEquals(dto.getTitle(),job.getTitle());
        Assertions.assertEquals(dto.getDescription(),job.getDescription());
        Assertions.assertEquals(dto.getPrice(),job.getPrice());
        Assertions.assertNull(job.getCategory());
        Assertions.assertNull(job.getProvider());
    }

    @Test
    void toDtoDeveCopiarDadosInclusiveIdEPegarOIdDasEntidadesRelacionadasParaColocarNoDTO() {
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setTitle("titulo");
        job.setDescription("descrição");
        job.setPrice(BigDecimal.TEN);
        job.setCategory(new Category("Teste"));
        User user = new User();
        user.setId(UUID.randomUUID());
        job.setProvider(user);

        JobDTO dto = mapper.toDto(job);

        assertEquals(job.getId().toString(),dto.getId());
        assertEquals(job.getProvider().getId().toString(),dto.getProvider());
        assertEquals(job.getTitle(),dto.getTitle());
        assertEquals(job.getDescription(),dto.getDescription());
        assertEquals(job.getPrice(),dto.getPrice());
        assertEquals(job.getCategory().getName(),dto.getCategory());

    }

    @Test
    void copyNaoDeveConseguirCopiarIdQuandoAFonteEUmDTO() {

        Job job1 = new Job();
        JobDTO jobDTO = new JobDTO();
        job1.setId(UUID.randomUUID());
        jobDTO.setId(UUID.randomUUID().toString());

        mapper.copy(jobDTO,job1);
        assertNotEquals(jobDTO.getId(),job1.getId().toString());
    }

    @Test
    void copyDeveCopiarTodosOsDadosExcetoOIdEOsRelacionamentosDeUmDtoParaUmJob(){

        JobDTO dto = JobDTO
                .builder()
                .id(UUID.randomUUID().toString())
                .title("Isso é um teste")
                .description("Isso é um teste")
                .price(BigDecimal.valueOf(100))
                .category("Essa é a categoria")
                .provider("Esse é o provedor")
                .build();

        Job job = new Job();
        job.setTitle("");
        job.setDescription("");

        mapper.copy(dto,job);

        Assertions.assertNull(job.getId());
        Assertions.assertEquals(dto.getTitle(),job.getTitle());
        Assertions.assertEquals(dto.getDescription(),job.getDescription());
        Assertions.assertEquals(dto.getPrice(),job.getPrice());
        Assertions.assertNull(job.getCategory());
        Assertions.assertNull(job.getProvider());
    }

}