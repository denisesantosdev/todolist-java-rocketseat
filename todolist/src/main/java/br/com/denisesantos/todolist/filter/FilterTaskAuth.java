package br.com.denisesantos.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.denisesantos.todolist.user.IUserRepository;
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
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {
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

            // validar usuario
            var user = this.userRepository.findByUsername(username);
            // se usuario não existir no banco de dados
            if (user == null) {
                response.sendError(401);
            } else {
                // verificar se a senha está correta
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // segue viagem
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);

                } else {
                    response.sendError(401);
                }

            }

        } else {
            // segue viagem
            filterChain.doFilter(request, response);
        }

    }

}
