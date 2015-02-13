/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }
    
    /**
     * Constructor al que se le introduce el archivo a analizar
     */
    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        reader =  new LogfileReader(filename);
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while( hour < hourCounts.length)
        {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }
        //         for(int hour = 0; hour < hourCounts.length; hour++) {
        //             System.out.println(hour + ": " + hourCounts[hour]);
        //         }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    /**
     * Metodo que devuelve el número total de accesos al servidor web
     * registrados en el archivo de log. 
     */
    public int numberOfAccesses()
    {
        int total = 0;    //variable para almacenar el total de registros
        
        for(int hour= 0;hour<hourCounts.length;hour++) //recorremos la coleccion  
        {
            total += hourCounts[hour];   //sumamos al total la cantidad de registros
                                         //que hay en cada hora
        }
        
        return total;    
    }
    
    /**
     * Metodo que devuelva en qué hora el servidor tuvo que responder a más peticiones.
     */
    public int busiestHour()
    {
        int temp= 0;    //almacena el numero de accesos registrados
        int horaMasOcupada = 0;   //almancena la hora a la cual hay mas accesos al servidor
        for(int hour=0; hour<hourCounts.length; hour++)  //recorremos la coleccion
        {
                 if(temp< hourCounts[hour])  //si en la hora analizada hay mas accesos que los que
                                             // tenemos almacenados
                 {
                     horaMasOcupada= hour;   //guardamos la hora en la que hay mas accesos
                     temp= hourCounts[hour];  //almacenamos el mayor numero de accesos registrados
                 }
        }
        
        return horaMasOcupada;
    }
    
    /**
     * Metodo  que devuelva la hora a la que el servidor estuvo menos sobrecargado. 
     */
    public int quietestHour()
    {
        int horaMenosSobrecargado= 0;  //almacena la hora a la cual hay menos accesos registrados
        int temp = hourCounts[0];      //almacenamos los accesos del primer objeto de la coleccion
        for(int hour= 0; hour<hourCounts.length;hour++)
        {
            
            if(temp> hourCounts[hour])   //si la cantidad de accesos registrados almacenada es
                                         //mayor que los accesos de la hora comparada
            {
                horaMenosSobrecargado = hour;   //almacenamos la hora
                temp = hourCounts[hour];        //almacenamos la cantidad con menor accesos
            }
            
        }
        return horaMenosSobrecargado;
    }
    
    /**
     * Metodo que calcule el período de dos horas consecutivas con más carga del día 
     * y devuelva un entero con la primera hora de dicho periodo
     */
    public int periodoMasSobrecargado()
    {
        int total = 0; // almacena la suma de accesos de dos horas consecutivas
        int masSobrecargado = 0;    //indica el periodo mas sobrecargado
        
        for(int hour =0 ; hour<(hourCounts.length -1); hour++)
        {
            if(total < hourCounts[hour]+hourCounts[hour+1])
            {
                masSobrecargado= hour;
                total = hourCounts[hour] + hourCounts[hour+1];
            }
        }
        return masSobrecargado;
    }
}
