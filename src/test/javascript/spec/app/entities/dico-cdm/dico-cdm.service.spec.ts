import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { IDicoCDM, DicoCDM } from 'app/shared/model/dico-cdm.model';

describe('Service Tests', () => {
  describe('DicoCDM Service', () => {
    let injector: TestBed;
    let service: DicoCDMService;
    let httpMock: HttpTestingController;
    let elemDefault: IDicoCDM;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DicoCDMService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DicoCDM(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a DicoCDM', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DicoCDM(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DicoCDM', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            prevDicoId: 'BBBBBB',
            imtRef: 'BBBBBB',
            version: 'BBBBBB',
            date: 'BBBBBB',
            releaseDate: 'BBBBBB',
            toolVersion: 'BBBBBB',
            state: 'BBBBBB'
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

      it('should return a list of DicoCDM', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            prevDicoId: 'BBBBBB',
            imtRef: 'BBBBBB',
            version: 'BBBBBB',
            date: 'BBBBBB',
            releaseDate: 'BBBBBB',
            toolVersion: 'BBBBBB',
            state: 'BBBBBB'
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

      it('should delete a DicoCDM', () => {
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
