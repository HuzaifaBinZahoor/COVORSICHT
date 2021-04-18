package scs.covid.covorsicht.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import scs.covid.covorsicht.models.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

}
