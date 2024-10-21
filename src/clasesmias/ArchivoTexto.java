/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasesmias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author SEVEN
 */
public class ArchivoTexto {
    public void Escribir(File fFichero,String cadena)
    {
        // Declaramos un buffer de escritura
        BufferedWriter bw;
        boolean crearLinea;

        try
        {
            // Comprobamos si el archivo no existe y si es asi creamos uno nuevo.
         crearLinea = true;
         if(!fFichero.exists())
         {
             fFichero.createNewFile();
             crearLinea = false;
         }

           // Luego de haber creado el archivo procedemos a escribir dentro de el.
            bw = new BufferedWriter(new FileWriter(fFichero,true));
            if (crearLinea){
                bw.newLine();
            }
            bw.write(cadena);
            bw.close();

        }catch(Exception e)
        {
            System.out.println(e);
        }

    }
    
    public boolean copiarArch(File archivoEntrada, File archivoSalida, boolean devMensaje){        
        boolean r = false;
        try {
            FileInputStream fis = new FileInputStream(archivoEntrada);
            FileOutputStream fos = new FileOutputStream(archivoSalida);
            FileChannel canalEntrada = fis.getChannel();
            FileChannel canalSalida = fos.getChannel();

            canalEntrada.transferTo(0, canalEntrada.size(), canalSalida);

            fis.close();
            fos.close();
            r = true;
            //System.out.println("Archivo copiado exitosamente.");
        } catch (IOException e) {
            if (devMensaje){
                JOptionPane.showMessageDialog(null, "Error al copiar archivo. Verifique que el archivo destino no esté abierto.\n\n" + e);
            }                        
        }
        
        return r;        
    }
    
    
    
    
    
    

    /***************************************************************
     * El metodo "Borrar" como su nombre lo indica, nos ayuda a
     * borrar un fichero previamente creado, este metodo cuenta con
     * un parametro, el cual es el nombre del fichero que deseamos
     * borrar
    * ***************************************************************/
    public void borrar (File Ffichero)
    {
        try
        {
           // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
            if(Ffichero.exists())
            {
                Ffichero.delete();
                //System.out.println("Ficherro Borrado");
            }

        }catch(Exception e)
        {
            //System.out.println(e);
        }
    }

    /***************************************************************
     * Metodo "Modificar", este cuenta con tres parametros que debemos
     * pasarle para su correcto funcionamiento, los cuales son:
     *
     * fAntiguo: Este nos ayuda a saber cual es y donde esta el archivo
     * que deseamos modificar
     *
     * aCadena: Aqui debemos especificar la cadena de caracteres que
     * deseamos modificar o cambiar
     *
     * nCadena: Por ultimo escribimos el nuevo dato que va a sustituir
     * el existente.
    * ******************************************************************/



    public void modificar(File fAntiguo,String aCadena,String nCadena)
    {
       /*
            Las dos lienas de codigo siguientes, basicamente lo que hacen es generar un numero aleatorio y
            asignarselos a una nueva variable "nFnuevo" (Nombre Fichero Nuevo) la cual es igual a la ruta
            del directorio padre "fAntiguo" mas  la palabra auxiliar seguido del numero aletorio y la extension
            del archivo nuevo
       * */
        Random numaleatorio = new Random(3816L);
        String nFnuevo = fAntiguo.getParent()+"/auxiliar"+String.valueOf(Math.abs(numaleatorio.nextInt()))+".txt";

     // Creo un nuevo archivo
        File fNuevo= new File(nFnuevo);
        // Declaro un nuevo buffer de lectura
        BufferedReader br;
        try
        {
            /*Valido si el fichero antiguo que pasamos como parametro existe, si es asi procedo a leer el
            contenido que hay dentro de el
             */

            if(fAntiguo.exists())
            {
                br = new BufferedReader(new FileReader(fAntiguo));

                String linea;

                /* Mientras el contenido del archivo sea diferente de null procedo a comprar  la linea a modificar con
                lo que hay dentro del archivo, si linea es igual a aCadena escribo el contenido de aCadena en mi nuevo
                fichero(Auxiliar) de lo contrario escribo el mismo contenido que ya tenia el antiguo fichero en mi fichero auxiliar

                 */
                while((linea=br.readLine()) != null)
                {
                    if(linea.equals(aCadena))
                    {
                        Escribir(fNuevo,nCadena);

                    }
                    else
                    {
                        Escribir(fNuevo,linea);
                    }
                }

              // Cierro el buffer de lectura
                br.close();

                // Capturo el nombre del fichero antiguo
                String nAntiguo = fAntiguo.getName();
                // Borro el fichero antiguo
                borrar(fAntiguo);
                //Renombro el fichero auxiliar con el nombre del fichero antiguo
                fNuevo.renameTo(fAntiguo);




            }
            else
            {
                System.out.println("Fichero no Existe");
            }

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    public ArrayList lineasArch(String nomArch){
        
            
        ArrayList li = new ArrayList();
        
        File f = new File(nomArch);  //inicia en la carpeta que esta trabjando
        try {
            
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader (new InputStreamReader (new FileInputStream (f), "utf-8"));                                              
            String linea = "";
            
            
            while((linea=br.readLine())!=null){
            
                li.add(linea);
                //RESULTADO = RESULTADO + linea + "\n";
                 
                                
            }  
            
                                    
        }
        catch (FileNotFoundException e) {            
            JOptionPane.showMessageDialog(null, "No se encontró el archivo");        
            li.clear();
            return li;
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer archivo");
            li.clear();
            return li;
        }
        
        return li;
    
    
    }
}
