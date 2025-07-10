package com.conjunta.simbana;

import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.DenominacionTransaccion;
import com.conjunta.simbana.model.Enums;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimbanaApplicationTests {

	@Test
	void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }

    @Test
    void testTurnoCaja() {
        // Prueba la clase TurnoCaja
        TurnoCaja turno = new TurnoCaja("TURNO-2024-001");
        turno.setCodigoCaja("CAJA001");
        turno.setCodigoCajero("CAJ001");
        turno.setInicioTurno(LocalDateTime.now());
        turno.setMontoInicial(new BigDecimal("1000.00"));
        turno.setEstado(Enums.EstadoTurno.ABIERTO);
        
        assertEquals("TURNO-2024-001", turno.getCodigoTurno());
        assertEquals("CAJA001", turno.getCodigoCaja());
        assertEquals("CAJ001", turno.getCodigoCajero());
        assertEquals(Enums.EstadoTurno.ABIERTO, turno.getEstado());
        assertEquals(new BigDecimal("1000.00"), turno.getMontoInicial());
        assertNotNull(turno.getInicioTurno());
        assertNull(turno.getFinTurno());
    }

    @Test
    void testTransaccionTurno() {
        // Prueba la clase TransaccionTurno
        TransaccionTurno transaccion = new TransaccionTurno();
        transaccion.setCodigoTurno("TURNO-2024-001");
        transaccion.setCodigoCaja("CAJA001");
        transaccion.setCodigoCajero("CAJ001");
        transaccion.setTipoTransaccion(Enums.TipoTransaccion.DEPOSITO);
        transaccion.setMontoTotal(new BigDecimal("500.00"));
        transaccion.setFechaHora(LocalDateTime.now());
        
        assertEquals("TURNO-2024-001", transaccion.getCodigoTurno());
        assertEquals("CAJA001", transaccion.getCodigoCaja());
        assertEquals("CAJ001", transaccion.getCodigoCajero());
        assertEquals(Enums.TipoTransaccion.DEPOSITO, transaccion.getTipoTransaccion());
        assertEquals(new BigDecimal("500.00"), transaccion.getMontoTotal());
        assertNotNull(transaccion.getFechaHora());
    }

    @Test
    void testDenominacionTransaccion() {
        // Prueba la clase DenominacionTransaccion
        DenominacionTransaccion denominacion = new DenominacionTransaccion();
        denominacion.setTransaccionId(1);
        denominacion.setBillete(Enums.Denominacion.CIEN);
        denominacion.setCantidadBilletes(10);
        denominacion.setMonto(new BigDecimal("1000.00"));
        
        assertEquals(1, denominacion.getTransaccionId());
        assertEquals(Enums.Denominacion.CIEN, denominacion.getBillete());
        assertEquals(10, denominacion.getCantidadBilletes());
        assertEquals(new BigDecimal("1000.00"), denominacion.getMonto());
    }

    @Test
    void testEnums() {
        // Prueba los enums del sistema
        assertEquals("100", Enums.Denominacion.CIEN.getValor());
        assertEquals("50", Enums.Denominacion.CINCUENTA.getValor());
        assertEquals("20", Enums.Denominacion.VEINTE.getValor());
        assertEquals("10", Enums.Denominacion.DIEZ.getValor());
        assertEquals("5", Enums.Denominacion.CINCO.getValor());
        assertEquals("1", Enums.Denominacion.UNO.getValor());
        
        // Prueba conversión desde valor
        assertEquals(Enums.Denominacion.CIEN, Enums.Denominacion.fromValor("100"));
        assertEquals(Enums.Denominacion.CINCUENTA, Enums.Denominacion.fromValor("50"));
    }

    @Test
    void testCalculoMontoDenominaciones() {
        // Prueba el cálculo del monto total de denominaciones usando streams
        List<DenominacionTransaccion> denominaciones = new ArrayList<>();
        
        DenominacionTransaccion den1 = new DenominacionTransaccion();
        den1.setMonto(new BigDecimal("500.00"));
        
        DenominacionTransaccion den2 = new DenominacionTransaccion();
        den2.setMonto(new BigDecimal("300.00"));
        
        DenominacionTransaccion den3 = new DenominacionTransaccion();
        den3.setMonto(new BigDecimal("200.00"));
        
        denominaciones.add(den1);
        denominaciones.add(den2);
        denominaciones.add(den3);
        
        BigDecimal montoTotal = denominaciones.stream()
                .map(DenominacionTransaccion::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal montoEsperado = new BigDecimal("1000.00");
        assertEquals(montoEsperado, montoTotal);
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba equals y hashCode de TurnoCaja
        TurnoCaja turno1 = new TurnoCaja("TURNO-001");
        TurnoCaja turno2 = new TurnoCaja("TURNO-001");
        TurnoCaja turno3 = new TurnoCaja("TURNO-002");
        
        assertEquals(turno1, turno2);
        assertNotEquals(turno1, turno3);
        assertEquals(turno1.hashCode(), turno2.hashCode());
        assertNotEquals(turno1.hashCode(), turno3.hashCode());
    }
}
