import java.util.*;
import java.util.function.*;
import java.time.*;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.BlockingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Part11BlockingToReactive {

//========================================================================================

	// TODO Create a Flux for reading all users from the blocking repository deferred until the flux is subscribed, and run it with an elastic scheduler
	Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
		return Flux.defer(() -> Flux.fromIterable(repository.findAll())).subscribeOn(Schedulers.elastic());
	}
	
    @Test
    public void slowPublisherFastSubscriber() {
        BlockingUserRepository repository = new BlockingUserRepository();
        Flux<User> flux = blockingRepositoryToFlux(repository);
        assertEquals("The call to findAll must be deferred until the flux is subscribed", 0, repository.getCallCount());
        StepVerifier.create(flux)
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .expectComplete()
                .verify();
    }

//========================================================================================

	// TODO Insert users contained in the Flux parameter in the blocking repository using an elastic scheduler and return a Mono<Void> that signal the end of the operation
	Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
		return flux.publishOn(Schedulers.elastic())
		            .doOnNext(user -> repository.save(user))
		            .then();
	}
	
	@Test
    public void fastPublisherSlowSubscriber() {
        ReactiveRepository<User> reactiveRepository = new ReactiveUserRepository();
        BlockingUserRepository blockingRepository = new BlockingUserRepository(new User[]{});
        Mono<Void> complete = fluxToBlockingRepository(reactiveRepository.findAll(), blockingRepository);
        assertEquals(0, blockingRepository.getCallCount());
        StepVerifier.create(complete)
                .expectComplete()
                .verify();
        Iterator<User> it = blockingRepository.findAll().iterator();
        assertEquals(User.SKYLER, it.next());
        assertEquals(User.JESSE, it.next());
        assertEquals(User.WALTER, it.next());
        assertEquals(User.SAUL, it.next());
        assertFalse(it.hasNext());
    }

}
