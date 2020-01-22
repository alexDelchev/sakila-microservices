import { Injectable } from '@angular/core';

import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent} from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable()
export class ApiRequestInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (~req.url.indexOf('/api/')) {
      req = req.clone({
        setHeaders: {
          'Content-type': 'application/json'
        }
      });
    }

    return next.handle(req);
  }
}
