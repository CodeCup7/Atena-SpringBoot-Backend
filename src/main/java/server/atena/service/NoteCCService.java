package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.NoteCC;
import server.atena.repositories.NoteCCRepository;

@Service
public class NoteCCService {
	
    private final NoteCCRepository repository;

    @Autowired
    public NoteCCService(NoteCCRepository repository) {
        this.repository = repository;
    }
    
    public void add(NoteCC e) {
        repository.save(e);
    }
    
    public NoteCC getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<NoteCC> getAllNotes() {
        Iterable<NoteCC> iterable = repository.findAll();
        List<NoteCC> NoteList = new ArrayList<>();
        iterable.forEach(NoteList::add);
        return NoteList;
    }

    public void update(NoteCC NoteCC) {
        repository.save(NoteCC);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    public Iterable<NoteCC> getAllNote() {
        return repository.findAll();
    }
}