package br.inf.safetech.cd.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Role;
import br.inf.safetech.cd.models.Usuario;

@Repository
@Transactional
public class UsuarioDAO implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager manager;

	public Usuario find(Integer id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> listar() {
		return manager.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}

	public Usuario loadUserByUsername(String login) {
		List<Usuario> usuarios = manager
				.createQuery("select u from Usuario u join fetch u.roles where login = :login", Usuario.class)
				.setParameter("login", login).getResultList();

		if (usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + login + " não foi encontrado");
		}

		return usuarios.get(0);
	}

	public void gravar(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		manager.persist(usuario);
	}

	public boolean usuarioJaExiste(Usuario usuario) {
		if (!manager.createQuery("select u from Usuario u where login = :pLogin", Usuario.class)
				.setParameter("pLogin", usuario.getLogin()).getResultList().isEmpty()) {
			return true;
		}
		return false;
	}

	public void alterarSenha(Integer id, String senha) {
		Usuario usuario = manager.find(Usuario.class, id);
		usuario.setSenha(passwordEncoder.encode(senha));
	}
}