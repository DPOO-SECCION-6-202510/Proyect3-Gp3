package project1_parqueUsuarios.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList; 

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Usuario;
import sistema_parque.tiquetes.*;
import java.util.Date;


public class EmpleadoTest {
	
	private Empleado empleado;
	
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	empleado = new Empleado();
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    void getDESCUENTOCOMPRA() {
        assertEquals(1, Empleado.getDESCUENTOCOMPRA(), "Un error ha sucedido" );

    	
    }

}
