package scs.covid.covorsicht.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.repositories.SubscriberRepository;

@Service
@Transactional
public class SubscriberService {

	@Autowired
	SubscriberRepository subscriberRepository;

	public Subscriber save(Subscriber subscriber) {

		return subscriberRepository.save(subscriber);
	}

	public Subscriber update(Subscriber subscriber) {

		return subscriberRepository.saveAndFlush(subscriber);
	}

	public Subscriber get(Long id) {
		return subscriberRepository.findById(id).orElse(null);
	}

	public List<Subscriber> getAll() {
		return subscriberRepository.findAll();
	}

	public void delete(Subscriber c) {
		subscriberRepository.delete(c);
	}

}
