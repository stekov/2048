// interface är en abstrakt klass. (metoderna ska vara tomma)
// Brädspel är ju mer abstrakt än just Femtonspelet, därav måste metoderna implementeras
// Skapa inte objekt, använd bara metoderna

public interface Boardgame {
    public boolean move(String direction);   // ger true om draget gick bra, annars false
    public String getStatus(int i, int j);   // returnera innehåll på ruta (i,j)
    public String getMessage();              // returnera OK (eller liknande) eller felmeddelande avseende senaste drag
}
