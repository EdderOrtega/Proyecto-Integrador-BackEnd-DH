package com.dh.Integrador;

import com.dh.Integrador.entity.Odontologo;
import com.dh.Integrador.repository.IOdontologoRepository;
import com.dh.Integrador.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;



@SpringBootTest
public class IntegradorApplicationTests {

	@Mock
	private IOdontologoRepository odontologoRepository;

	@InjectMocks
	private OdontologoService odontologoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testEliminarOdontologo() {
		doNothing().when(odontologoRepository).deleteById(1);

		odontologoService.eliminarOdontologo(1);

		verify(odontologoRepository, times(1)).deleteById(1);
	}

    @Test
    public void testActualizarOdontologo() {

        Odontologo odontologoExistente = new Odontologo(1, "lopez", "adrian", "333333344");
        Odontologo odontologoActualizado = new Odontologo(1, "martinez", "sandra", "4434455555");

        when(odontologoRepository.existsById(odontologoExistente.getId())).thenReturn(true);
        when(odontologoRepository.save(odontologoActualizado)).thenReturn(odontologoActualizado);

        Odontologo resultado = odontologoService.actualizarOdontologo(odontologoActualizado);

        assertNotNull(resultado, "El odontólogo actualizado no debería ser nulo");
        assertEquals(odontologoActualizado, resultado, "El odontólogo retornado debería ser el actualizado");
    }
	@Test
	void testBuscarPorId() {
		Odontologo odontologo = new Odontologo();
		odontologo.setId(1);
		odontologo.setApellido("Gomez");
		odontologo.setNombre("Juan");
		odontologo.setNroMatricula("123456");

		when(odontologoRepository.findById(1)).thenReturn(Optional.of(odontologo));

		Optional<Odontologo> result = odontologoService.buscarPorId(1);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
		assertEquals("Gomez", result.get().getApellido());
		assertEquals("Juan", result.get().getNombre());
		assertEquals("123456", result.get().getNroMatricula());
	}
}

