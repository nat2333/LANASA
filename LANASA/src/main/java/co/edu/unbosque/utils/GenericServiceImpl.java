package co.edu.unbosque.utils;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericServiceAPI<T, ID> {

	public abstract JpaRepository<T, ID> getDao();
	
	@Override
	public T save(T entity) {
		return getDao().save(entity);
	}

	@Override
	public void delete(ID id) {
		if (!getDao().existsById(id))
            throw new ResourceNotFoundException("No existe id=" + id);
        getDao().deleteById(id);
	}

	@Override
	public T get(ID id) {
		return getDao().findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("No existe id=" + id));
	}

	@Override
	public List<T> getAll() {
		return getDao().findAll();
	}

	
}
