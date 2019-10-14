import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CdmService } from 'app/entities/cdm/cdm.service';
import { ICdm, Cdm } from 'app/shared/model/cdm.model';
import { CdmNature } from 'app/shared/model/enumerations/cdm-nature.model';
import { EqptKind } from 'app/shared/model/enumerations/eqpt-kind.model';

describe('Service Tests', () => {
  describe('Cdm Service', () => {
    let injector: TestBed;
    let service: CdmService;
    let httpMock: HttpTestingController;
    let elemDefault: ICdm;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CdmService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Cdm(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CdmNature.DISCRETE,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        EqptKind.R,
        false,
        0
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

      it('should create a Cdm', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Cdm(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Cdm', () => {
        const returnedFromService = Object.assign(
          {
            index: 'BBBBBB',
            nameFR: 'BBBBBB',
            nameGB: 'BBBBBB',
            commentFR: 'BBBBBB',
            commentGB: 'BBBBBB',
            docNameFR: 'BBBBBB',
            docNameGB: 'BBBBBB',
            busMessage: 'BBBBBB',
            busWord: 'BBBBBB',
            mnemonicFR: 'BBBBBB',
            offset: 1,
            coding: 'BBBBBB',
            unitMsg: 'BBBBBB',
            minMsg: 'BBBBBB',
            maxMsg: 'BBBBBB',
            nature: 'BBBBBB',
            sign: true,
            cadrageVTL: 'BBBBBB',
            minValueVTL: 'BBBBBB',
            maxValueVTL: 'BBBBBB',
            minByteVTL: 1,
            maxByteVTL: 1,
            unitVTL: 'BBBBBB',
            kind: 'BBBBBB',
            linear: true,
            func: 1
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

      it('should return a list of Cdm', () => {
        const returnedFromService = Object.assign(
          {
            index: 'BBBBBB',
            nameFR: 'BBBBBB',
            nameGB: 'BBBBBB',
            commentFR: 'BBBBBB',
            commentGB: 'BBBBBB',
            docNameFR: 'BBBBBB',
            docNameGB: 'BBBBBB',
            busMessage: 'BBBBBB',
            busWord: 'BBBBBB',
            mnemonicFR: 'BBBBBB',
            offset: 1,
            coding: 'BBBBBB',
            unitMsg: 'BBBBBB',
            minMsg: 'BBBBBB',
            maxMsg: 'BBBBBB',
            nature: 'BBBBBB',
            sign: true,
            cadrageVTL: 'BBBBBB',
            minValueVTL: 'BBBBBB',
            maxValueVTL: 'BBBBBB',
            minByteVTL: 1,
            maxByteVTL: 1,
            unitVTL: 'BBBBBB',
            kind: 'BBBBBB',
            linear: true,
            func: 1
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

      it('should delete a Cdm', () => {
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
