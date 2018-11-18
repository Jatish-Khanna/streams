
import io.pivotal.literx.domain.User;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Part07Errors {

//========================================================================================

	// TODO Return a Mono<User> containing User.SAUL when an error occurs in the input Mono, else do not change the input Mono.
	Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
        return mono.onErrorReturn(User.SAUL);
	}
	
    @Test
    public void monoWithValueInsteadOfError() {
		// Mono with error
        Mono<User> mono = betterCallSaulForBogusMono(Mono.error(new IllegalStateException()));
        StepVerifier.create(mono)
                .expectNext(User.SAUL)
                .expectComplete()
                .verify();
		// Mono with value
        mono = betterCallSaulForBogusMono(Mono.just(User.SKYLER));
        StepVerifier.create(mono)
                .expectNext(User.SKYLER)
                .expectComplete()
                .verify();

    }

//========================================================================================

	// TODO Return a Flux<User> containing User.SAUL and User.JESSE when an error occurs in the input Flux, else do not change the input Flux.
	Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
		return flux.
					onErrorResume(error -> Flux.just(User.SAUL, User.JESSE));
	}

    @Test
    public void fluxWithValueInsteadOfError() {
        Flux<User> flux = betterCallSaulAndJesseForBogusFlux(Flux.error(new IllegalStateException()));
        StepVerifier.create(flux)
                .expectNext(User.SAUL, User.JESSE)
                .expectComplete()
                .verify();

        flux = betterCallSaulAndJesseForBogusFlux(Flux.just(User.SKYLER, User.WALTER));
        StepVerifier.create(flux)
                .expectNext(User.SKYLER, User.WALTER)
                .expectComplete()
                .verify();

    }

//========================================================================================

	// TODO Implement a method that capitalizes each user of the incoming flux using the
	// #capitalizeUser method and emits an error containing a GetOutOfHereException error
	Flux<User> capitalizeMany(Flux<User> flux) {
				return flux
					.map(user -> {
						try{
							return capitalizeUser(user);
						}catch(GetOutOfHereException e){
							throw Exceptions.propagate(e);
						}
					});
	}

	User capitalizeUser(User user) throws GetOutOfHereException {
		if (user.equals(User.SAUL)) {
			throw new GetOutOfHereException();
		}
		return new User(user.getUsername(), user.getFirstname(), user.getLastname());
	}

	protected final class GetOutOfHereException extends Exception {
	}

    @Test
    public void handleCheckedExceptions() {
        Flux<User> flux = capitalizeMany(Flux.just(User.SAUL, User.JESSE));
        StepVerifier.create(flux)
                .expectError(GetOutOfHereException.class)
                .verify();
    }
}
