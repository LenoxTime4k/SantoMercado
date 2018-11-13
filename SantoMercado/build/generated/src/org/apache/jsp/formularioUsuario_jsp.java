package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class formularioUsuario_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Registro Usuario</title>\n");
      out.write("    </head>\n");
      out.write("    <style>\n");
      out.write("        body{\n");
      out.write("            background-color: lightblue;\n");
      out.write("        }\n");
      out.write("        h1{\n");
      out.write("            color: white;\n");
      out.write("            text-align: center;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        \n");
      out.write("    </style>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <h1>Registrar Usuario</h1>\n");
      out.write("        <br>\n");
      out.write("        <br>\n");
      out.write("        <br>\n");
      out.write("        <br>\n");
      out.write("        \n");
      out.write("        <center>\n");
      out.write("        <div >\n");
      out.write("            <form action=\"GuardarUsuario\" method=\"post\">\n");
      out.write("                <table>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Nombre Usuario Completo</td>\n");
      out.write("                        <td><input type=\"text\" name=\"nombreUsuario\" requerid></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Contraseña</td>\n");
      out.write("                        <td><input type=\"text\" name=\"contraseña\" requerid></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Nombre Completo</td>\n");
      out.write("                        <td><input type=\"text\" name=\"nombre\" requerid></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Rut</td>\n");
      out.write("                        <td><input type=\"text\" name=\"rut\" requerid></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Comuna Residencia</td>\n");
      out.write("                        <td><input type=\"text\" name=\"comuna\" requerid></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>Sede Institucion</td>\n");
      out.write("                        <td> <select name=\"sede\" >\n");
      out.write("                        <option value=\"opcion\" selected>Eliga una opcion</option>\n");
      out.write("                        <option value=\"arica\">Arica</option>\n");
      out.write("                        <option value=\"iquique\">Iquique</option>\n");
      out.write("                        <option value=\"antofagasta\">Antofagasta</option>\n");
      out.write("                        <option value=\"copiapo\">Copiapo</option>\n");
      out.write("                        <option value=\"serena\">La serena</option>\n");
      out.write("                        <option value=\"ovalle\">Ovalle</option>\n");
      out.write("                        <option value=\"viña\">Viña del mar</option>\n");
      out.write("                        <option value=\"santiago\">UST Santiago</option>\n");
      out.write("                        <option value=\"santiagocentro\">IP/CFT Santiago Centro</option>\n");
      out.write("                        <option value=\"estacioncentral\">IP/CFT Estacion Central</option>\n");
      out.write("                        <option value=\"sanjoaquin\">IP/CFT San Joaquin </option>\n");
      out.write("                        <option value=\"puentealto\">CFT Puente alto</option>\n");
      out.write("                        <option value=\"rancagua\">Rancagua</option>\n");
      out.write("                        <option value=\"curico\">Curico</option> \n");
      out.write("                        <option value=\"talca\">Talca</option> \n");
      out.write("                        <option value=\"chillan\">Chillan</option> \n");
      out.write("                        <option value=\"conce\">Concepcion</option> \n");
      out.write("                        <option value=\"angeles\">Los Angeles</option>\n");
      out.write("                        <option value=\"temuco\">Temuco</option> \n");
      out.write("                        <option value=\"valdivia\">Valdivia</option> \n");
      out.write("                        <option value=\"osorno\">Osorno</option> \n");
      out.write("                        <option value=\"puerto\">Puerto Montt</option> \n");
      out.write("                        <option value=\"puntaarenas\">Punta Arenas</option> \n");
      out.write("                        </select></td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr colspan=\"2\">\n");
      out.write("                        <td> <center><input type=\"submit\" value=\"Guardar\"> </center></td>\n");
      out.write("                    </tr>\n");
      out.write("                </table>\n");
      out.write("            </form>\n");
      out.write("             \n");
      out.write("            \n");
      out.write("           \n");
      out.write("        </div>\n");
      out.write("        </center>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
