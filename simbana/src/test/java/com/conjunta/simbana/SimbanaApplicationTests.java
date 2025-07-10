package com.conjunta.simbana;

import com.conjunta.simbana.model.DenominacionTurno;
import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.TurnoCajaId;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.TransaccionTurnoId;
import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
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
    void testTurnoCajaId() {
        // Prueba la clase TurnoCajaId
        TurnoCajaId id = new TurnoCajaId("CAJA001", "CAJERO001", "20241201");
        
        assertEquals("CAJA001", id.getCodigoCaja());
        assertEquals("CAJERO001", id.getCodigoCajero());
        assertEquals("20241201", id.getFecha());
        
        // Prueba equals y hashCode
        TurnoCajaId id2 = new TurnoCajaId("CAJA001", "CAJERO001", "20241201");
        assertEquals(id, id2);
        assertEquals(id.hashCode(), id2.hashCode());
    }

    @Test
    void testDenominacionTurno() {
        // Prueba la clase DenominacionTurno
        DenominacionTurno denominacion = new DenominacionTurno(new BigDecimal("100.00"), 10);
        
        assertEquals(new BigDecimal("100.00"), denominacion.getDenominacion());
        assertEquals(10, denominacion.getCantidad());
        assertEquals(new BigDecimal("1000.00"), denominacion.getMonto());
        
        // Prueba el cálculo automático del monto
        denominacion.setCantidad(5);
        assertEquals(new BigDecimal("500.00"), denominacion.getMonto());
    }

    @Test
    void testTurnoCaja() {
        // Prueba la clase TurnoCaja
        TurnoCajaId id = new TurnoCajaId("CAJA001", "CAJERO001", "20241201");
        TurnoCaja turno = new TurnoCaja(id, LocalDateTime.now(), new BigDecimal("1000.00"));
        
        assertEquals(id, turno.getTurnoCajaId());
        assertEquals(TurnoCaja.EstadoTurno.ABIERTO, turno.getEstado());
        assertEquals(new BigDecimal("1000.00"), turno.getMontoInicial());
        assertNotNull(turno.getFechaInicio());
        assertNull(turno.getFechaFin());
    }

    @Test
    void testTransaccionTurno() {
        // Prueba la clase TransaccionTurno
        TransaccionTurnoId id = new TransaccionTurnoId("CAJA001", "CAJERO001", "20241201", "UUID123");
        List<DenominacionTurno> denominaciones = new ArrayList<>();
        denominaciones.add(new DenominacionTurno(new BigDecimal("50.00"), 10));
        
        TransaccionTurno transaccion = new TransaccionTurno(id, TipoTransaccion.DEPOSITO, 
                                                           new BigDecimal("500.00"), denominaciones);
        
        assertEquals(id, transaccion.getTransaccionTurnoId());
        assertEquals(TipoTransaccion.DEPOSITO, transaccion.getTipoTransaccion());
        assertEquals(new BigDecimal("500.00"), transaccion.getMontoTotal());
        assertEquals(1, transaccion.getDenominaciones().size());
        assertNotNull(transaccion.getFechaTransaccion());
    }

    @Test
    void testTransaccionTurnoId() {
        // Prueba la clase TransaccionTurnoId
        TransaccionTurnoId id = new TransaccionTurnoId("CAJA001", "CAJERO001", "20241201", "UUID123");
        
        assertEquals("CAJA001", id.getCodigoCaja());
        assertEquals("CAJERO001", id.getCodigoCajero());
        assertEquals("20241201", id.getCodigoTurno());
        assertEquals("UUID123", id.getCodigoTransaccion());
        
        // Prueba equals y hashCode
        TransaccionTurnoId id2 = new TransaccionTurnoId("CAJA001", "CAJERO001", "20241201", "UUID123");
        assertEquals(id, id2);
        assertEquals(id.hashCode(), id2.hashCode());
    }

    @Test
    void testCalculoMontoDenominaciones() {
        // Prueba el cálculo del monto total de denominaciones
        TransaccionTurno transaccion = new TransaccionTurno();
        transaccion.agregarDenominacion(new DenominacionTurno(new BigDecimal("100.00"), 5));
        transaccion.agregarDenominacion(new DenominacionTurno(new BigDecimal("50.00"), 10));
        transaccion.agregarDenominacion(new DenominacionTurno(new BigDecimal("20.00"), 25));
        
        BigDecimal montoTotal = transaccion.calcularMontoTotalDenominaciones();
        BigDecimal montoEsperado = new BigDecimal("1000.00"); // 500 + 500 + 500
        
        assertEquals(montoEsperado, montoTotal);
    }
}
