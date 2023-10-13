package br.com.denisesantos.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.var;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                // pegar autenticação (usuario e senha)

                // numero codificado em base 64
                var authorization = request.getHeader("Authorization");
                System.out.println(authorization);
                
                // remove a palavra basic
                var authEncoded = authorization.substring("Basic".length()).trim();
                System.out.println(authEncoded);
                
                // decodifica os numeros
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                System.out.println(authDecode);
                
                // transforma array de bytes em uma string
                var authString = new String(authDecode);
                System.out.println(authString);
                
                // divide a string em duas partes
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];
                System.out.println(username);
                System.out.println(password);


                filterChain.doFilter(request, response);


    }

   
    
}
