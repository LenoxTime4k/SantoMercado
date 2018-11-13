<%-- 
    Document   : formularioUsuario
    Created on : 11-11-2018, 19:54:04
    Author     : Kevin_PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Usuario</title>
    </head>
    <style>
        body{
            background-color: lightblue;
        }
        h1{
            color: white;
            text-align: center;
        }
        
        
    </style>
    
    <body>
        
        <h1>Registrar Usuario</h1>
        <br>
        <br>
        <br>
        <br>
        
        <center>
        <div >
            <form action="GuardarUsuario" method="post">
                <table>
                    <tr>
                        <td>Nombre Usuario</td>
                        <td><input type="text" name="nombreUsuario" placeholder="Usuario" requerid></td>
                    </tr> <br>
                    <tr>
                        <td>Contraseña</td>
                        <td><input type="text" name="contraseña" placeholder="Contraseña" requerid></td>
                    </tr>
                    <tr>
                        <td>Nombre Completo</td>
                        <td><input type="text" name="nombre" placeholder="Nombre completo" requerid></td>
                    </tr>
                    <tr>
                        <td>Rut</td>
                        <td><input type="text" name="rut" placeholder="12345678-9" requerid></td>
                    </tr>
                    <tr>
                        <td>Comuna Residencia</td>
                        <td><input type="text" name="comuna" placeholder="Comuna" requerid></td>
                    </tr>
                    <tr>
                        <td>Sede Institucion</td>
                        <td> <select name="sede" >
                        <option value="opcion" selected>Eliga una opcion</option>
                        <option value="arica">Arica</option>
                        <option value="iquique">Iquique</option>
                        <option value="antofagasta">Antofagasta</option>
                        <option value="copiapo">Copiapo</option>
                        <option value="serena">La serena</option>
                        <option value="ovalle">Ovalle</option>
                        <option value="viña">Viña del mar</option>
                        <option value="santiago">UST Santiago</option>
                        <option value="santiagocentro">IP/CFT Santiago Centro</option>
                        <option value="estacioncentral">IP/CFT Estacion Central</option>
                        <option value="sanjoaquin">IP/CFT San Joaquin </option>
                        <option value="puentealto">CFT Puente alto</option>
                        <option value="rancagua">Rancagua</option>
                        <option value="curico">Curico</option> 
                        <option value="talca">Talca</option> 
                        <option value="chillan">Chillan</option> 
                        <option value="conce">Concepcion</option> 
                        <option value="angeles">Los Angeles</option>
                        <option value="temuco">Temuco</option> 
                        <option value="valdivia">Valdivia</option> 
                        <option value="osorno">Osorno</option> 
                        <option value="puerto">Puerto Montt</option> 
                        <option value="puntaarenas">Punta Arenas</option> 
                        </select></td>
                    </tr>
                    <tr colspan="2">
                        <td> <center><input type="submit" value="Guardar"> </center></td>
                    </tr>
                </table>
            </form>
             
            
           
        </div>
        </center>
        
    </body>
</html>
