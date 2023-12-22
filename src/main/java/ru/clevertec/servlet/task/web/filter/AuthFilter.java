package ru.clevertec.servlet.task.web.filter;

import ru.clevertec.servlet.task.context.ApplicationContext;
import ru.clevertec.servlet.task.security.service.JwtService;
import ru.clevertec.servlet.task.util.Constants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = {
		"/api/v1/clients/*", "/api/v1/clients", "/api/v1/clients_xml"
})
public class AuthFilter implements Filter {

	private final static String NO_ACCESS = "No access, wrong UUID token.";
	public final JwtService jwtService = (JwtService) ApplicationContext.getBean(JwtService.class);

	/**
	 * Предоставляет доступ к ресурсам только Пользователям прошедшим аутентификацию.
	 * Проводит проверку token, предоставленную через
	 * Header uuid_token - он должен совпадать с token Пользователя из базы данных.
	 * Если token не правильный - выводится сообщение об этом
	 * "No access, wrong UUID token."
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		String tokenJWT = null;
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = httpRequest.getHeader(key);
				if (key.equals(Constants.UUID_TOKEN)) {
					tokenJWT = value;
				}
			}
		}
		if (jwtService.validateToken(tokenJWT)) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.getOutputStream()
							   .println(NO_ACCESS);
			httpServletResponse.setStatus(403);
		}
	}

	@Override
	public void destroy() {
		jwtService.deleteTokens();
	}

}
