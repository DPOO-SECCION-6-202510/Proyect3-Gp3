package CONTROL_PERSISTENCIA;

import sistema_parque.sisParque.PrincipalParque;

public interface PersistenciaParque {
	void cargarParque(String archivo, PrincipalParque parque);
	void salvarParque(String archivo, PrincipalParque parque);
}
