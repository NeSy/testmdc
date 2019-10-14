import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { EqptService } from 'app/entities/eqpt/eqpt.service';
import { IEqpt, Eqpt } from 'app/shared/model/eqpt.model';
import { EqptType } from 'app/shared/model/enumerations/eqpt-type.model';

describe('Service Tests', () => {
  describe('Eqpt Service', () => {
    let injector: TestBed;
    let service: EqptService;
    let httpMock: HttpTestingController;
    let elemDefault: IEqpt;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EqptService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Eqpt(
        0,
        EqptType.SA,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Eqpt', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Eqpt(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Eqpt', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            nameFR: 'BBBBBB',
            nameGB: 'BBBBBB',
            mnemonicFR: 'BBBBBB',
            mnemonicGB: 'BBBBBB',
            commentFR: 'BBBBBB',
            commentGB: 'BBBBBB',
            address: 'BBBBBB',
            kind: 'BBBBBB',
            cdp: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Eqpt', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            nameFR: 'BBBBBB',
            nameGB: 'BBBBBB',
            mnemonicFR: 'BBBBBB',
            mnemonicGB: 'BBBBBB',
            commentFR: 'BBBBBB',
            commentGB: 'BBBBBB',
            address: 'BBBBBB',
            kind: 'BBBBBB',
            cdp: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Eqpt', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
