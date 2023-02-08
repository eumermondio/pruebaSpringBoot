package com.example.demo.jwtSecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class MyWebFilter implements Filter{
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    /**
     * 
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest) servletRequest; // Petición recibida desde el cliente
    	String uriDePeticionWeb = request.getRequestURI(); // url petición
    	String metodoRequerido = request.getMethod(); // método
    	//comprobamos si en la cabecera se han enviado los datos del usuario (lo que significaría que está logueado)
    	int idUsuarioAutenticadoMedianteJWT = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); 
    	     
    	
    	//La petición continúa si: el usuario está logueado (jwt); se recibe una petición options;
    	//se intenta acceder a la parte estática /webapp; si no hay jwt porque se está intentando acceder al método de autenticación   	
    	if (metodoRequerido.equalsIgnoreCase("OPTIONS") || 
    			uriDePeticionWeb.startsWith("/") ||     
    			uriDePeticionWeb.equals("/api/autentica") || 
    			idUsuarioAutenticadoMedianteJWT != -1) {
    		filterChain.doFilter(servletRequest, servletResponse);
    	}
    	else {
        	// En caso contrario, informamos de acceso denegado
        	HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.sendError(403, "No autorizado");  
    	}
    }
 
    @Override
    public void destroy() {
    }
}