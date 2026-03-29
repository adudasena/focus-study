package com.focusstudy.backend.security;
import com.focusstudy.backend.model.entity.Papel;
import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.repositories.UsuarioRepository;
import com.focusstudy.backend.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService; // Use o Service, como o prof

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca através do service
        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario == null) throw new UsernameNotFoundException("Usuário não encontrado");

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getAuthorities())
                .build();
    }
}
