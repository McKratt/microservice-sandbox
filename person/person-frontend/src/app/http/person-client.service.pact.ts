import {TestBed} from '@angular/core/testing';
import {PersonClientService} from './person-client.service';
import {Pact} from '@pact-foundation/pact';
import * as path from 'path';
import {HttpClientModule} from '@angular/common/http';
import {term} from '@pact-foundation/pact/dsl/matchers';

describe('Partner API', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      providers: [
        PersonClientService
      ]
    });
  });

  const provider: Pact = new Pact({
    port: 1234,
    log: path.resolve(process.cwd(), 'target', 'logs', 'mockserver-integration.log'),
    dir: path.resolve(process.cwd(), 'target', 'pacts'),
    spec: 2,
    logLevel: 'debug',
    pactfileWriteMode: 'update',
    consumer: 'person-frontend',
    provider: 'partner-api',
  });

  beforeAll((done) => {
    provider.setup().then(() => {
      done();
    });
  });

  afterAll((done) => {
    provider.verify()
      .then(() => provider.finalize())
      .then(() => {
        done();
      });
  });

  beforeAll((done) => {
    provider.addInteraction({
      state: '',
      uponReceiving: 'create partner',
      withRequest: {
        method: 'POST',
        path: '/person/rest/api/v1/partners',
        body: {
          name: 'Einstein',
          forename: 'Albert',
          birthDate: '14.03.1879'
        }
      },
      willRespondWith: {
        status: 201,
        body: {
          id: term({
            generate: 'P87654321',
            matcher: 'P[0-9]{8}'
          }),
          name: 'Einstein',
          forename: 'Albert'
        }
      }
    }).then(
      () => {
        done();
      },
      error => done.fail(error)
    );
  });

  it('should answer that the partner is created', (done) => {
    const client: PersonClientService = TestBed.get(PersonClientService);
    client.createPartner({
      name: 'Einstein', forename: 'Albert', birthDate: '14.03.1879'
    })
      .subscribe(
        (partner) => {
          expect(partner.id).toBeTruthy();
          done();
        },
        (error) => done.fail(error)
      );
  });

  beforeAll((done) => {
    provider.addInteraction({
      state: 'Exist a Partner',
      uponReceiving: 'read partner',
      withRequest: {
        method: 'GET',
        path: '/person/rest/api/v1/partners/P12345678'
      },
      willRespondWith: {
        status: 200,
        body: {
          id: 'P12345678',
          name: 'Einstein',
          forename: 'Albert',
          birthDate: '14.03.1879'
        }
      }
    }).then(
      () => {
        done();
      },
      error => done.fail(error)
    );
  });

  it('should read the corresponding partner', (done) => {
    const client: PersonClientService = TestBed.get(PersonClientService);
    client.readPartner('P12345678')
      .subscribe(
        () => {
          done();
        },
        (error) => done.fail(error)
      );
  });

})
;
