import { TestBed } from '@angular/core/testing';

import { AesCryptoService } from './aes-crypto.service';

describe('AesCryptoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AesCryptoService = TestBed.get(AesCryptoService);
    expect(service).toBeTruthy();
  });
});
