import java.util.*;
import java.util.function.*;
import java.time.*;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Part10ReactiveToBlocking {
//========================================================================================

	// TODO Return the user contained in that Mono
	User monoToValue(Mono<User> mono) {
		return mono.block();
	}
	
    @Test
    public void mono() {
        Mono<User> mono = repository.findFirst();
        User user = monoToValue(mono);
        assertEquals(User.SKYLER, user);
    }

//========================================================================================

	// TODO Return the users contained in that Flux
	Iterable<User> fluxToValues(Flux<User> flux) {
		return flux.toIterable();
	}
	
    @Test
    public void flux() {
        Flux<User> flux = repository.findAll();
        Iterable<User> users = fluxToValues(flux);
        Iterator<User> it = users.iterator();
        assertEquals(User.SKYLER, it.next());
        assertEquals(User.JESSE, it.next());
        assertEquals(User.WALTER, it.next());
        assertEquals(User.SAUL, it.next());
        assertFalse(it.hasNext());
    }
}
