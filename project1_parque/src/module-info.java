/**
 *
 */
/**
 *
 */
module project1_parque {
    requires com.google.gson;
	requires org.junit.jupiter.api; // 
	requires java.desktop;
	requires com.google.zxing;
    
    
    opens CONTROL_PERSISTENCIA to com.google.gson;
    opens sistema_parque.atracciones to com.google.gson;
    opens sistema_parque.lugaresServicio to com.google.gson;
    opens sistema_parque.sisParque to com.google.gson;
    opens sistema_parque.tiquetes to com.google.gson;
    opens sistema_parque.usuarios to com.google.gson;
    

}