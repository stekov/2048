// interface �r en abstrakt klass. (metoderna ska vara tomma)
// Br�dspel �r ju mer abstrakt �n just Femtonspelet, d�rav m�ste metoderna implementeras
// Skapa inte objekt, anv�nd bara metoderna

public interface Boardgame {
    public boolean move(String direction);   // ger true om draget gick bra, annars false
    public String getStatus(int i, int j);   // returnera inneh�ll p� ruta (i,j)
    public String getMessage();              // returnera OK (eller liknande) eller felmeddelande avseende senaste drag
}
