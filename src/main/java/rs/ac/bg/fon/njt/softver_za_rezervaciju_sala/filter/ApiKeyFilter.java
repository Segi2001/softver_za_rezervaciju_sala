
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.filter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.ApiKeyService;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "x-api-key";

    @Autowired
    private ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader(API_KEY_HEADER);
        String requestPath = request.getRequestURI();

        System.out.println("Requested Path: " + requestPath);
        System.out.println("API Key: " + apiKey);

        if (requestPath.startsWith("/api/public")) {
            if (apiKey == null || !apiKeyService.isValidApiKey(apiKey)) {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("Vrednost api kljuca nije validna ili nije poslata!!!");
      
                return;
            }
        }
       
        filterChain.doFilter(request, response);
    }
}
