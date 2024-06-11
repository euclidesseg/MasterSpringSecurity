package com.master.api.spring.security.master.config_security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.master.api.spring.security.master.config_security.filter.JwtAuthenticationFilter;
import com.master.api.spring.security.master.util.Role;
// * esta clase me premite especificar la configuracion para permitir el acceso a las
// * distintas areas de la aplicacion como lo son servicios repositorios o
// * controladores
@Configuration
@EnableWebSecurity //== activa y congigura componentes como el autentication configuration que esta en el securitibeaninjector, tambien activa el autenticatorentripoint, Habilita la seguridad basada en coincidencias de url
// @EnableMethodSecurity(prePostEnabled = true)// * Habilita la seguridad basada en aseguracion de metodos
public class HttpSecurityConfig { // esta es una cadena de filtros
    

	
    @Autowired
	SecurityBeansInjector securityBeansInjector;

	@Autowired
	private AuthenticationProvider daoAuthProvider ;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
    //== este metodo permite personalizar  como se van a gestionar y proteger las solicitudes http
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception { 
		SecurityFilterChain filterChain = http
			.csrf(csrsConfig -> csrsConfig.disable()) // desactiva crsf (crsf) deshabilitamos este tipo de proteccion
			.cors(corsConfig -> corsConfig.disable()) // desactiva cors para 
			.sessionManagement(	sessionManagConfig -> sessionManagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(this.daoAuthProvider) //== no necesitas llamar explícitamente ningún método de SecurityBeansInjector porque Spring Security
			//== se encarga de inyectar los beans necesarios automáticamente. Al referenciar authenticationProvider(this.authenticationProvider),
			//== Spring Security buscará un bean de tipo AuthenticationProvider en el contexto de la aplicación y lo utilizará para la autenticación
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(authReqConfig -> buildRequestMatchers(authReqConfig))
			.exceptionHandling(exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint))
			.exceptionHandling(exceptionConfig -> exceptionConfig.accessDeniedHandler(accessDeniedHandler))
			.build();
			 

		return  filterChain;
	}

	private void buildRequestMatchers(	AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
		// == aseguracion basada en coincidencias de url
		
		/*autorizacion paa endpoints de productos  */
		authReqConfig.requestMatchers(HttpMethod.GET, "/products")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.READ_ALL_PRODUCTS.name());
		authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.READ_ONE_PRODUCT.name());
		authReqConfig.requestMatchers(HttpMethod.POST, "/products/")
		.hasAnyRole(Role.ADMIN.name());
		// .hasAnyAuthority(RolPermission.CREATE_ONE_PRODUCT.name());
		authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.UPDATE_ONE_PRODUCT.name());
		authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
		.hasAnyRole(Role.ADMIN.name());
		// .hasAnyAuthority(RolPermission.DISABLE_ONE_PRODUCT.name());

		/*autorizacion paa endpoints de categories */
		authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.READ_ALL_CATEGORYS.name());
		authReqConfig.requestMatchers(HttpMethod.GET, "/categories{categoryId}")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.READ_ONE_CATEGORY.name());
		authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
		.hasAnyRole(Role.ADMIN.name());
		// .hasAnyAuthority(RolPermission.CREATE_ONE_CATEGORY.name());
		authReqConfig.requestMatchers(HttpMethod.PUT, "/categories{categoryID}")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name());
		// .hasAnyAuthority(RolPermission.UPDATE_ONE_CATEGORY.name());
		authReqConfig.requestMatchers(HttpMethod.PUT, "/categories{categoryId}/disabled")
		.hasAnyRole(Role.ADMIN.name());
		// .hasAnyAuthority(RolPermission.DISABLE_ONE_CATEGORY.name());
	
		
		/*Autorizacion para el endpoint de leer mi perfil */
		authReqConfig.requestMatchers(HttpMethod.PUT, "/auth/profile")
		.hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADIM.name(),Role.CUSTOMER.name());
		// .hasAnyAuthority(RolPermission.READ_MY_PROFILE.name());

		/*Authorization de enpoints publicos */
		authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll(); //== Aquí se especifica que las solicitudes POST a la ruta /auth/login deben ser permitidas para todos (permitAll())
		authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
		authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
		authReqConfig.requestMatchers("/error").permitAll();
		//== Spring Security accede al contexto de autenticación actual para verificar 
		//== si el usuario tiene alguna de las autoridades especificadas ("NORMAL" o "ADMIN"). 
		authReqConfig.anyRequest().authenticated();//== que todas las demas url deban estar autenticadas
	}
			
	private void buildRequestMatchersV2(	AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
		

		/*Authorization de enpoints publicos */
		authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll(); //== Aquí se especifica que las solicitudes POST a la ruta /auth/login deben ser permitidas para todos (permitAll())
		authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
		authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
		authReqConfig.requestMatchers("/error").permitAll();
		//== Spring Security accede al contexto de autenticación actual para verificar 
		//== si el usuario tiene alguna de las autoridades especificadas ("NORMAL" o "ADMIN"). 
		authReqConfig.anyRequest().authenticated();//== que todas las demas url deban estar autenticadas
	}
			
			
	
}


//! STATELESS
//**Indica que el tipo de politica de sesion de mi app es una sesion sin estado es decir, no va a manterner un 
//** estado de la sesion en el servidor ya que es pr jwt

//!  El orden
//** los filtros en Spring Security son  cruciales para determinar cómo se procesan las solicitudes
//** y se aplica la seguridad. En este caso, la inserción de JwtAuthenticationFilter antes de UsernamePasswordAuthenticationFilter
//** tiene un propósito específico:

	//==  1. Priorizar la autenticación JWT:
	//**  JwtAuthenticationFilter se encarga de la autenticación basada en tokens JWT. 
	//**  Al colocarlo antes de UsernamePasswordAuthenticationFilter, se le da prioridad a la aurorizacion JWT.
	//**  Si la solicitud contiene un token JWT válido, el usuario será autorizado utilizando el token

	//==  2. Optimizar el rendimiento:
	//**  Si la solicitud no contiene un token JWT válido o la autorizacion JWT falla,
	//**  la solicitud sera bloqueada pro los mecanismos de seguridad yy se lanzará una excepcion con AuthenticationEntryPoint
	
	//== 3. Seguridad
	//** El filtro JwtAuthenticationFilter no solo valida el token JWT, sino que también establece
	//** el contexto de seguridad con la información del usuario autenticado. Esto es crucial para que las partes
	//** posteriores del flujo de la solicitud, como los controladores o servicios, puedan acceder a la identidad del usuario
	//** y aplicar las reglas de autorización correspondientes.

	//# ¿Qué es UsernamePasswordAuthenticationFilter?
	//** UsernamePasswordAuthenticationFilter es un filtro de Spring Security predefinido que maneja la autenticación
	//** tradicional basada en nombre de usuario y contraseña.


//# Porque se pone jwtAuthenticationFilter antes  de UsernamePasswordAuthenticationFilter
//** Primero que todo todos estos son filtros de spring boot, que se aplican a todas las peticiones
//** y la razon del orden es que jwtAuthenticationFilter restrinje o permite el acceso a enpoints establecidos, 
//** y UsernamePasswordAuthenticationFilter actua como un filtro para permitir o denegar el acceso al sistema durante una peticion de logueo o registro,
//** ahora, no se nota, pero el jwtAuthenticationFilter siempre se ejecuta primero que UsernamePasswordAuthenticationFilter incluso en una peticion de logueo o registro
//** solo que va a permmitir el acceso ya que estos epoints son publicos, por tanto el jwtAuthenticationFilter comienza a trabajar más durante las
//** peticiones subsecuentes


