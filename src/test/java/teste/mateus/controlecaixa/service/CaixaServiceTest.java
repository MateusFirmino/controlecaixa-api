package teste.mateus.controlecaixa.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teste.mateus.controlecaixa.controller.dtos.caixa.CriarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.caixa.RespostaCaixaDto;
import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.exception.ResourceNotFoundException;
import teste.mateus.controlecaixa.repository.CaixaRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CaixaServiceTest {

  @Mock
  private CaixaRepository caixaRepository;

  @InjectMocks
  private CaixaService caixaService;

  @Captor
  private ArgumentCaptor<Caixa> caixaArgumentCaptor;

  @Captor
  private ArgumentCaptor<Long> idArgumentCaptor;

  @Nested
  class createCaixaTest {

    @Test
    @DisplayName("Deve criar Caixa com sucesso")
    void deveCriarCaixaERetornarRespostaDto() {
      // Arrange
      CriarCaixaDto criarCaixaDto = new CriarCaixaDto("Banco Novo");
      Caixa caixaEsperada = new Caixa(criarCaixaDto);
      doReturn(caixaEsperada).when(caixaRepository).save(caixaArgumentCaptor.capture());

      // Act
      RespostaCaixaDto resposta = caixaService.criarCaixa(criarCaixaDto);
      assertNotNull(resposta);
      // Assert
      var caixaCaptured = caixaArgumentCaptor.getValue();

      assertEquals(criarCaixaDto.descricao(), caixaCaptured.getDescricao());
      verify(caixaRepository, times(1)).save(any(Caixa.class));
    }

    @Test
    @DisplayName("Jogar exceção se o erro acontecer ao criar Caixa")
    void jogarExcecaoDeErroAoCriarCaixa() {
      CriarCaixaDto criarCaixaDto = new CriarCaixaDto("Banco Novo");

      doThrow(new RuntimeException()).when(caixaRepository).save(any(Caixa.class));

      assertThrows(RuntimeException.class, () -> caixaService.criarCaixa(criarCaixaDto));
    }

  }

  @Nested
  class getCaixaById {

    @Test
    @DisplayName("Deve retornar o Caixa pelo Id com sucesso")
    void pegarCaixaPorIdComSucesso() {
      Long id = 1L;
      CriarCaixaDto criarCaixaDto = new CriarCaixaDto("Banco Novo");
      Caixa caixaEsperada = new Caixa(criarCaixaDto);
      caixaEsperada.setId(id);

      doReturn(Optional.of(caixaEsperada)).when(caixaRepository).findById(id);

      var output = caixaService.buscarPorId(id);

      assertNotNull(output);
      assertEquals(caixaEsperada.getId(), output.getId());
      assertEquals(caixaEsperada.getDescricao(), output.getDescricao());
    }

  }

  @Nested
  class listCaixas {

    @Test
    @DisplayName("Retorne todos os caixas com sucesso")
    void listCaixasComSucesso() {
      Caixa caixa1 = new Caixa(1L, "Caixa 1", BigDecimal.ZERO);
      Caixa caixa2 = new Caixa(2L, "Caixa 2", BigDecimal.ZERO);

      List<Caixa> listaCaixa = List.of(caixa1, caixa2);

      doReturn(listaCaixa).when(caixaRepository).findAll();

      var output = caixaService.listarCaixas();

      assertNotNull(output);
      assertEquals(2, output.size());
      assertEquals("Caixa 1", output.get(0).descricao());
      assertEquals("Caixa 2", output.get(1).descricao());
      verify(caixaRepository, times(1)).findAll();
    }

    @Test
    void listarCaixasDeveRetornarListaVaziaQuandoNaoExistemCaixas() {

      doReturn(Collections.emptyList()).when(caixaRepository).findAll();

      List<RespostaCaixaDto> resultado = caixaService.listarCaixas();

      assertNotNull(resultado);
      assertTrue(resultado.isEmpty());
      verify(caixaRepository, times(1)).findAll();
    }

  }

  @Nested
  class deleteById {

    @DisplayName("Deletar Caixa com Sucesso")
    @Test
    void deletarCaixaComSucesso() {
      Long id = 1L;
      doReturn(true)
        .when(caixaRepository)
        .existsById(idArgumentCaptor.capture());

      doNothing()
        .when(caixaRepository)
        .deleteById(idArgumentCaptor.capture());

      caixaService.deletar(id);

      var idList = idArgumentCaptor.getAllValues();
      assertEquals(1L, idList.get(0));
      assertEquals(1L, idList.get(1));

      verify(caixaRepository, times(1)).existsById(idList.get(0));
      verify(caixaRepository, times(1)).deleteById(idList.get(1));
    }

    @Test
    @DisplayName("Lançar ResourceNotFoundException quando o Caixa não existe")
    void deletarCaixaComFalha() {
      Long id = 1L;
      doReturn(false).when(caixaRepository).existsById(id);


      ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> caixaService.deletar(id)
      );


      assertEquals("Caixa não encontrado com id: " + id, exception.getMessage());


      verify(caixaRepository, times(1))
        .existsById(id);

      verify(caixaRepository, times(0)).deleteById(any());
    }

  }

}

//  @Nested
//  class updateUserById {
//
//    @Test
//    @DisplayName("Should update user by id when user exists and username and password is filled")
//    void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordIsFilled() {
//
//
//    }
//
//  }

