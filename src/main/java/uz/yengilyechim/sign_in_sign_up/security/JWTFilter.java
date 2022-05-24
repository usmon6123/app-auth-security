package uz.yengilyechim.sign_in_sign_up.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.yengilyechim.sign_in_sign_up.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;
    private final AuthService authService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //REQUESTDAN TOKENNI OLINYAPDI
        String authorization = request.getHeader("Authorization");


        //TOKEN BOR BO'LSA VA Bearer BILAN BOSHLANSA IFGA KIRADI
        if (authorization != null && authorization.startsWith("Bearer")) {

            //ASOSIY QISMINI AJRATIB OLYAPMIZ
            String token = authorization.substring(7);

            //TOKENIMIZ YAROQLI BO'LSA KIRADI
            if (jwtProvider.validateToken(token)) {

                //TOKEN ICHIDAN USER ID SINI OLYAPMIZ
                String userId = jwtProvider.getIdFromToken(token);

                //ID ORQALI USER DETAILS OLYAPMIZ
                UserDetails userDetails = authService.loadById(UUID.fromString(userId));//tokendagi idli user bazada bor bo'lsa o'sha userni qaytaryapdi aks holda throw

                //USER DETAILS ORQALI AUTHENTICATION YASAB OLYAPMIZ
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,  //Object principal
                                null, //Object credentials
                                userDetails.getAuthorities() //authorities (sistemada kirgan user bu) shu joyda user authenticate = true bo'ladi
                        );

                //SISTEMAGA QAYSI USER KIRAYOTGANINI O'RNATYAPMIZ
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);//sistemaga token orqali kelgan userni sistemaga kirgizadi, sistemada ekanini eslab qoladi

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        //BIZNING FILTRLAR TUGAGANDAN SO'NGSECURITYNING O'ZINI FILTERLARINI BOSHLASHINI AYTYAPMIZ
        filterChain.doFilter(request, response);
    }
}
