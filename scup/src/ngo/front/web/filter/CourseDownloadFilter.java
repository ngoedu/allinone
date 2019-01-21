package ngo.front.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 */
public class CourseDownloadFilter implements Filter {


	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if(req instanceof HttpServletRequest) {
			doFilter((HttpServletRequest)req, (HttpServletResponse)res, chain);
		}else {
			chain.doFilter(req, res);
		}
	}

	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
			String token = request.getParameter("token");
			
			if (!isTokenValidated(token)) {
				response.sendError(response.SC_UNAUTHORIZED);
			} else {
				chain.doFilter(request, response);
			}
	}

	public void init(FilterConfig config) throws ServletException {
		
	}
	
	private boolean isTokenValidated(String token) {
		//TODO: add real token check here.
		if (token != null && token.equals("NGO"))
			return true;
		return false;
	}

}
