package mn.cassandra.health.jackson

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import jakarta.inject.Inject

@MicronautTest(rebuildContext = true)
class MnCassandraHealthJacksonSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client

    @Property(name = "jackson.serialization-inclusion", value = "ALWAYS")
    void 'health call fails with jackson.serialization-inclusion set to ALWAYS'() {
        when:
        client.toBlocking().exchange("/health")

        then:
        def caught = thrown(HttpClientResponseException)
        HttpStatus.INTERNAL_SERVER_ERROR == caught.response.status()
    }

    @Property(name = "jackson.serialization-inclusion", value = "NON_EMPTY")
    void 'health call succeeds with jackson.serialization-inclusion set to the non-empty'() {
        when:
        def response = client.toBlocking().exchange("/health")

        then:
        HttpStatus.OK == response.status()
    }

}
